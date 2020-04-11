package net.graonidou.assignment.shop.product;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
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
	
	public void loadInitialTestData() {
		add("s1dd3", "super product", Price.of(BigDecimal.valueOf(100), Currency.ARGENTINE_PESO));
		add("q5588", "minimal product", Price.of(BigDecimal.valueOf(100), Currency.POUND_STERLING));
		add("5yz8a", "smooth product", Price.of(BigDecimal.valueOf(100), Currency.EURO));
		add("lkz1q", "awesome product",Price.of(BigDecimal.valueOf(100), Currency.JAPANESE_YEN));
	}
	
	
	
}
