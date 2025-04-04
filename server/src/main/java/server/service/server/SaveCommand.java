package server.service.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import server.domain.DataBase;
import java.io.File;
import java.util.regex.Pattern;

@Component
public class SaveCommand extends ServerCommand {

    @Autowired @Lazy private DataBase dataBase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^save\\s+(?<filename>\\S+)$").matcher(command);
        var matches = matcher.matches();
        DataBase dataBase = new DataBase();
        dataBase.setTopics(this.dataBase.getTopics());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(matcher.group("filename")), dataBase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Данные успешно сохранены!");
        serverHandler.read();
    }
}
