package net.graonidou.assignment.shop.stock;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

interface Reservations extends CrudRepository<Reservation, Long> {
	
	Set<Reservation> findAll(Specification<Reservation> spec);
	
	Optional<Reservation> findByOrderItemId(UUID orderItemId);

}
