package client.controller;

import client.controller.response.ResponseDecoder;
import client.service.CreateTopicCommand;
import client.service.CreateVoteCommand;
import client.service.DeleteVoteCommand;
import client.service.ViewCommand;
import client.service.ViewTopicCommand;
import client.service.ViewVoteCommand;
import client.service.VoteCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final ResponseDecoder responseDecoder;

    private final CreateTopicCommand createTopicCommand;
    private final ViewCommand viewCommand;
    private final ViewTopicCommand viewTopicCommand;
    private final CreateVoteCommand createVoteCommand;
    private final ViewVoteCommand viewVoteCommand;
    private final VoteCommand voteCommand;
    private final DeleteVoteCommand deleteVoteCommand;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        var response = responseDecoder.decode(msg);
        var command = response.clientCommand();

        if (command.matches("^create\\s+topic\\s+-n=(?<topic>\\w+)$")) {
            createTopicCommand.execute(response);
        } else if (command.matches("^view$")) {
            viewCommand.execute(response);
        } else if (command.matches("^view\\s+-t=(?<topic>\\w+)$")) {
            viewTopicCommand.execute(response);
        } else if (command.matches("^create\\s+vote\\s+-t=(?<topic>\\w+)\\s+-n=(?<number>\\d+)$")) {
            createVoteCommand.execute(response);
        } else if (command.matches("^view\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$")) {
            viewVoteCommand.execute(response);
        } else if (command.matches("^vote\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)\\s+-n=(?<number>\\d+)$")) {
            voteCommand.execute(response);
        } else if (command.matches("^delete\\s+-t=(?<topic>\\w+)\\s+-v=(?<vote>\\w+)$")) {
            deleteVoteCommand.execute(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
