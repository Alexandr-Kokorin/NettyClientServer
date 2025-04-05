package server.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import server.controller.request.Request;
import server.controller.response.Response;
import server.domain.DataBase;

public abstract class ClientCommand {

    @Autowired protected DataBase dataBase;
    @Autowired protected ValidationService validationService;

    public abstract Response execute(Request request);

    protected abstract Response validate(String[] data, String command, String user);

    protected abstract Response make(String[] data, String command, String user);

    protected Response createOkResponse(String command, String body) {
        return Response.builder()
            .status(200)
            .clientCommand(command)
            .body(body)
            .build();
    }
}
