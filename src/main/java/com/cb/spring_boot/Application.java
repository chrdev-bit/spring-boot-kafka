package com.cb.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Running Application.class >>>>>>>>>>>>>");
		SpringApplication.run(Application.class, args);
	}

}
