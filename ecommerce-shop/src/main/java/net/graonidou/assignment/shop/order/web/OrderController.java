package net.graonidou.assignment.shop.order.web;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody OrderDto post(@RequestBody OrderDto orderDto) {
		return null;
	}
	
	
	/**
	 * Adds items to the existing <code>Order</code>.
	 * If the order does not exist yet, a new order will be published.
	 * 
	 * @param orderId the identifier of the order to add items to.
	 * @param orderItem the order item information to be added
	 * @return the newest version of the order representation.
	 */
	@RequestMapping(path = "{orderId}/items", method = RequestMethod.POST)
	public @ResponseBody OrderDto postOrderITem(
			@PathVariable("orderId") Long orderId,
			@RequestBody List<OrderItemDto> orderItem) {
		
		return null;
		
	}
	
	/**
	 * Completes the order; last step of the order life cycle
	 *   
	 * @param orderId the identfier of the order to be completed.
	 * 
	 * @return new last version of the order representation.
	 */
	@RequestMapping(path = "{orderId}", method = RequestMethod.PATCH)
	public @ResponseBody OrderDto complete(
			@PathVariable("orderId") Long orderId) {
		
		return null;
		
	}

}
