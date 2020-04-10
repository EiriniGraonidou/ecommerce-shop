package net.graonidou.assignment.shop.stock.web;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;

@Builder
public class ProductStockDto extends RepresentationModel<ProductStockDto> {
	
	public String name;
	public String description;
	public BigDecimal price;
	public long stock;

}
