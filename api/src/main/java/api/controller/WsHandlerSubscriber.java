package api.controller;

import api.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WsHandlerSubscriber extends TextWebSocketHandler {

    @Autowired
    private SessionService sessionService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionService.addSession(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionService.removeSession(session);
        super.afterConnectionClosed(session, status);

    }
}
