package net.graonidou.assignment.shop.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.graonidou.assignment.shop.commons.BusinessRuntimeException;

/**
 * 
 * @author Eirini Graonidou
 *
 */
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends AbstractAggregateRoot<Order> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orderItems;
	
	private Status status;
	
	
	Order() {
		this.orderItems = new HashSet<>();
		this.status = Status.CREATED;
	}
	
	void addItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		registerEvent(OrderItemsAdded.with(this.id, LocalDateTime.now(), List.of(orderItem)));
	}
	
	void addItems(List<OrderItem> orderItems) {
		this.orderItems.addAll(orderItems);
		registerEvent(OrderItemsAdded.with(this.id, LocalDateTime.now(), orderItems));
	}
	
	void complete() {
		if (this.status == Status.COMPLETED) {
			throw new BusinessRuntimeException("Cannot complete.The order is already completed");
		}
		this.status = Status.COMPLETED;
		registerEvent(OrderCompleted.of(this));
	}
	
	
	public enum Status {
		CREATED, SUBMITTED, COMPLETED;
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
	 * Domain event indicating that an <code>OrderItem</code>has been added to the order.
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
