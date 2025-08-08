package com.api.product_mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hibernate / JPA Homework", version = "1.0", description = "This Spring Boot application provides CRUD operations for managing products using JPA EntityManager. It supports product creation, updating, deletion, retrieval, and pagination."))
public class ProductMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMgmtApplication.class, args);
	}

}
