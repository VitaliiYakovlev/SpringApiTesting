package mockclient.controller;

import mockclient.models.InitModel;
import mockclient.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    private final MessageSender messageSender;

    @Autowired
    public InitController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping(value = "/init",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String init(@RequestBody InitModel initModel) {
        messageSender.runWorker(initModel);
        return "OK";
    }
}
