package com.microservicios.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // En versiones actuales, reemplaza a @EnableEurekaClient
@EnableFeignClients
public class ClienteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteServiceApplication.class, args);
	}

}
