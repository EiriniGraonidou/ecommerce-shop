/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.graonidou.assignment.shop.commons.BusinessRuntimeException;

@ExtendWith(MockitoExtension.class)
public class OrderShould {
	
	/**
	 * subject under test
	 */
	private Order sut;
	
	@BeforeEach
	void setup() {
		this.sut = new Order();
	}
	
	@Test
	void notCompleteWithEmptyOrderItems() {
		assertThatThrownBy(() -> this.sut.complete())
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("No order items have been added to the order");
	}
	
	@Test
	void onlyCompleteOnce() {
		this.sut.addItem(Mockito.mock(OrderItem.class));
		this.sut.complete();
		assertThatThrownBy(() -> this.sut.complete())
		.isInstanceOf(BusinessRuntimeException.class)
		.hasMessageContaining("The order is already completed");
	}

}
