/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.graonidou.assignment.shop.product.ProductManager;

@SpringBootApplication
@EnableScheduling
public class EcommerceShopApplication implements CommandLineRunner {

	@Autowired
	private ProductManager productManager;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productManager.loadInitialTestData();
		
	}

}
