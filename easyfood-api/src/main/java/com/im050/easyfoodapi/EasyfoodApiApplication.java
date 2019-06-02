package com.im050.easyfoodapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.im050")
public class EasyfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyfoodApiApplication.class, args);
	}

}
