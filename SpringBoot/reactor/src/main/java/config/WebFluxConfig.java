package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import websocket.WebFluxWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class WebFluxConfig {

    @Autowired
    private WebFluxWebSocketHandler handler;

    @Bean
    public HandlerMapping handlerMapping(){
        Map<String, WebFluxWebSocketHandler> handlerMap =new HashMap<>();
        handlerMap.put("/uppercase", handler);
        return new SimpleUrlHandlerMapping(handlerMap, 1);
    }

}