package client.service;

import client.controller.request.Request;
import client.controller.response.Response;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class DeleteVoteCommand extends Command {

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^delete\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$").matcher(command);
        var matches = matcher.matches();
        var request = Request.builder()
            .serverCommand("delete/votes")
            .clientCommand(command)
            .login(client.getLogin())
            .body(matcher.group("topic") + "|" + matcher.group("vote"))
            .build();
        client.send(request);
    }

    @Override
    public void execute(Response response) {
        if (response.status() == 200) {
            System.out.println("Голосование успешно удалено.");
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
