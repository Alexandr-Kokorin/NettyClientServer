package client.controller.response;

public record Response(
    int status,
    String clientCommand,
    String body
) {
}
