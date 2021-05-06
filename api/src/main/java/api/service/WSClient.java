package api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@ClientEndpoint
public class WSClient {

    private final WebSocketContainer webSocketContainer;
    private final SessionService sessionService;
    private final String URL_WS;

    @Autowired
    public WSClient(SessionService sessionService,
                    @Value("${parameter.ws.url.connect}") String urlWs,
                    WebSocketContainer webSocketContainer) {
        this.webSocketContainer = webSocketContainer;
        this.sessionService = sessionService;
        this.URL_WS = urlWs;
    }

    @PostConstruct
    public void connect() throws URISyntaxException, DeploymentException, IOException {
        webSocketContainer.connectToServer(this, new URI(URL_WS));
    }

    @OnMessage
    public void receiveMessage(String message) {
        sessionService.sendMessageToAll(message.toUpperCase());
    }
}
