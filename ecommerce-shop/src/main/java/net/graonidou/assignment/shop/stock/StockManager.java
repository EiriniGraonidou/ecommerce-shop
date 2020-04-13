package net.graonidou.assignment.shop.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.graonidou.assignement.shop.commons.ResourceNotFoundException;
import net.graonidou.assignment.shop.product.Product.ProductAdded;

/**
 * Class responsible for the stock management of products.
 * 
 * @author Eirini Graonidou
 *
 */
@Service
@RequiredArgsConstructor(onConstructor__ = @Autowired)
@Slf4j
public class StockManager {
	
	private final ProductStockRepository productsStock;
	
	/**
	 * Finds and returns all product stock entries for a given page.
	 
	 * @param page 
	 * @return the found stock.
	 */
	public Page<ProductStock> fetchAll(Pageable page) {
		return productsStock.findAll(page);
	}
	
	/**
	 * Finds a returns the stock information for a given product stock identifier.
	 * 
	 * @param productStockId the identifier to search for
	 * @return the found
	 * 
	 * @throws a <code>ResourceNotFoundException</code> if no entry could be given for the provided identifier.
	 */
	public ProductStock fetchById(Long productStockId) {
		return productsStock.findById(productStockId)
				.orElseThrow(() -> new ResourceNotFoundException("No product stock for id: "+ productStockId + " could be found"));
	}
	
	/**
	 * Refills the stock for a given product stock by a given quantity.
	 *  
	 * @param productStockId the identifier of the product stock 
	 * @param quantity the quantity by the stock will be refilled
	 * 
	 * @return the refilled product stock
	 * 
	 *  @throws a <code>ResourceNotFoundException</code> if no entry could be given for the provided identifier.
	 */
	public ProductStock refill(Long productStockId, Long amount) {
		
		ProductStock productStock = productsStock.findById(productStockId)
				.orElseThrow(() -> new ResourceNotFoundException("No product stock for id: "+ productStockId + " could be found"));
		productStock.refill(amount);
		log.info("Refilling product stock with id: "+productStockId + " by: "+amount);
		return productsStock.save(productStock);
	}

	/**
	 * Listener for new products. Creates the product stock entries.
	 * 
	 * @param event of the product that was added
	 */
	@EventListener
	void on(ProductAdded event) {
		productsStock.save(new ProductStock(event.getProduct()));
	}
	
}
