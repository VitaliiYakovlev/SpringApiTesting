package it;

import client.StompHandler;
import client.WSClient;
import dto.RequestMessageModel;
import dto.ResponseMessageModel;
import mockclient.models.InitModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WsSystemIT {

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private final WSClient wsClient = new WSClient();

    @Test
    void webSocketInternalTest() throws DeploymentException, URISyntaxException, IOException, InterruptedException {
        wsClient.connect("ws://localhost:8081/websocket");
        wsClient.sendMessage("aabbccdd123AA");

        String message = wsClient.getLastMessage(2, TimeUnit.SECONDS);

        assertEquals("AABBCCDD123AA", message);
    }

    @Test
    void webSocketExternalTest()
            throws DeploymentException, URISyntaxException, IOException, InterruptedException {
        wsClient.connect("ws://localhost:8081/subscribe");

        Queue<String> messages = new LinkedList<>();
        messages.add("aaaaaa");
        messages.add("bbbbbb");
        messages.add("cccccc");
        InitModel initModel = new InitModel(500, messages);
        testRestTemplate.postForEntity("http://localhost:10081/init", initModel, String.class);

        String message1 = wsClient.getLastMessage(1, TimeUnit.SECONDS);

        assertEquals("AAAAAA", message1, "check first message");

        String message2 = wsClient.getLastMessage(1, TimeUnit.SECONDS);

        assertEquals("BBBBBB", message2, "check second message");

        String message3 = wsClient.getLastMessage(1, TimeUnit.SECONDS);

        assertEquals("CCCCCC", message3, "check third message");
    }

    @Test
    void stompWsTest() throws ExecutionException, InterruptedException, TimeoutException {
        RequestMessageModel requestMessageModel = new RequestMessageModel("my message", 10);
        ResponseMessageModel exp = new ResponseMessageModel("MY MESSAGE", 25);

        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        BlockingQueue<ResponseMessageModel> blockingQueue = new ArrayBlockingQueue<>(5);
        StompHandler stompHandler = new StompHandler(blockingQueue);

        StompSession session = webSocketStompClient.connect("ws://localhost:8081/chat", new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);
        session.subscribe("/topic/greetings/5", stompHandler);
        session.send("/app/hello/5", requestMessageModel);

        ResponseMessageModel act = blockingQueue.poll(1, TimeUnit.SECONDS);

        assertEquals(exp, act);
    }
}
