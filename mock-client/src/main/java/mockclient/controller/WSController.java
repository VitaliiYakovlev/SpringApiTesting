package mockclient.controller;

import mockclient.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WSController extends TextWebSocketHandler {

    @Autowired
    private MessageSender messageSender;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        messageSender.addSession(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        messageSender.removeSession(session);
        super.afterConnectionClosed(session, status);
    }
}
