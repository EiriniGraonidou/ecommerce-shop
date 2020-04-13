/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.product.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import net.graonidou.assignment.shop.product.Product;

/**
 * Converter between a <code>Product</code> and the product representation

 * @author Eirini Graonidou
 *
 */
@Component
public class ProductConverter extends RepresentationModelAssemblerSupport<Product, ProductDto> {

	ProductConverter() {
		super(ProductController.class, ProductDto.class);
	}

	public ProductDto toModel(Product product) {
		ProductDto productDto = ProductDto.builder()
				.name(product.getName())
				.code(product.getCode())
				.currency(product.getPrice().getCurrency().getCode())
				.price(product.getPrice().getAmount())
				.build();
		productDto.add(linkTo(methodOn(ProductController.class).get(product.getId())).withSelfRel());
		return productDto;
	}

}
