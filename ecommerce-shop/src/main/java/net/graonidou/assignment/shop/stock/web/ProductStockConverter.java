package net.graonidou.assignment.shop.stock.web;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import net.graonidou.assignment.shop.product.Product;
import net.graonidou.assignment.shop.stock.ProductStock;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 
 * Converter between the domain model <code>ProductStock</code> and the web representation
 * <code>ProductStockDto</code>
 * 
 * @author Eirini Graonidou
 *
 */
@Component
public class ProductStockConverter extends RepresentationModelAssemblerSupport<ProductStock, ProductStockDto> {

	public ProductStockConverter() {
		super(ProductStockController.class, ProductStockDto.class);
	}

	@Override
	public ProductStockDto toModel(ProductStock productStock) {
		Product product = productStock.getProduct();
		ProductStockDto productStockDto = ProductStockDto.builder()
				.name(product.getName())
				.price(product.getPrice().getAmount())
				.currency(product.getPrice().getCurrency().getCode())
				.stock(productStock.getStock())
				.build();
		
		productStockDto.add(linkTo(methodOn(ProductStockController.class).get(productStock.getId())).withSelfRel());
		return productStockDto;
	}

}
