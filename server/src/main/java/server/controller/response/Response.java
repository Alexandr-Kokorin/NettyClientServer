package server.controller.response;

import lombok.Builder;

@Builder
public record Response(
    int status,
    String clientCommand,
    String body
) {
}
