package net.graonidou.assignment.shop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Manages the basic actions that can be done for products.
 * 
 * @author Eirini Graonidou
 *
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductManager {
	
	private final Catalogue catalogue;
	
	/**
	 * Persists a new product by adding it to the product catalogue.
	 * 
	 * @param code 
	 * @param name the product's name
	 
	 * @return the newly created product
	 */
	public Product add(String code, String name) {
		Product product = Product.with()
			.code(code)
			.name(name)
			.build();
		return catalogue.save(product);
	}
	
	
	
}
