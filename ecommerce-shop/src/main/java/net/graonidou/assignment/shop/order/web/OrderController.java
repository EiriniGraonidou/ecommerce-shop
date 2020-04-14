/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.order.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.graonidou.assignment.shop.order.Order;
import net.graonidou.assignment.shop.order.OrderManager;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderController {

	private final OrderManager orderManager;
	private final OrderConverter orderConverter;

	@ApiOperation(value = "Creates a new order", response = OrderDto.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully created order")
	})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody OrderDto post() {
		return this.orderConverter.toModel(orderManager.create());
	}

	/**
	 * Adds items to the existing <code>Order</code>.
	 * 
	 * @param orderId   the identifier of the order to add items to.
	 * @param orderItem the order item information to be added
	 * @return the newest version of the order representation.
	 */
	@ApiOperation(value = "Adds items to an existing order", response = OrderDto.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully added order items.")
	})
	@RequestMapping(path = "{orderId}/items", method = RequestMethod.PUT)
	public @ResponseBody OrderDto postOrderItem(@PathVariable("orderId") Long orderId,
			@RequestBody List<OrderItemForCreation> orderItem) {
		Order order = this.orderManager.addItems(orderId, this.orderConverter.convert(orderItem));
		return this.orderConverter.toModel(order);
	}

	/**
	 * Completes the order; last step of the order life cycle
	 * 
	 * @param orderId the identifier of the order to be completed.
	 * 
	 * @return new last version of the order representation.
	 */
	@ApiOperation(value = "Completes an existing order", response = OrderDto.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully completed order."),
	        @ApiResponse(code = 400, message = "The order is unable to complete."
	        		+ " Either it is in no appropriate state or no items were added")
	})
	@RequestMapping(path = "{orderId}", method = RequestMethod.PATCH)
	public @ResponseBody OrderDto complete(@PathVariable("orderId") Long orderId) {
		return this.orderConverter.toModel(this.orderManager.complete(orderId));

	}

}
