/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.stock.web;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;

@Builder
public class ProductStockDto extends RepresentationModel<ProductStockDto> {
	
	public Long id;
	public String name;
	public long stock;

}
