package server.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import server.controller.request.Request;
import server.controller.response.Response;
import server.domain.DataBase;

public abstract class ClientCommand {

    @Autowired protected DataBase dataBase;

    public abstract Response execute(Request request);
}
