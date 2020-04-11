package net.graonidou.assignment.shop.stock.web;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;

@Builder
public class ProductStockDto extends RepresentationModel<ProductStockDto> {
	
	public String name;
	public BigDecimal price;
	public String currency;
	public long stock;

}
