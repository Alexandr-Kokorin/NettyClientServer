package server.controller;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import server.controller.request.RequestDecoder;
import server.controller.response.ResponseEncoder;
import server.service.client.CreateTopicCommand;
import server.service.client.CreateVoteCommand;
import server.service.client.DeleteVoteCommand;
import server.service.client.GetTopicCommand;
import server.service.client.GetTopicsCommand;
import server.service.client.GetVoteCommand;
import server.service.client.VoteCommand;

@Log4j2
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final ResponseEncoder responseEncoder;
    private final RequestDecoder requestDecoder;

    private final CreateTopicCommand createTopicCommand;
    private final CreateVoteCommand createVoteCommand;
    private final DeleteVoteCommand deleteVoteCommand;
    private final VoteCommand voteCommand;
    private final GetTopicsCommand getTopicsCommand;
    private final GetTopicCommand getTopicCommand;
    private final GetVoteCommand getVoteCommand;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        var request = requestDecoder.decode(msg);
        log.info("Received request: {}", request);
        var response = switch (request.serverCommand()) {
            case "post/topics" -> createTopicCommand.execute(request);
            case "post/votes" -> createVoteCommand.execute(request);
            case "delete/votes" -> deleteVoteCommand.execute(request);
            case "post/vote" -> voteCommand.execute(request);
            case "get/topics" -> getTopicsCommand.execute(request);
            case "get/topics/name" -> getTopicCommand.execute(request);
            case "get/votes/name" -> getVoteCommand.execute(request);
            default -> throw new IllegalStateException("Unexpected value: " + request.serverCommand());
        };
        log.info("Sending response: {}", response);
        ctx.writeAndFlush(responseEncoder.encode(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
