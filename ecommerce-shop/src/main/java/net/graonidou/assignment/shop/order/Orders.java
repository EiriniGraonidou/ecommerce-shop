/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.order;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author Eirini Graonidou
 *
 */
interface Orders extends CrudRepository<Order, Long> {

}
