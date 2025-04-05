package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class VoteCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        var data = request.body().split("\\|");

        var response = validate(data, request.clientCommand(), request.login());
        if (response != null) return response;

        return make(data, request.clientCommand(), request.login());
    }

    protected Response validate(String[] data, String command, String user) {
        Response response;
        if ((response = validationService.validateTopicExists(data[0], command)) != null) return response;
        if ((response = validationService.validateVoteExists(data[0], data[1], command)) != null) return response;
        if ((response = validationService.validateAnswerExists(data[0], data[1], data[2], command)) != null) return response;
        if ((response = validationService.validateNotVoted(data[0], data[1], user, command)) != null) return response;
        return null;
    }

    @Override
    protected Response make(String[] data, String command, String user) {
        dataBase.addAnswer(data[0], data[1], data[2], user);
        return createOkResponse(command, null);
    }
}
