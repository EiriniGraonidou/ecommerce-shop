package net.graonidou.assignment.shop.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import net.graonidou.assignment.shop.product.Product;

/**
 * 
 * @author Eirini Graonidou
 *
 */
public interface ProductStockRepository extends CrudRepository<ProductStock, Long>{

	Page<ProductStock> findAll(Pageable page);
	
	ProductStock findByProduct(Product product);
	
}
