package server.configuration;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import server.controller.NettyServerHandler;

@Configuration
@RequiredArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<Channel> {

    private final NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(nettyServerHandler);
    }
}
