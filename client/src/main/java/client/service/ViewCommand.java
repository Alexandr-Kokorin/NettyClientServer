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
            var topics = response.body().split("\\|");
            for (int i = 0; i < topics.length; i += 2) {
                System.out.println(topics[i] + " - " + topics[i + 1]);
            }
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
