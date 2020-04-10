package net.graonidou.assignment.shop.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.graonidou.assignment.shop.product.Product;

/**
 * 
 * @author Eirini Graonidou
 *
 */
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne
	private Product product;
	
	private long amount;

	public OrderItem(Product product, long amount) {
		this.product = product;
		this.amount = amount;
	}
	
}
