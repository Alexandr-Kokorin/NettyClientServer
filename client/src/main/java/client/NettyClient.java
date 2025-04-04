package client;

import client.controller.request.Request;
import client.controller.request.RequestEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NettyClient {

    private final RequestEncoder requestEncoder;
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    private Channel channel;
    @Setter @Getter private String login;

    @PostConstruct
    public void connect() {
        try {
            System.out.println("Подключение к серверу...");
            var future = bootstrap.connect("localhost", 8080).sync();
            channel = future.channel();
            System.out.println("Соединение успешно установлено!");
        } catch (Exception e) {
            System.out.println("Сервер недоступен, попробуйте позже.");
        }
    }

    @PreDestroy
    public void disconnect() {
        if (channel != null) {
            channel.close();
        }
        eventLoopGroup.shutdownGracefully();
        System.out.println("Завершение работы.");
    }

    public void send(Request request) {
        try {
            channel.writeAndFlush(requestEncoder.encode(request));
        } catch (Exception e) {
            System.out.println("Сервер недоступен, попробуйте позже.");
        }
    }
}
