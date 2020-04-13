/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/

package net.graonidou.assignment.shop.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.graonidou.assignment.shop.commons.BusinessRuntimeException;
import net.graonidou.assignment.shop.product.Product;

/**
 * 
 * @author Eirini Graonidou
 *
 */
@ExtendWith(MockitoExtension.class)
public class StockManagerShould {
	
	/**
	 * subject under test
	 */
	@InjectMocks
	private StockManager sut;
	
	@Mock
	private ProductStockRepository productStockRepository;
	
	@Test
	void throwExceptionForUnknownStockId() {
		final Long unknownStockId = 1L;
		Mockito.when(this.productStockRepository.findById(unknownStockId)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> this.sut.fetchById(unknownStockId))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("No product stock for id:");
	}
	
	@Test
	void fetchKnownStock() {
		final Long known = 1L;
		Mockito.when(this.productStockRepository.findById(known)).thenReturn(Optional.of(Mockito.mock(ProductStock.class)));
		ProductStock stock = this.sut.fetchById(known);
		assertThat(stock).isNotNull();
	}
	
	@Test
	void notRefillUnknownStock() {
		final Long unknownStockId = 1L;
		Mockito.when(this.productStockRepository.findById(unknownStockId)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> this.sut.refill(unknownStockId, 10L))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("No product stock for id:");
	}
	
	@Test
	void refillValidAmount() {
		final Long known = 1L;
		final long amountToRefill = 10L;
		ProductStock stock = new ProductStock(Mockito.mock(Product.class));
		Mockito.when(this.productStockRepository.findById(known))
		.thenReturn(Optional.of(stock));
		Mockito.when(productStockRepository.save(stock)).thenReturn(stock);
		this.sut.refill(known, amountToRefill);
		
		assertThat(stock.getStock()).isEqualTo(ProductStock.INITIAL_STOCK_SIZE + amountToRefill);
	}
	
	
}
