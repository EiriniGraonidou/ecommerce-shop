/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.graonidou.assignment.shop.commons.PageDto;
import net.graonidou.assignment.shop.product.Product;
import net.graonidou.assignment.shop.product.ProductManager;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductController {
	
	private final ProductManager productManager;
	private final ProductConverter productConverter;
	
	@ApiOperation(value = "Get a list of available products", response = PageDto.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list")
	})
	@RequestMapping(method = RequestMethod.GET)
	public PageDto<ProductDto> get(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		
		Page<Product> paginatedProducts = productManager.findAll(PageRequest.of(page, size, Sort.by("name")));
		return createPageDto(paginatedProducts);
	}
	
	@ApiOperation(value = "Get details of a selected product", response = ProductDto.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved product"),
	        @ApiResponse(code = 404, message = "A product with the given identifier cannot be found")
	})
	@RequestMapping(path = "/{productId}", method = RequestMethod.GET)
	public ProductDto get(@PathVariable("productId") Long productId) {
		return this.productConverter.toModel(productManager.findById(productId));
	}
	
	private PageDto<ProductDto> createPageDto(Page<Product> paginatedProducts) {
		PageDto<ProductDto> pageDto = new PageDto<>();
		pageDto.content = this.productConverter.toCollectionModel(paginatedProducts).getContent();
		pageDto.size = paginatedProducts.getSize();
		pageDto.page = paginatedProducts.getNumber();
		pageDto.totalElements = paginatedProducts.getTotalElements();
		return pageDto;
	}

}
