package org.commugram.relay.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class CommugramWebsocketConfigurer implements WebSocketConfigurer {
	
    @Autowired
	CommugramMessageHandler commugramMessageHandler;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(commugramMessageHandler, "/")
        		.setAllowedOrigins("*");
    }

}
