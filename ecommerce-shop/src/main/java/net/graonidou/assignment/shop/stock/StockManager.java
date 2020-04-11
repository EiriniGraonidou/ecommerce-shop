package net.graonidou.assignment.shop.stock;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.graonidou.assignment.shop.order.Order.OrderCompleted;
import net.graonidou.assignment.shop.product.Product;
import net.graonidou.assignment.shop.product.Product.ProductAdded;

/**
 * Class responsible for the stock management of products reacting to specific application events.
 * 
 * @author Eirini Graonidou
 *
 */
@Service
@RequiredArgsConstructor(onConstructor__ = @Autowired)
@Slf4j
public class StockManager {
	
	private final ProductStockRepository productsStock;
	
	public Page<ProductStock> fetchAll(Pageable page) {
		return productsStock.findAll(page);
	}
	
	public ProductStock fetchById(Long productStockId) {
		return productsStock.findById(productStockId).orElseThrow(EntityNotFoundException::new);
	}
	
	public ProductStock refill(Long productStockId, Long quantity) {
		ProductStock productStock = productsStock.findById(productStockId).orElseThrow(EntityNotFoundException::new);
		productStock.refill(quantity);
		return productsStock.save(productStock);
	}

	@EventListener
	void on(ProductAdded event) {
		productsStock.save(new ProductStock(event.getProduct()));
	}
	
	@EventListener
	void on(OrderCompleted event) {
		event.getOrder().getOrderItems().forEach(orderItem -> {
			Product product = orderItem.getProduct();
			ProductStock stock = productsStock.findByProduct(product);
			log.info("Reducing stock for product {} by {} amount", product.getName(), orderItem.getAmount());
			stock.reduce(orderItem.getAmount());
		});
	}
	
}
