package server.service.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import server.domain.DataBase;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class LoadCommand extends ServerCommand {

    @Autowired @Lazy private DataBase dataBase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^load\\s+(?<filename>\\S+)$").matcher(command);
        var matches = matcher.matches();
        try {
            dataBase.setTopics(objectMapper.readValue(new File(matcher.group("filename")), DataBase.class).getTopics());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Данные успешно загружены!");
        serverHandler.read();
    }
}
