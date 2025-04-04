package client.controller.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ResponseDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Response decode(String response) {
        try {
            return objectMapper.readValue(response, Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
