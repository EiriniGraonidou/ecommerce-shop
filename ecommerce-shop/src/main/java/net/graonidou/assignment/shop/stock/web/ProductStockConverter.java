package net.graonidou.assignment.shop.stock.web;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import net.graonidou.assignment.shop.product.Product;
import net.graonidou.assignment.shop.stock.ProductStock;

@Component
public class ProductStockConverter extends RepresentationModelAssemblerSupport<ProductStock, ProductStockDto> {

	public ProductStockConverter(Class<?> controllerClass, Class<ProductStockDto> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public ProductStockDto toModel(ProductStock productStock) {
		Product product = productStock.getProduct();
		return ProductStockDto.builder()
				.description(product.getDescription())
				.name(product.getName())
				.price(product.getPrice())
				.stock(productStock.getStock())
				.build();
				
	}

}
