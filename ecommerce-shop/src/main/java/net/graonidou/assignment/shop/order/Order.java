package net.graonidou.assignment.shop.order;

import java.time.LocalDateTime;
import java.util.HashSet;
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
	
	
	public Order() {
		this.orderItems = new HashSet<>();
		this.status = Status.CREATED;
	}
	
	public void addItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		
		registerEvent(OrderItemAdded.with(LocalDateTime.now(), orderItem));
	}
	
	public void complete() {
		this.status = Status.COMPLETED;
		
		registerEvent(OrderCompleted.of(this));
	}
	
	
	enum Status {
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
	public static class OrderItemAdded {
		LocalDateTime itemAddedAt;
		OrderItem orderItem;
	}
}
