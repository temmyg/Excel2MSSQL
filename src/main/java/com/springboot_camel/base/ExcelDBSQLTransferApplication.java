package com.springboot_camel.base;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExcelDBSQLTransferApplication {

	public static void main(String[] args) {
		System.out.println("Hello Camel from Spring Boot!!");
		ApplicationContext applicationContext = SpringApplication.run(ExcelDBSQLTransferApplication.class, args);

		CamelSpringBootApplicationController applicationController =
				applicationContext.getBean(CamelSpringBootApplicationController.class);
		applicationController.blockMainThread();
	}
}
