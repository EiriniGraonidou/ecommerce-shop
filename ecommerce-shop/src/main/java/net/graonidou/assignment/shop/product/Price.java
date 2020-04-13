/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * Representation model of price. Consists of the amount/quantity of money
 * and a subset of supported currencies defined according to ISO 4217.
 * 
 * @author Eirini Graonidou
 *
 */
@Entity
@Table(name = "prices")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private BigDecimal amount;
	private Currency currency;
	
	public static Price of(BigDecimal amount, Currency currency) {
		Price price = new Price();
		price.amount = amount;
		price.currency = currency;
		return price;
	}
	
	@AllArgsConstructor
	@Getter
	public enum Currency {
		EURO("EUR",978),
		UNITED_STATES_DOLLAR("USD", 840),
		POUND_STERLING("GBP", 826),
		RUSSIAN_RUBLE("RUB", 643),
		ARGENTINE_PESO("ARS", 032),
		JAPANESE_YEN("JPY", 392);
		
		private String code;
		private int numericCode;
		
	}
	

}
