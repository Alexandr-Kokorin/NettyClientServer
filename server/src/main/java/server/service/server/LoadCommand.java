package server.service.server;

import org.springframework.stereotype.Component;
import server.domain.DataBase;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class LoadCommand extends ServerCommand {

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^load\\s+(?<filename>\\S+)$").matcher(command);
        var matches = matcher.matches();

        dataBase.setTopics(readFile(matcher.group("filename")).getTopics());

        System.out.println("Данные успешно загружены!");
        serverHandler.read();
    }

    private DataBase readFile(String filename) {
        try {
            return objectMapper.readValue(new File(filename), DataBase.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
