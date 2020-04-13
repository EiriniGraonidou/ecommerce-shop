package net.graonidou.assignment.shop.order;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.graonidou.assignment.shop.commons.ResourceNotFoundException;
import net.graonidou.assignment.shop.order.Order.Status;

/**
 * Class responsible for the managing of orders.
 * 
 * @author Eirini Graonidou
 *
 */
@Service
@RequiredArgsConstructor
public class OrderManager {

	private final Orders orders;
	
	/**
	 * Creates an order with its default initial state.
	 * 
	 * @return the newly created order.
	 */
	public Order create() {
		return orders.save(new Order());
	}
	
	/**
	 * Retrieves the <code>Order</code> for the given identifier.
	 * 
	 * @param orderId the identifier of the order to fetch
	 * @return the fetched <code>Order</code>.
	 * 
	 * @throws {@link ResourceNotFoundException} if no order with the given identifier exists.
	 */
	public Order fetchById(Long orderId) {
		return orders.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("the requested resource could not be found"));
	}
	
	/**
	 * Adds an <code>OrderItem</code> to the existing <code>Order</code>
	 * @param orderId the identifier of the <code>Order</code>
	 * @param orderItem the <code>OrderItem</code> to be added.
	 * 
	 * @return the persisted, up-to-date version of the <code>Order</code> with the added item.
	 */
	public Order addItems(Long orderId, List<OrderItem> orderItems) {
		Order order = fetchById(orderId);
		order.addItems(orderItems);
		return orders.save(order);
	}
	
	/**
	 * Completes the order. This is the last state that an <code>Order</code> can reach.
	 * @param orderId the identifier of the order.
	 * 
	 * @return the updated, completed order.
	 */
	public Order complete(Long orderId) {
		Order order = fetchById(orderId);
		order.complete();
		return orders.save(order);
	}
	
	
}
