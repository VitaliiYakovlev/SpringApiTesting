package client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WSClient {

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
