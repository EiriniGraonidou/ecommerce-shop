/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.product;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.graonidou.assignment.shop.commons.ResourceNotFoundException;
import net.graonidou.assignment.shop.product.Price.Currency;

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
	Product add(String code, String name, Price price) {
		Product product = Product.with()
			.code(code)
			.name(name)
			.price(price)
			.build();
		return catalogue.save(product);
	}
	
	/**
	 * Retrieves the <code>Product</code> with the given identifier.
	 * 
	 * @param productId the identifier of the <code>Product</code>
	 * @return the <code>Product</code> with the given identifier.
	 */
	public Product findById(Long productId) {
		return this.catalogue.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("The product with id: " + productId + " could not be found"));
	}
	
	/**
	 * Finds all <code>Products</code> in a paginated manner.
	 * 
	 * @param page the page to retrieve products
	 * 
	 * @return the product information for the given or a default page
	 */
	public Page<Product> findAll(Pageable page) {
		return catalogue.findAll(page);
	}
	
	public void loadInitialTestData() {
		add("s1dd3", "super product", Price.of(BigDecimal.valueOf(100), Currency.ARGENTINE_PESO));
		add("q5588", "minimal product", Price.of(BigDecimal.valueOf(100), Currency.POUND_STERLING));
		add("5yz8a", "smooth product", Price.of(BigDecimal.valueOf(100), Currency.EURO));
		add("lkz1q", "awesome product",Price.of(BigDecimal.valueOf(100), Currency.JAPANESE_YEN));
	}
	
	
	
}
