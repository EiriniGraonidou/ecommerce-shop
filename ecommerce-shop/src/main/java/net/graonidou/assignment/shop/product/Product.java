package net.graonidou.assignment.shop.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Eirini Graonidou
 *
 */
@Getter
@Entity(name = "product")
public class Product extends AbstractAggregateRoot<Product> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String code;
	private String name;
	private String description;
	
	private BigDecimal price;
	

	@Builder(access = AccessLevel.PACKAGE, builderMethodName = "with")
	public Product(String code, String name, String description, BigDecimal price) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		
		registerEvent(ProductAdded.of(this));
	}
	
	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
	public static class ProductAdded {
		
		Product product;
		
	}

}
