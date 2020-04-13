package net.graonidou.assignment.shop.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.graonidou.assignment.shop.commons.BusinessRuntimeException;
import net.graonidou.assignment.shop.product.Product;

@ExtendWith(MockitoExtension.class)
class ProductStockShould {

	private ProductStock sut;
	
	@BeforeEach
	void setup() {
		this.sut = new ProductStock(Mockito.mock(Product.class)); 
		
	}
	
	@Test
	void notRefillWhenAmountNegative() {
		assertThatThrownBy(() -> this.sut.refill(-10L))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("Error while refilling the product");
	}
	
	@Test
	void refillToStandardForUndefinedAmount() {
		this.sut.refill(null);
		assertThat(this.sut.getStock()).isEqualTo(ProductStock.INITIAL_STOCK_SIZE);
	}
	
	@Test
	void refillForGivenAmount() {
		long amountToRefill = 20L;
		this.sut.refill(amountToRefill);
		assertThat(this.sut.getStock()).isEqualTo(ProductStock.INITIAL_STOCK_SIZE + amountToRefill);
	}
	
	@Test
	void notReduceWhenAmountNegative() {
		assertThatThrownBy(() -> this.sut.reduce(-10L))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("Error while reducing the product:");
	}
	
	@Test
	void notReduceWhenAmountIsOutOfStock() {
		assertThatThrownBy(() -> this.sut.reduce(10000L))
		.isInstanceOf(OutOfStock.class)
		.hasMessageContaining("Insufficient stock");
	}
	

}
