package client.service;

import client.controller.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand extends Command {

    @Autowired @Lazy private ConfigurableApplicationContext context;

    @Override
    public void execute(String command) {
        context.close();
    }

    @Override
    public void execute(Response response) {}
}
