package server.service.server;

import org.springframework.stereotype.Component;
import server.domain.DataBase;
import java.io.File;
import java.util.regex.Pattern;

@Component
public class SaveCommand extends ServerCommand {

    @Override
    public void execute(String command) {
        var matcher = Pattern.compile("^save\\s+(?<filename>\\S+)$").matcher(command);
        var matches = matcher.matches();

        DataBase dataBase = new DataBase();
        dataBase.setTopics(this.dataBase.getTopics());
        writeFile(dataBase, matcher.group("filename"));

        System.out.println("Данные успешно сохранены!");
        serverHandler.read();
    }

    private void writeFile(DataBase dataBase, String filename) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), dataBase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
