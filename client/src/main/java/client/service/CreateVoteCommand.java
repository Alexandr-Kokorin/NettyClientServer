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
            case "1" -> execute1(response, matcher);
            case "2" -> execute2(response);
        }
    }

    private void execute1(Response response, Matcher matcher) {
        if (response.status() != 200) {
            System.out.println("Раздел с таким названием не существует.");
            clientHandler.read();
        } else {
            System.out.println("Введите название голосования:");
            var vote = scanner.nextLine();
            System.out.println("Введите тему голосования:");
            var desc = scanner.nextLine();
            System.out.println("Введите количество вариантов ответа:");
            var count = scanner.nextInt();
            System.out.println("Вводите варианты ответа с новой строки:");
            StringBuilder answers = new StringBuilder();
            for (int i = 0; i < count; i++) {
                answers.append("|").append(scanner.nextLine());
            }

            var request = Request.builder()
                .serverCommand("post/vote")
                .clientCommand("create vote -t=" + matcher.group("topic") + " -n=2")
                .login(client.getLogin())
                .body(matcher.group("topic") + "|" + vote + "|" + desc + "|" + count + answers)
                .build();
            client.send(request);
        }
    }

    private void execute2(Response response) {
        if (response.status() == 200) {
            System.out.println("Голосование успешно создано.");
        } else {
            System.out.println(response.body());
        }
        clientHandler.read();
    }
}
