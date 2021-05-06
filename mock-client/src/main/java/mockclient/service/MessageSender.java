package mockclient.service;

import mockclient.models.InitModel;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageSender {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public void runWorker(InitModel initModel) {
        Queue<String> messages = initModel.getMessage();
        long delay = initModel.getDelay();
        while (!messages.isEmpty()) {
            if (!sessions.isEmpty()) {
                String message = messages.remove();
                sessions.stream()
                        .filter(WebSocketSession::isOpen)
                        .forEach(session -> sendMessage(session, message));
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            sessions.remove(session);
        }
    }
}
