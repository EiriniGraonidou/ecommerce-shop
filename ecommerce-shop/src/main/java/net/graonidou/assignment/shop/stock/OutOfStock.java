package net.graonidou.assignment.shop.stock;

import net.graonidou.assignment.shop.commons.BusinessRuntimeException;
import net.graonidou.assignment.shop.product.Product;

/**
 * Exception noting the insufficiency of the product's stock.
 * 
 * @author Eirini Graonidou
 *
 */
public class OutOfStock extends BusinessRuntimeException {

	/**
	 * default serialVersionUild
	 */
	private static final long serialVersionUID = 1L;
	
	Product product;
	long stock;

	OutOfStock(Product product, long stock) {

		super("Insufficient stock for product: "+ product 
				+ " Current stock: " + stock);

		this.product = product;
		this.stock = stock;
	}

}
