package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class CreateVoteCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        return null;
    }
}
