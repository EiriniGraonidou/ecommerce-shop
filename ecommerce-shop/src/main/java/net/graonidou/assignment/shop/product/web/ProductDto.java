/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.product.web;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;

/**
 * Representation of a product
 * 
 * @author Eirini Graonidou
 *
 */
@Builder
public class ProductDto extends RepresentationModel<ProductDto> {
	
	public String name;
	public String code;
	public String description;
	public BigDecimal price;
	public String currency;

}
