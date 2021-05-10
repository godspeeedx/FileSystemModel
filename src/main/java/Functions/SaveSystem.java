package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;
import com.google.gson.Gson;

import java.io.*;

public class SaveSystem extends BaseCommand implements iCommand {

    protected String filename;

    public SaveSystem(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {

        readParameters();
        Gson gson = new Gson();

        String json = gson.toJson(fs);

        System.out.println(json);

        try {
            saveSystem(filename, json);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        monitor.writeMessage("Система сохранена");
    }

    public void saveSystem(String filename, String file) throws IOException {
        FileWriter writer = new FileWriter(filename + ".txt");
        writer.write(file);
        writer.close();
    }

    @Override
    public void readParameters() {
        this.filename = monitor.readString("Введите названия файла для сохранения");
    }
}
