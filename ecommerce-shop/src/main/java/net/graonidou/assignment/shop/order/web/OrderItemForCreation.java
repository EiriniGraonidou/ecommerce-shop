/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.order.web;

import lombok.NoArgsConstructor;

/**
 * Representation of an <code>OrderItem</code> to be created.
 * 
 * @author Eirini Graonidou
 *
 */
@NoArgsConstructor
public class OrderItemForCreation {

	public Long productId;
	public Long amount;
}
