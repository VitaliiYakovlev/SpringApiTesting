package api;

import api.service.WSClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WsModuleIT {

    private final WSTestClient wsTestClient = new WSTestClient();

    @MockBean
    private WebSocketContainer webSocketContainer;

    @Autowired
    private WSClient wsClient;

    @Value("${local.server.port}")
    private int port;

    @Test
    void webSocketInternalTest() throws DeploymentException, URISyntaxException, IOException, InterruptedException {
        wsTestClient.connect(String.format("ws://localhost:%d/websocket", port));
        wsTestClient.sendMessage("aabbccdd123AA");

        String message = wsTestClient.getLastMessage(2, TimeUnit.SECONDS);

        assertEquals("AABBCCDD123AA", message);
    }

    @Test
    void webSocketExternalTest()
            throws DeploymentException, URISyntaxException, IOException, InterruptedException {
        wsTestClient.connect(String.format("ws://localhost:%d/subscribe", port));

        wsClient.receiveMessage("aaaaaa");
        String message1 = wsTestClient.getLastMessage(2, TimeUnit.SECONDS);

        assertEquals("AAAAAA", message1, "check first message");

        wsClient.receiveMessage("bbbbbb");
        String message2 = wsTestClient.getLastMessage(2, TimeUnit.SECONDS);

        assertEquals("BBBBBB", message2, "check second message");

        wsClient.receiveMessage("cccccc");
        String message3 = wsTestClient.getLastMessage(2, TimeUnit.SECONDS);

        assertEquals("CCCCCC", message3, "check third message");
    }

    @ClientEndpoint
    private class WSTestClient {

        private Session session;
        private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(5);

        public void connect(String uri) throws URISyntaxException, DeploymentException, IOException {
            this.session = ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(uri));
        }

        public void sendMessage(String message) throws IOException {
            session.getBasicRemote().sendText(message);
        }

        @OnMessage
        public void receiveMessage(String message) {
            try {
                messageQueue.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getLastMessage(long timeOut, TimeUnit timeUnit) throws InterruptedException {
            return messageQueue.poll(timeOut, timeUnit);
        }
    }
}
