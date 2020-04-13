/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.stock;

import static java.lang.Long.parseLong;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.graonidou.assignment.shop.commons.ResourceNotFoundException;
import net.graonidou.assignment.shop.order.Order.OrderCompleted;
import net.graonidou.assignment.shop.order.Order.OrderItemsAdded;
import net.graonidou.assignment.shop.product.Product;

/**
 * Class responsible for the management of reservations
 * 
 * @author Eirini Graonidou
 *
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ReservationManager {
	
	@Value("${reservation.expiration.minutes}")
	private String reservationExpirationInMuntes;

	private final Reservations reservations;
	private final ProductStockRepository productsStock;
	

	/**
	 * Handler for <code>OrderItemsAdded</code> event.
	 * Makes a <code>Reservation</code>; that is the ordered amount of the product reduces the
	 * stock for the configured period of time.
	 * 
	 * @param event
	 */
	@EventListener
	void on(OrderItemsAdded event) {
		log.info("Creating reservation for the added items");
		event.getOrderItems().forEach(orderItem -> {
			Reservation reservation = Reservation.builder()
					.orderItemId(orderItem.getId())
					.product(orderItem.getProduct())
					.amount(orderItem.getAmount())
					.createdAt(event.getItemAddedAt())
					.expiresAt(event.getItemAddedAt().plusMinutes(parseLong(reservationExpirationInMuntes)))
					.build();
			
			ProductStock productStock = this.productsStock.findByProduct(orderItem.getProduct());
			productStock.reduce(orderItem.getAmount());
			this.productsStock.save(productStock);
			
			this.reservations.save(reservation);
			
		});
	}
	
	/**
	 * Handler for <code>OrderCompleted</code> event. 
	 * Reduces the stock if needed; that is if there is no active reservation for the ordered items.
	 * 
	 * @param event
	 */
	@EventListener
	void on(OrderCompleted event) {
		event.getOrder().getOrderItems().forEach(orderItem -> {
			Reservation reservation = this.reservations.findByOrderItemId(orderItem.getId())
					.orElseThrow(() -> new ResourceNotFoundException("No reservation could be found for the order item :" + orderItem));
			if(reservation.isRollbacked()) {
				Product product = orderItem.getProduct();
				ProductStock productStock = productsStock.findByProduct(product);
				log.info("Reducing stock for product {} by {} amount", product.getName(), orderItem.getAmount());
				productStock.reduce(orderItem.getAmount());
				this.productsStock.save(productStock);
			}
			this.reservations.delete(reservation);
		});
	}
	
	/**
	 * Scheduled job running every minute, checking if the reservation period of an ordered item has been exceeded.
	 * If so, it releases the reserved ordered item back to the stock. 
	 */
	@Scheduled(fixedRate = 60_000)
	void rollback() {
		Set<Reservation> reservations = this.reservations.findAll(toRollback());
		log.info("Detected {} reservations that are about to be rolled back.", reservations.size());
		reservations.forEach(reservation -> {
			long reservedAmount = reservation.rollback();
			ProductStock stock = productsStock.findByProduct(reservation.getProduct());
			stock.refill(reservedAmount);
			this.productsStock.save(stock);
			this.reservations.save(reservation);
		});
	}
	
	private static Specification<Reservation> toRollback() {
		return new Specification<Reservation>() {
			/**
			 * default serialVersionUID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(
						cb.greaterThanOrEqualTo(root.get(Reservation_.EXPIRES_AT), LocalDateTime.now()),
						cb.isFalse(root.get(Reservation_.IS_ROLLBACKED)));
			}
		};
	}

}
