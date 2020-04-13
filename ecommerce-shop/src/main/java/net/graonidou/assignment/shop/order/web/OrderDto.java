package net.graonidou.assignment.shop.order.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.hateoas.RepresentationModel;

import net.graonidou.assignment.shop.order.Order.Status;

/**
 * Representation of an <code>Order</code>
 * 
 * @author Eirini Graonidou
 *
 */
public class OrderDto extends RepresentationModel<OrderDto> {
	
	public Long id;
	public List<OrderItemDto> orderItems = new ArrayList<>();
	public Status status;
	
	public BigDecimal totalPrice() {
		return orderItems
				.stream()
				.map(OrderItemDto::getTotalPrice)
				.collect(summingUp());
	}
	
	public static Collector<BigDecimal, ?, BigDecimal> summingUp() {
	    return Collectors.reducing(BigDecimal.ZERO, BigDecimal::add);
	}

}
