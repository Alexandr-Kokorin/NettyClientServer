package client.service;

import client.NettyClient;
import client.controller.ClientHandler;
import client.controller.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class Command {

    @Autowired @Lazy protected NettyClient client;
    @Autowired @Lazy protected ClientHandler clientHandler;
    @Autowired @Lazy protected DataWriter dataWriter;

    public abstract void execute(String command);

    public abstract void execute(Response response);
}
