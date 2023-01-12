package com.divary.carsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CarSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSearchServiceApplication.class, args);
	}

}
