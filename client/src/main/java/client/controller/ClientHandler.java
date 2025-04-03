package client.controller;

import client.NettyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final NettyClient client;
    private final Scanner scanner = new Scanner(System.in);

    public void read() {
        var string = scanner.nextLine();
        client.getChannel().writeAndFlush(string);
    }
}
