package net.graonidou.assignment.shop.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EntityScan(basePackages = {
	"net.graonidou.assignment.shop.order",
	"net.graonidou.assignment.shop.stock",
	"net.graonidou.assignment.shop.product"
})
@ComponentScan(basePackages = {
	"net.graonidou.assignment.shop.order",
	"net.graonidou.assignment.shop.stock",
	"net.graonidou.assignment.shop.product"
})
@EnableJpaRepositories(basePackages = {
		"net.graonidou.assignment.shop.order",
		"net.graonidou.assignment.shop.stock",
		"net.graonidou.assignment.shop.product"
})
public class ApplicationConfiguration implements WebMvcConfigurer {

}
