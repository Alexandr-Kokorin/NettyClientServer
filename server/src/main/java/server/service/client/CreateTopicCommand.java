package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class CreateTopicCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        var response = validate(new String[]{request.body()}, request.clientCommand(), request.login());
        if (response != null) return response;

        return make(new String[]{request.body()}, request.clientCommand(), request.login());
    }

    @Override
    protected Response validate(String[] data, String command, String user) {
        Response response;
        if ((response = validationService.validateTopicNotExists(data[0], command)) != null) return response;
        return null;
    }

    @Override
    protected Response make(String[] data, String command, String user) {
        dataBase.addTopic(data[0]);
        return createOkResponse(command, null);
    }
}
