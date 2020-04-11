package net.graonidou.assignment.shop.stock;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.graonidou.assignement.shop.commons.BusinessRuntimeException;
import net.graonidou.assignment.shop.product.Product;

/**
 * Domain model expressing the stock of the product. 
 * <p>
 * Contains basic information about the product and the amount
 * of the stocked/available items.
 * </p>
 * @author Eirini Graonidou
 *
 */
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "product_stock")
public class ProductStock {
	
	private final static long INITIAL_STOCK_SIZE = 100L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne
	private Product product;
	
	private long stock;
	
	public ProductStock(Product product) {
		this.stock = INITIAL_STOCK_SIZE;
		this.product = product;
	}
	
	
	/**
	 * Reduces the stock by the given amount.
	 * 
	 * @param amount the quantity by to reduce the product. May not be a negative number.
	 *
	 * @return the reduced product stock.
	 */
	ProductStock reduce(long amount) {
		if (amount < 0) {
			throw new BusinessRuntimeException(
					"Error while reducing the product: "+ this.product
					+ ". The amount to reduce must be a positive number");
		}
		if (this.stock - amount < 0) {
			throw new OutOfStock(this.product, amount);
		}
		this.stock = this.stock - amount;
		return this;
	}
	
	/**
	 * Implements the standard product refill;
	 * makes sure that the stock will be set back to its default value.
	 * 
	 * @return the updated <code>ProductStock</code>
	 */
	ProductStock refill() {
		this.stock = this.stock + INITIAL_STOCK_SIZE - this.stock;
		return this;
	}
	
	/**
	 * Makes the refill according to the specified value
	 * 
	 * @param amount the amount based on which the stock will be refilled.
	 * 
	 * @return updated <code>ProductStock</code>
	 */
	ProductStock refill(Long amount) {
		if (amount == null) {
			return refill();
		}
		
		if(amount < 0) {
			throw new BusinessRuntimeException(
					"Error while refilling the product: "+ product
					+ ". The amount to refill must be a positive number");
		}
		
		this.stock = this.stock + amount;
		return this;
	}
	
	
}
