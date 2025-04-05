package server.service.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import server.controller.ServerHandler;
import server.domain.DataBase;

public abstract class ServerCommand {

    @Autowired @Lazy protected ServerHandler serverHandler;
    @Autowired @Lazy protected DataBase dataBase;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    public abstract void execute(String command);

}
