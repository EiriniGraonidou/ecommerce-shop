/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.order;

import java.util.UUID;

import javax.persistence.Entity;
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
	private UUID id;
	
	@OneToOne
	private Product product;
	
	private long amount;

	public OrderItem(Product product, long amount) {
		this.id = UUID.randomUUID();
		this.product = product;
		this.amount = amount;
	}
	
}
