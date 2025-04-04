package server.controller;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import server.controller.request.RequestDecoder;
import server.controller.response.Response;
import server.controller.response.ResponseEncoder;

@Log4j2
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final ResponseEncoder responseEncoder;
    private final RequestDecoder requestDecoder;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        var request = requestDecoder.decode(msg);
        ctx.writeAndFlush(responseEncoder.encode(
            Response.builder()
                .status(200)
                .clientCommand(request.clientCommand())
                .build())
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
