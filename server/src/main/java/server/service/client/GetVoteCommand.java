package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class GetVoteCommand extends ClientCommand {

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
        if ((response = validationService.validateVoteExists(data[0], data[1], command)) != null) return response;
        return null;
    }

    @Override
    protected Response make(String[] data, String command, String user) {
        var desc = dataBase.getVoteDesc(data[0], data[1]);
        var map = dataBase.getVoteMap(data[0], data[1]);

        StringBuilder sb = new StringBuilder();
        sb.append(desc).append("|");
        for (var vote : map.keySet()) {
            sb.append(vote).append("|").append(map.get(vote)).append("|");
        }
        sb.deleteCharAt(sb.length() - 1);

        return createOkResponse(command, sb.toString());
    }
}
