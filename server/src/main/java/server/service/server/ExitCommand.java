package server.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand extends ServerCommand {

    @Autowired @Lazy private ConfigurableApplicationContext context;

    @Override
    public void execute(String command) {
        System.out.println("Завершение работы...");
        context.close();
    }
}
