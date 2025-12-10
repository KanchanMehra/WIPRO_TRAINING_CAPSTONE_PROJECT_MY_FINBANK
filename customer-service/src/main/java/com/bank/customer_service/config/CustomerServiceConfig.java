package com.bank.customer_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for customer service
 */
@Configuration
public class CustomerServiceConfig {

	/**
	 * RestTemplate bean for making REST API calls to other microservices
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

