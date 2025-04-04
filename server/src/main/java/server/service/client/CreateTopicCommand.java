package server.service.client;

import org.springframework.stereotype.Component;
import server.controller.request.Request;
import server.controller.response.Response;

@Component
public class CreateTopicCommand extends ClientCommand {

    @Override
    public Response execute(Request request) {
        if (dataBase.isExistTopic(request.body())) {
            return Response.builder()
                .status(409)
                .clientCommand(request.clientCommand())
                .body("Раздел с таким названием уже существует.")
                .build();
        }

        dataBase.addTopic(request.body());
        return Response.builder()
            .status(200)
            .clientCommand(request.clientCommand())
            .build();
    }
}
