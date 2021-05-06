package api.controller;

import api.dto.RequestMessageDto;
import api.dto.ResponseMessageDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {


    @MessageMapping("/hello/{userId}")
    @SendTo("/topic/greetings/{userId}")
    public ResponseMessageDto greeting(@Payload RequestMessageDto message, @DestinationVariable String userId) {
        return new ResponseMessageDto(message.getMsg().toUpperCase(), message.getId() * 2 + Integer.parseInt(userId));
    }
}
