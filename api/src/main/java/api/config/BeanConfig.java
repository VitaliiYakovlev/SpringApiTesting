package api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(10_000);
        clientHttpRequestFactory.setReadTimeout(10_000);
        return new RestTemplate(clientHttpRequestFactory);
    }
    @Bean
    public WebSocketContainer webSocketContainer () {
         return ContainerProvider.getWebSocketContainer();
    }
}
