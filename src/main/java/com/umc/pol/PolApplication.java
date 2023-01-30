package com.umc.pol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PolApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolApplication.class, args);
	}

}
