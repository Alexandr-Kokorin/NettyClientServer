package server.controller.request;

public record Request(
    String serverCommand,
    String clientCommand,
    String login,
    String body
) {
}
