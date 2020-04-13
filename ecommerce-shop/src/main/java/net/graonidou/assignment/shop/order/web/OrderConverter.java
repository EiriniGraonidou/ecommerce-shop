package net.graonidou.assignment.shop.order.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import net.graonidou.assignment.shop.order.Order;
import net.graonidou.assignment.shop.order.Order.Status;
import net.graonidou.assignment.shop.order.OrderItem;
import net.graonidou.assignment.shop.product.Product;
import net.graonidou.assignment.shop.product.ProductManager;
import net.graonidou.assignment.shop.product.web.ProductController;

/**
 * Converter between the model and the representation of an <code>Order</code>.
 * 
 * @author Eirini Graonidou
 *
 */
@Component
public class OrderConverter extends RepresentationModelAssemblerSupport<Order, OrderDto> {

	private final ProductManager productManager;
	
	@Autowired
	public OrderConverter(ProductManager productManager) {
		super(OrderController.class, OrderDto.class);
		this.productManager = productManager;
	}

	@Override
	public OrderDto toModel(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.id = order.getId();
		orderDto.status = order.getStatus();
		orderDto.orderItems = toOrderItemsDto(order.getOrderItems());
		addLinks(orderDto);

		return orderDto;
	}
	
	public List<OrderItem> convert(List<OrderItemForCreation> orderItemDtos) {
		List<OrderItem> orderItems = new ArrayList<>();
		orderItemDtos.forEach(dto -> {
			Product product = productManager.findById(dto.productId);
			orderItems.add(new OrderItem(product, dto.amount));
		});
		return orderItems;
	}
	
	private void addLinks(OrderDto orderDto) {
		if (orderDto.status == Status.COMPLETED) {
			return;
		}
		orderDto.add(linkTo(methodOn(OrderController.class).postOrderItem(orderDto.id, Collections.emptyList()))
				.withRel("addItems")
				.withType("POST"));
		orderDto.add(linkTo(methodOn(OrderController.class).complete(orderDto.id))
				.withRel("complete").withType("PATCH"));
	}

	private List<OrderItemDto> toOrderItemsDto(Set<OrderItem> orderItems) {
		 List<OrderItemDto> orderItemsDto = new ArrayList<>();
		 orderItems.forEach(item -> {
			 OrderItemDto dto = new OrderItemDto();
			 dto.code = item.getProduct().getCode();
			 dto.name = item.getProduct().getName();
			 dto.itemPrice = item.getProduct().getPrice().getAmount();
			 dto.currency = item.getProduct().getPrice().getCurrency().getCode();
			 dto.amount = item.getAmount();
			 dto.add(linkTo(methodOn(ProductController.class).get(item.getProduct().getId())).withRel("productDetails"));
			 orderItemsDto.add(dto);
		 });
		return orderItemsDto;
	}
}
