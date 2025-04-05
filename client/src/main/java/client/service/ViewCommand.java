package client.service;

import client.controller.request.Request;
import client.controller.response.Response;
import org.springframework.stereotype.Component;

@Component
public class ViewCommand extends Command {

    @Override
    public void execute(String command) {
        var request = Request.builder()
            .serverCommand("get/topics")
            .clientCommand(command)
            .login(client.getLogin())
            .build();
        client.send(request);
    }

    @Override
    public void execute(Response response) {
        if (response.status() == 200) {
            dataWriter.writeTopics(response.body());
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
