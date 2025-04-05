package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class GetTopicCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        var response = validate(new String[]{request.body()}, request.clientCommand(), request.login());
        if (response != null) return response;

        return make(new String[]{request.body()}, request.clientCommand(), request.login());
    }

    @Override
    protected Response validate(String[] data, String command, String user) {
        Response response;
        if ((response = validationService.validateTopicExists(data[0], command)) != null) return response;
        return null;
    }

    @Override
    protected Response make(String[] data, String command, String user) {
        var set = dataBase.getTopicSet(data[0]);

        StringBuilder sb = new StringBuilder();
        for (var vote : set) {
            sb.append(vote).append("|");
        }
        if (!set.isEmpty()) sb.deleteCharAt(sb.length() - 1);

        return createOkResponse(command, sb.toString());
    }
}
