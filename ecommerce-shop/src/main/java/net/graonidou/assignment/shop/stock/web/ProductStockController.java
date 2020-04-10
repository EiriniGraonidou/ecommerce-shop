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
@RestController(value = "/products")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductStockController {
	
	private final ProductStockConverter productStockConverter;
	private final StockManager stockManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody PageDto<ProductStockDto> getAll(
			@RequestParam(name = "size", required = false) int size,
			@RequestParam(name = "page", required = false) int page) {
		
		CollectionModel<ProductStockDto> content = productStockConverter.toCollectionModel(
				stockManager.fetchAll(PageRequest.of(page, size)).getContent());
		
		return PageDto.<ProductStockDto>builder()
				.content(content.getContent())
				.size(size)
				.number(page)
				.build();
	}
	
	@RequestMapping(path = "/{productId}", method = RequestMethod.GET)
	public @ResponseBody ProductStockDto get(@PathVariable("productId") Long productId) {
		return productStockConverter.toModel(stockManager.fetchById(productId));
	}
	
	@RequestMapping(path = "/{productId}", method = RequestMethod.PUT)
	public @ResponseBody ProductStockDto refill(
			@PathVariable("productId") Long productId,
			@RequestBody(required = false) long amountToAdd) {
		
		ProductStock stock = stockManager.fetchById(productId);
		return productStockConverter.toModel(stockManager.fetchById(productId));
	}
	
	@Builder
	static class PageDto<RepresentationModel> {
		Collection<RepresentationModel> content;
		int size;
		int number;
	}

	
}
