package client.service;

import client.controller.request.Request;
import client.controller.response.Response;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CreateVoteCommand extends Command {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^create\\s+vote\\s+-t=(?<topic>\\w+)$").matcher(command);
        var matches = matcher.matches();
        var request = Request.builder()
            .serverCommand("get/topics/name")
            .clientCommand(command + " -n=1")
            .login(client.getLogin())
            .body(matcher.group("topic"))
            .build();
        client.send(request);
    }

    @Override
    public void execute(Response response) {
        var matcher = Pattern.compile("^create\\s+vote\\s+-t=(?<topic>\\w+)\\s+-n=(?<number>\\d+)$").matcher(response.clientCommand());
        var matches = matcher.matches();
        switch (matcher.group("number")) {
            case "1" -> executeFirst(response, matcher);
            case "2" -> executeSecond(response);
        }
    }

    private void executeFirst(Response response, Matcher matcher) {
        if (response.status() == 200) {
            executeFirstOk(matcher);
        } else {
            executeFirstError();
        }
    }

    private void executeFirstOk(Matcher matcher) {
        var data = read();
        var request = Request.builder()
            .serverCommand("post/votes")
            .clientCommand("create vote -t=" + matcher.group("topic") + " -n=2")
            .login(client.getLogin())
            .body(matcher.group("topic") + "|" + data)
            .build();
        client.send(request);
    }

    private String read() {
        System.out.println("Введите название голосования:");
        var vote = scanner.nextLine();
        System.out.println("Введите тему голосования:");
        var desc = scanner.nextLine();
        System.out.println("Введите количество вариантов ответа:");
        var count = Integer.parseInt(scanner.nextLine());

        System.out.println("Вводите варианты ответа с новой строки:");
        StringBuilder answers = new StringBuilder();
        for (int i = 0; i < count; i++) {
            answers.append("|").append(scanner.nextLine());
        }
        return vote + "|" + desc + answers;
    }

    private void executeFirstError() {
        System.out.println("Раздел с таким названием не существует.");
        clientHandler.read();
    }

    private void executeSecond(Response response) {
        if (response.status() == 200) {
            System.out.println("Голосование успешно создано.");
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
