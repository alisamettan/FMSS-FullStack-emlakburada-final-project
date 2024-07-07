package com.patika.emlakburada_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
public class EmlakburadaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmlakburadaUserApplication.class, args);
	}

}
