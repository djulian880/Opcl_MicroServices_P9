package com.openclassrooms.p9.microservice_notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient

@SpringBootApplication
public class MicroserviceNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceNotesApplication.class, args);
	}

}
