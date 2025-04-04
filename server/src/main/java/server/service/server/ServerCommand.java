package server.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import server.controller.ServerHandler;

public abstract class ServerCommand {

    @Autowired @Lazy protected ServerHandler serverHandler;

    public abstract void execute(String command);

}
