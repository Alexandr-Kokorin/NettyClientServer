package client.service;

import client.controller.response.Response;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class LoginCommand extends Command {

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^login\\s+-u=(?<username>\\w+)$").matcher(command);
        var matches = matcher.matches();
        client.setLogin(matcher.group("username"));
        System.out.println("Вы успешно авторизовались!");
        clientHandler.read();
    }

    @Override
    public void execute(Response response) {}
}
