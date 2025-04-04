package client.controller.request;

import lombok.Builder;

@Builder
public record Request(
    String serverCommand,
    String clientCommand,
    String login,
    String body
) {
}
