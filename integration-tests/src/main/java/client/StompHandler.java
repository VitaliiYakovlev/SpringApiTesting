package client;

import dto.ResponseMessageModel;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;

public class StompHandler implements StompFrameHandler {

    private final BlockingQueue<ResponseMessageModel> messages;

    public StompHandler(BlockingQueue<ResponseMessageModel> messages) {
        this.messages = messages;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ResponseMessageModel.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ResponseMessageModel message = (ResponseMessageModel) payload;
        messages.add(message);
    }
}
