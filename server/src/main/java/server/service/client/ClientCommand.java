package server.service.client;

import server.controller.request.Request;
import server.controller.response.Response;

public abstract class ClientCommand {

    public abstract Response execute(Request request);
}
