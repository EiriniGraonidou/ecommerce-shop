package net.graonidou.assignment.shop.order.web;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import net.graonidou.assignment.shop.order.Order;

public class OrderConverter extends RepresentationModelAssemblerSupport<Order, OrderDto> {

	public OrderConverter() {
		super(OrderController.class, OrderDto.class);
	}

	@Override
	public OrderDto toModel(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

}
