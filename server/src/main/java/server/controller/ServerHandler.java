package server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.service.server.ExitCommand;
import server.service.server.LoadCommand;
import server.service.server.SaveCommand;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ServerHandler {

    private final Scanner scanner = new Scanner(System.in);

    private final LoadCommand loadCommand;
    private final SaveCommand saveCommand;
    private final ExitCommand exitCommand;

    public void read() {
        var command = scanner.nextLine();

        if (command.matches("^load\\s+(?<filename>\\S+)$")) {
            loadCommand.execute(command);
        }
        else if (command.matches("^save\\s+(?<filename>\\S+)$")) {
            saveCommand.execute(command);
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
