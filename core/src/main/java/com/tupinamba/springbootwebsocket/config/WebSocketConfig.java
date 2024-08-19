package com.tupinamba.springbootwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String WEBSOCKET_ENDPOINT = "/websocket";
    private static final String[] BROKER_DESTINATIONS = {"/topic", "/queue"};
    private static final String APPLICATION_DESTINATION_PREFIX = "/app";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WEBSOCKET_ENDPOINT);
        System.out.println("Websocket configurado");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Configura o broker para destinos de mensagens com suporte para tópicos e filas
        registry.enableSimpleBroker(BROKER_DESTINATIONS);
        // Define o prefixo para os destinos mapeados pelos controladores
        registry.setApplicationDestinationPrefixes(APPLICATION_DESTINATION_PREFIX);
        System.out.println("Chegou mensagem");
    }
}
