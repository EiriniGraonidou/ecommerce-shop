/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import net.graonidou.assignment.shop.commons.BusinessRuntimeException;

/**
 * Model for an order. Has a <code>Status</code> and holds a set of order items.
 * 
 * @author Eirini Graonidou
 *
 */
@Getter
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends AbstractAggregateRoot<Order> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orderItems;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	
	Order() {
		this.orderItems = new HashSet<>();
		this.status = Status.SUBMITTED;
	}
	
	void addItem(@NonNull OrderItem orderItem) {
		this.orderItems.add(orderItem);
		registerEvent(OrderItemsAdded.with(this.id, LocalDateTime.now(), List.of(orderItem)));
	}
	
	void addItems(@NonNull List<OrderItem> orderItems) {
		if (orderItems.isEmpty()) {
			throw new BusinessRuntimeException("Cannot add items. The input list is empty");
		}
		this.orderItems.addAll(orderItems);
		registerEvent(OrderItemsAdded.with(this.id, LocalDateTime.now(), orderItems));
	}
	
	/**
	 * Validates and completes the order. 
	 * This is the last state of that an order can reach.
	 * 
	 */
	void complete() {
		if (this.status == Status.COMPLETED) {
			throw new BusinessRuntimeException("Cannot complete. The order is already completed");
		}
		
		if (this.orderItems.isEmpty()) {
			throw new BusinessRuntimeException("Cannot complete order. No order items have been added to the order");
		}
		
		this.status = Status.COMPLETED;
		registerEvent(OrderCompleted.of(this));
	}
	
	
	public enum Status {
		SUBMITTED, COMPLETED;
	}
	

	/**
	 * Domain event indicating that the order has been completed; here the action
	 * of the product being bought is expressed implicitly. 
	 * 
	 * @author Eirini Graonidou
	 *
	 */
	@Value
	@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
	public static class OrderCompleted {
		Order order;
	}
	
	/**
	 * Domain event indicating that one or more <code>OrderItem</code>(s) have been added to the order.
	 * 
	 * @author Eirini Graonidou
	 *
	 */
	@Value
	@RequiredArgsConstructor(staticName = "with", access = AccessLevel.PRIVATE)
	public static class OrderItemsAdded {
		Long orderId;
		LocalDateTime itemAddedAt;
		List<OrderItem> orderItems;
	}
}
