package com.five9.openbanking.aisp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AispDemoApplication {
    public static Map<String, String> statePaymentCache = new HashMap<>();
	public static void main(String[] args) {

		SpringApplication.run(AispDemoApplication.class, args);

	}
}
