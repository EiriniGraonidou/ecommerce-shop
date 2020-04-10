package net.graonidou.assignment.shop.stock;

import net.graonidou.assignment.shop.product.Product;

/**
 * Exception noting the insufficiency of the product's stock.
 * 
 * @author Eirini Graonidou
 *
 */
public class OutOfStock extends RuntimeException {

	Product product;
	long stock;

	OutOfStock(Product product, long stock) {

		super("Insufficient stock for product: "
				+ product.getName()+ " Current stock: " + stock);

		this.product = product;
		this.stock = stock;
	}

}
