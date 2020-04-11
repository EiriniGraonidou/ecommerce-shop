package net.graonidou.assignment.shop.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EntityScan(basePackages = {
	"net.graonidou.assignment.shop.order",
	"net.graonidou.assignment.shop.stock",
	"net.graonidou.assignment.shop.product"
})
@ComponentScan(basePackages = {
	"net.graonidou.assignement.shop.commons",
	"net.graonidou.assignment.shop.order",
	"net.graonidou.assignment.shop.stock",
	"net.graonidou.assignment.shop.product"
})
@EnableJpaRepositories(basePackages = {
		"net.graonidou.assignment.shop.order",
		"net.graonidou.assignment.shop.stock",
		"net.graonidou.assignment.shop.product"
})
@EnableSwagger2
public class ApplicationConfiguration implements WebMvcConfigurer {
	
	public static final int PAGE_SIZE = 10;
	public static final int DEFAULT_PAGE = 0;
	
	@Bean
	public LinkDiscoverers discovers() {

		List<LinkDiscoverer> plugins = new ArrayList<>();
		plugins.add(new CollectionJsonLinkDiscoverer());
		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
		
	}
	  @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build();                                           
	    }

}
