package client.service;

import client.controller.request.Request;
import client.controller.response.Response;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class ViewTopicCommand extends Command {

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^view\\s+-t=(?<topic>\\w+)$").matcher(command);
        var matches = matcher.matches();
        var request = Request.builder()
            .serverCommand("get/topics/name")
            .clientCommand(command)
            .login(client.getLogin())
            .body(matcher.group("topic"))
            .build();
        client.send(request);
    }

    @Override
    public void execute(Response response) {
        if (response.status() == 200) {
            dataWriter.writeTopic(response.body());
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
