package com.patika.emlakburada_package;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmlakburadaPackageApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmlakburadaPackageApplication.class, args);
	}

}
