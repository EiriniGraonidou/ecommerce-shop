package net.graonidou.assignment.shop.order;

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
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "orders")
public class Order  extends AbstractAggregateRoot<Order> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orderItems;
	
	private Status status;
	
	public void complete() {
		this.status = Status.COMPLETED;
		
		registerEvent(OrderCompleted.of(this));
	}
	
	
	enum Status {
		CREATED, SUBMITTED, COMPLETED;
	}
	

	@Value
	@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
	public static class OrderCompleted {
		Order order;
	}
}
