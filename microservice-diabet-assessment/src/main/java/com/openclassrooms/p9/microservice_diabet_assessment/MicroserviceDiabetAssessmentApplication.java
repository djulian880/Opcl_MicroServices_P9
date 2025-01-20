package com.openclassrooms.p9.microservice_diabet_assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.openclassrooms.p9.microservice_diabet_assessment")
public class MicroserviceDiabetAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDiabetAssessmentApplication.class, args);
	}

}
