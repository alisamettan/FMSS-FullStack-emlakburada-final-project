package com.patika.emlakburada_advert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EmlakburadaAdvertApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmlakburadaAdvertApplication.class, args);
	}

}
