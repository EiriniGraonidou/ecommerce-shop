/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.stock.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.graonidou.assignment.shop.commons.PageDto;
import net.graonidou.assignment.shop.stock.ProductStock;
import net.graonidou.assignment.shop.stock.StockManager;

/**
 * 
 * @author Eirini Graonidou
 *
 */
@RestController
@RequestMapping("/product-stocks")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductStockController {
	
	private final ProductStockConverter productStockConverter;
	private final StockManager stockManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody PageDto<ProductStockDto> getAll(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		
		Page<ProductStock> paginatedContent = stockManager.fetchAll(PageRequest.of(page, size));
		return createPageDto(paginatedContent);
	}
	
	@RequestMapping(path = "/{productStockId}", method = RequestMethod.GET)
	public @ResponseBody ProductStockDto get(@PathVariable("productStockId") Long productStockId) {
		return productStockConverter.toModel(stockManager.fetchById(productStockId));
	}
	
	@RequestMapping(path = "/{productStockId}", method = RequestMethod.PATCH)
	public ProductStockDto refill(
			@PathVariable("productStockId") Long productStockId,
			@RequestBody(required = false) RefillRequestDto refillRequest) {
		
		return productStockConverter.toModel(
				stockManager.refill(productStockId, refillRequest.quantity));
	}
	
	private PageDto<ProductStockDto> createPageDto(Page<ProductStock> paginatedContent) {
		PageDto<ProductStockDto> pageDto = new PageDto<>();
		pageDto.content = this.productStockConverter.toCollectionModel(paginatedContent).getContent();
		pageDto.size = paginatedContent.getSize();
		pageDto.page = paginatedContent.getNumber();
		pageDto.totalElements = paginatedContent.getTotalElements();
		return pageDto;
	}
	
}
