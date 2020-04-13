package net.graonidou.assignment.shop.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.graonidou.assignment.shop.product.Product;


/**
 * 
 * @author Eirini Graonidou
 *
 */
@ExtendWith(MockitoExtension.class)
public class ReservationShould {
	
	/**
	 * subject under test
	 */
	private Reservation sut;
	
	@Test
	void rollback() {
		this.sut = Reservation.builder()
				.amount(10L)
				.createdAt(LocalDateTime.now())
				.orderItemId(UUID.randomUUID())
				.product(mock(Product.class))
				.build();
		
		long result = this.sut.rollback();
		assertThat(result).isEqualTo(this.sut.getAmount());
		assertTrue(this.sut.isRollbacked());
	}

}
