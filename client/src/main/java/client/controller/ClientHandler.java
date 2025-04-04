package client.controller;

import client.NettyClient;
import client.service.CreateTopicCommand;
import client.service.CreateVoteCommand;
import client.service.DeleteVoteCommand;
import client.service.ExitCommand;
import client.service.LoginCommand;
import client.service.ViewCommand;
import client.service.ViewTopicCommand;
import client.service.ViewVoteCommand;
import client.service.VoteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final Scanner scanner = new Scanner(System.in);
    private final NettyClient nettyClient;

    private final LoginCommand loginCommand;
    private final CreateTopicCommand createTopicCommand;
    private final ViewCommand viewCommand;
    private final ViewTopicCommand viewTopicCommand;
    private final CreateVoteCommand createVoteCommand;
    private final ViewVoteCommand viewVoteCommand;
    private final VoteCommand voteCommand;
    private final DeleteVoteCommand deleteVoteCommand;
    private final ExitCommand exitCommand;

    public void read() {
        var command = scanner.nextLine();

        if (nettyClient.getLogin() != null) {
            authorizedRead(command);
        }
        else if (command.matches("^login\\s+-u=(?<username>\\w+)$")) {
            loginCommand.execute(command);
        } else {
            System.out.println("Такой команды не существует или вы не авторизованы.");
            read();
        }
    }

    private void authorizedRead(String command) {
        if (command.matches("^create\\s+topic\\s+-n=(?<topic>\\w+)$")) {
            createTopicCommand.execute(command);
        }
        else if (command.matches("^view$")) {
            viewCommand.execute(command);
        }
        else if (command.matches("^view\\s+-t=(?<topic>\\w+)$")) {
            viewTopicCommand.execute(command);
        }
        else if (command.matches("^create\\s+vote\\s+-t=(?<topic>\\w+)$")) {
            createVoteCommand.execute(command);
        }
        else if (command.matches("^view\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$")) {
            viewVoteCommand.execute(command);
        }
        else if (command.matches("^vote\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$")) {
            voteCommand.execute(command);
        }
        else if (command.matches("^delete\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$")) {
            deleteVoteCommand.execute(command);
        }
        else if (command.matches("^exit$")) {
            exitCommand.execute(command);
        }
        else {
            System.out.println("Такой команды не существует.");
            read();
        }
    }
}
