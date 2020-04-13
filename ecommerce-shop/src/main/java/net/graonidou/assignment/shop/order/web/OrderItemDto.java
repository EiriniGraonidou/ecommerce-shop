package net.graonidou.assignment.shop.order.web;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.hateoas.RepresentationModel;

/**
 * 
 * Representation of an <code>OrderItem</code>
 * 
 * @author Eirini Graonidou
 *
 */
public class OrderItemDto  extends RepresentationModel<OrderItemDto> {
	
	public String code;
	public String name;
	public long amount;
	public BigDecimal itemPrice;
	public String currency;
	
	public BigDecimal getTotalPrice() {
		return Optional.ofNullable(itemPrice.multiply(new BigDecimal(amount))).orElse(BigDecimal.ZERO);
	}

}
