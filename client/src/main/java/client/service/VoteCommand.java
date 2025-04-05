package client.service;

import client.controller.request.Request;
import client.controller.response.Response;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VoteCommand extends Command {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^vote\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$").matcher(command);
        var matches = matcher.matches();
        var request = Request.builder()
            .serverCommand("get/votes/name")
            .clientCommand(command + " -n=1")
            .login(client.getLogin())
            .body(matcher.group("topic") + "|" + matcher.group("vote"))
            .build();
        client.send(request);
    }

    @Override
    public void execute(Response response) {
        var matcher = Pattern.compile("^vote\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)\\s+-n=(?<number>\\d+)$").matcher(response.clientCommand());
        var matches = matcher.matches();
        switch (matcher.group("number")) {
            case "1" -> executeFirst(response, matcher);
            case "2" -> executeSecond(response);
        }
    }

    private void executeFirst(Response response, Matcher matcher) {
        if (response.status() == 200) {
            executeFirstOk(response.body(), matcher);
        } else {
            executeFirstError(response.body());
        }
    }

    private void executeFirstOk(String body, Matcher matcher) {
        dataWriter.writeVote(body);
        var answer = read();
        var request = Request.builder()
            .serverCommand("post/vote")
            .clientCommand("vote -t=" + matcher.group("topic") + " -v=" + matcher.group("vote") + " -n=2")
            .login(client.getLogin())
            .body(matcher.group("topic") + "|" + matcher.group("vote") + "|" + answer)
            .build();
        client.send(request);
    }

    private String read() {
        System.out.println("Ваш выбор:");
        return scanner.nextLine();
    }

    private void executeFirstError(String body) {
        System.out.println(body);
        clientHandler.read();
    }

    private void executeSecond(Response response) {
        if (response.status() == 200) {
            System.out.println("Вы успешно проголосовали.");
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
