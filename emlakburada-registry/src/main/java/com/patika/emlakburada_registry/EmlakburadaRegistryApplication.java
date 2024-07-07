package com.patika.emlakburada_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EmlakburadaRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmlakburadaRegistryApplication.class, args);
	}

}
