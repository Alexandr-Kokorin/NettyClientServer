package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    @Getter private Channel channel;

    @PostConstruct
    public void connect(String host, int port) {
        try {
            System.out.println("Подключение к серверу...");
            ChannelFuture future = bootstrap.connect(host, port).sync();
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
}
