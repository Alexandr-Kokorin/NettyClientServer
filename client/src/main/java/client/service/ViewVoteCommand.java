package client.service;

import client.controller.request.Request;
import client.controller.response.Response;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class ViewVoteCommand extends Command {

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^view\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$").matcher(command);
        var matches = matcher.matches();
        var request = Request.builder()
            .serverCommand("get/vote")
            .clientCommand(command)
            .login(client.getLogin())
            .body(matcher.group("topic") + "|" + matcher.group("vote"))
            .build();
        client.send(request);
    }

    @Override
    public void execute(Response response) {
        if (response.status() == 200) {
            var vote = response.body().split("\\|");
            System.out.println(vote[0]);
            for (int i = 1; i < vote.length; i += 2) {
                System.out.println(vote[i] + " - " + vote[i + 1]);
            }
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
