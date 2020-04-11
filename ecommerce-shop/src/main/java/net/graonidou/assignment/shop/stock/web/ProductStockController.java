package net.graonidou.assignment.shop.stock.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
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
	public @ResponseBody CollectionModel<ProductStockDto> getAll(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		
		CollectionModel<ProductStockDto> content = productStockConverter.toCollectionModel(
				stockManager.fetchAll(PageRequest.of(page, size)).getContent());
		
		return content;
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
	
	@Builder
	static class PageDto<RepresentationModel> {
		Collection<RepresentationModel> content;
		int size;
		int number;
	}

	
}
