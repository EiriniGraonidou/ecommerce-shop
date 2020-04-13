/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/

package net.graonidou.assignment.shop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.graonidou.assignment.shop.commons.BusinessRuntimeException;
import net.graonidou.assignment.shop.order.Order.Status;
import net.graonidou.assignment.shop.product.Product;

/**
 * 
 * @author Eirini Graonidou
 *
 */
@ExtendWith(MockitoExtension.class)
public class OrderManagerShould {
	
	@InjectMocks
	private OrderManager sut;
	
	@Mock
	private Orders orders;
	
	@Test
	void throwExceptionForUnknownOrderId() {
		final Long unknownOrderId = 1L;
		Mockito.when(this.orders.findById(unknownOrderId)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> this.sut.fetchById(unknownOrderId))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("The requested resource could not be found");
	}
	
	@Test
	void notAddItemsToUnknownOrder() {
		final Long unknownOrderId = 1L;
		Mockito.when(this.orders.findById(unknownOrderId)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> this.sut.addItems(unknownOrderId, new ArrayList<OrderItem>()))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("The requested resource could not be found");
	}
	
	@Test
	void notAaddEmptyOrderItems() {
	
		long knownId = 2L;
		Order mocked = Mockito.mock(Order.class);
		Mockito.when(this.orders.findById(knownId))
				.thenReturn(Optional.of(mocked));
		
		Mockito.doCallRealMethod().when(mocked).addItems(new ArrayList<>());
		
		assertThatThrownBy(() -> this.sut.addItems(knownId, new ArrayList<>()))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("Cannot add items. The input list is empty");
	}
	
	@Test
	void notCompleteUnknownOrder() {
		final Long unknownOrderId = 1L;
		Mockito.when(this.orders.findById(unknownOrderId)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> this.sut.complete(unknownOrderId))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("The requested resource could not be found");
	}
	
	@Test
	void notCompleteCompletedOrder() {
		final Long known = 1L;
		Order completedOrder = new Order();
		completedOrder.setId(1L);
		completedOrder.setOrderItems(Set.of(
				new OrderItem(Mockito.mock(Product.class), 10)));
		completedOrder.setStatus(Status.COMPLETED);
		
		Mockito.when(this.orders.findById(known)).thenReturn(Optional.of(completedOrder));
		
		assertThatThrownBy(() -> this.sut.complete(known))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("Cannot complete. The order is already completed");
	}
	
	@Test
	void notCompleteEmptyOrder() {
		final Long known = 1L;
		Order knownOrder = new Order();
		knownOrder.setId(1L);
		
		Mockito.when(this.orders.findById(known)).thenReturn(Optional.of(knownOrder));
		
		assertThatThrownBy(() -> this.sut.complete(known))
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("Cannot complete order. No order items have been added to the order");
	}
	
	
	@Test
	void completeValidOrder() {
		final Long known = 1L;
		Order knownOrder = new Order();
		knownOrder.setId(1L);
		knownOrder.setOrderItems(Set.of(
				new OrderItem(Mockito.mock(Product.class), 10)));
		
		Mockito.when(this.orders.findById(known)).thenReturn(Optional.of(knownOrder));
		
		this.sut.complete(known);
		assertThat(knownOrder.getStatus()).isEqualTo(Status.COMPLETED);
	}
	
	

}
