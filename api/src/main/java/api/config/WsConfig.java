package api.config;

import api.controller.WsHandler;
import api.controller.WsHandlerSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(wsHandler(), "/websocket")
                .addHandler(wsHandlerSubscriber(), "/subscribe")
                .setAllowedOrigins("*");
    }

    @Bean
    public WsHandler wsHandler() {
        return new WsHandler();
    }

    @Bean
    public WsHandlerSubscriber wsHandlerSubscriber() {
        return new WsHandlerSubscriber();
    }
}
