package net.graonidou.assignment.shop.stock;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.graonidou.assignment.shop.product.Product;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	
	private Long orderItemId;
	
	@OneToOne
	private Product product;
	
	private long amount;
	
	private boolean isRollbacked;
	
	@Builder
	public Reservation(
			Long orderItemId,
			LocalDateTime createdAt, 
			Product product, 
			long amount, 
			LocalDateTime expiresAt) {
		this.orderItemId = orderItemId;
		this.createdAt = createdAt;
		this.product = product;
		this.amount = amount;
		this.expiresAt = expiresAt;
	}
	
	
	/**
	 * Marks the reservation as rolled back.
	 * 
	 * @return the product quantity that were reserved.
	 */
	long rollback() {
		this.isRollbacked = true;
		return this.amount;
	}

}
