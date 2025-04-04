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
            .serverCommand("get/vote")
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
            case "1" -> execute1(response, matcher);
            case "2" -> execute2(response);
        }
    }

    private void execute1(Response response, Matcher matcher) {
        if (response.status() == 200) {
            var vote = response.body().split("\\|");
            System.out.println(vote[0]);
            for (int i = 1; i < vote.length; i += 2) {
                System.out.println(vote[i] + " - " + vote[i + 1]);
            }
            System.out.println("Ваш выбор:");
            var ans = scanner.nextLine();

            var request = Request.builder()
                .serverCommand("post/vote")
                .clientCommand("vote -t=" + matcher.group("topic") + " -v=" + matcher.group("vote") + " -n=2")
                .login(client.getLogin())
                .body(matcher.group("topic") + "|" + matcher.group("vote") + "|" + ans)
                .build();
            client.send(request);
        } else {
            System.out.println(response.body());
            clientHandler.read();
        }
    }

    private void execute2(Response response) {
        if (response.status() == 200) {
            System.out.println("Вы успешно проголосовали.");
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
