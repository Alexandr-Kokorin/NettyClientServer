package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CreateVoteCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        var data = request.body().split("\\|");

        var response = validate(data, request.clientCommand(), request.login());
        if (response != null) return response;

        return make(data, request.clientCommand(), request.login());
    }

    @Override
    protected Response validate(String[] data, String command, String user) {
        Response response;
        if ((response = validationService.validateTopicExists(data[0], command)) != null) return response;
        if ((response = validationService.validateVoteNotExists(data[0], data[1], command)) != null) return response;
        return null;
    }

    @Override
    protected Response make(String[] data, String command, String user) {
        List<String> answers = new ArrayList<>(Arrays.asList(data).subList(3, data.length));
        dataBase.addVote(data[0], data[1], user, data[2], answers);
        return createOkResponse(command, null);
    }
}
