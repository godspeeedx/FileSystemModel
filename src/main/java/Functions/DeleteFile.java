package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;


public class DeleteFile extends BaseCommand implements iCommand {

    protected String fileName;

    public DeleteFile(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static boolean deleteFile(FileSystem fs, String fileName) {
        boolean findName = false;
        flag:
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                if (fs.segments.get(i).datas.get(j).getName().equals(fileName)) {
                    fs.segments.get(i).datas.get(j).type = false;
                    findName = true;
                    break flag;
                }
            }
        }
        return findName;
    }

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        if (deleteFile(fs, this.fileName)) {
            monitor.writeMessage("Файл удалён");
        } else {
            monitor.writeMessage("Файл не был найден");
        }
        monitor.writeMessage(MethodsForFunctions.saveSystem(fs));
    }

    @Override
    public void readParameters() {
        this.fileName = monitor.readString("Введите имя файла");
    }
}
