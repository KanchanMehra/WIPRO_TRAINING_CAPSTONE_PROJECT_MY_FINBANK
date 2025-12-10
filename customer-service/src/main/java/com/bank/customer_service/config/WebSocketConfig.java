package com.bank.customer_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * WebSocket configuration for chat functionality
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig {

	@Bean
	public WebSocketMessageBrokerConfigurer webSocketMessageBrokerConfigurer() {
		return new WebSocketMessageBrokerConfigurer() {
			@Override
			public void configureMessageBroker(MessageBrokerRegistry registry) {
				registry.enableSimpleBroker("/topic", "/queue");
				registry.setApplicationDestinationPrefixes("/app");
			}

			@Override
			public void registerStompEndpoints(StompEndpointRegistry registry) {
				registry.addEndpoint("/ws-chat")
						.setAllowedOriginPatterns("*")
						.withSockJS();
			}
		};
	}
}

