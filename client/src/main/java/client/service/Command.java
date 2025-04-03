package client.service;

import client.NettyClient;
import client.controller.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class Command {

    private final NettyClient client;

    public abstract void execute(String command);
    public abstract void execute(Response response);
}
