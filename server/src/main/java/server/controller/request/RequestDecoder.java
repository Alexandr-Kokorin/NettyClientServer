package server.controller.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RequestDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Request decode(String request) {
        try {
            return objectMapper.readValue(request, Request.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
