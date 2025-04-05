package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class GetTopicsCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        return make(new String[]{}, request.clientCommand(), request.login());
    }

    protected Response validate(String[] data, String command, String user) {
        return null;
    }

    @Override
    protected Response make(String[] data, String command, String user) {
        var map = dataBase.getTopicsMap();

        StringBuilder sb = new StringBuilder();
        for (var key : map.keySet()) {
            sb.append(key).append("|").append(map.get(key)).append("|");
        }
        if (!map.isEmpty()) sb.deleteCharAt(sb.length() - 1);

        return  createOkResponse(command, sb.toString());
    }
}
