package server.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.controller.response.Response;
import server.domain.DataBase;

@Component
@RequiredArgsConstructor
public class ValidationService {

    private final DataBase dataBase;

    public Response validateTopicExists(String topic, String command) {
        if (!dataBase.isExistTopic(topic)) {
            return error(404, command, "Раздел с таким названием не существует.");
        }
        return null;
    }

    public Response validateVoteExists(String topic, String vote, String command) {
        if (!dataBase.isExistVote(topic, vote)) {
            return error(404, command, "Голосование с таким названием не существует.");
        }
        return null;
    }

    public Response validateAnswerExists(String topic, String vote, String answer, String command) {
        if (!dataBase.isExistAnswer(topic, vote, answer)) {
            return error(404, command, "Такого варианта ответа не существует.");
        }
        return null;
    }

    public Response validateNotVoted(String topic, String vote, String user, String command) {
        if (dataBase.isVoter(topic, vote, user)) {
            return error(409, command, "Вы уже принимали участие в этом голосовании.");
        }
        return null;
    }

    public Response validateIsVoteCreator(String topic, String vote, String user, String command) {
        if (!dataBase.isVoteCreator(topic, vote, user)) {
            return error(409, command, "Вы не являетесь создателем этого голосования.");
        }
        return null;
    }

    public Response validateVoteNotExists(String topic, String vote, String command) {
        if (dataBase.isExistVote(topic, vote)) {
            return error(409, command, "Голосование с таким названием уже существует.");
        }
        return null;
    }

    public Response validateTopicNotExists(String topic, String command) {
        if (dataBase.isExistTopic(topic)) {
            return error(409, command, "Раздел с таким названием уже существует.");
        }
        return null;
    }

    private Response error(int code, String command, String message) {
        return Response.builder()
            .status(code)
            .clientCommand(command)
            .body(message)
            .build();
    }
}
