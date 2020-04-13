/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.product;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

/**
 * 
 * Product is the core of the e-commerce shop. In this model the most
 * important global characteristics are being defined.
 * 
 * @author Eirini Graonidou
 *
 */
@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(of = {"id", "name"})
public class Product extends AbstractAggregateRoot<Product> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String code;
	private String name;
	
	@OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
	private Price price;
	

	@Builder(access = AccessLevel.PACKAGE, builderMethodName = "with")
	public Product(String code, String name, String description, Price price) {
		this.code = code;
		this.name = name;
		this.price = price;
		
		registerEvent(ProductAdded.of(this));
	}
	
	/**
	 * Domain event indicating that a new product has arrived into our e commerce shop.
	 * 
	 * @author Eirini Graonidou
	 *
	 */
	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
	public static class ProductAdded {
		
		Product product;
		
	}

}
