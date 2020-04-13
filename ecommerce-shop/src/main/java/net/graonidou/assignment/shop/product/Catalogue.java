package net.graonidou.assignment.shop.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author Eirini Graonidou
 *
 */
interface Catalogue extends CrudRepository<Product, Long> {
	
	Page<Product> findAll(Pageable page);

}
