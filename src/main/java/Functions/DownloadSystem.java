package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadSystem extends BaseCommand implements iCommand {

    protected String systemName;

    public DownloadSystem(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {
        readParameters();

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT);

        Gson gson = gsonBuilder.create();

        try {
            String json = downloadSystem(this.systemName);
            FileSystem buf = gson.fromJson(json, FileSystem.class);
            fs.copy(buf);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        monitor.writeMessage("Система загружена!");

    }

    public String downloadSystem(String systemName) throws IOException {

        return Files.readString(Paths.get(systemName));

    }

    @Override
    public void readParameters() {
        this.systemName = monitor.readString("Введите название системы для загрузки:");
    }
}