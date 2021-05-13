package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;


public class ChangeFileSize extends BaseCommand implements iCommand {
    protected String fileName;
    protected int fileLength;

    public ChangeFileSize(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static int changeFileSize(FileSystem fs, String name, int length) {
        int check = MethodsForFunctions.takeFileLength(fs,name);
        if(check == length) {
            return 1;
        }
        if (DeleteFile.deleteFile(fs, name)) {
            int isFileCreate = CreateFile.createFile(fs, name, length);
            if (isFileCreate == 0) {
                return 0;
            } else if (isFileCreate == -1) {
                return -1;
            }
        }
        return -2;
    }

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        int create = changeFileSize(fs, this.fileName, this.fileLength);

        int isFileCreate = CreateFile.createFile(fs, this.fileName, this.fileLength);
        if (create == 0) {
            monitor.writeMessage("Размер файла успешно изменён");
        } else if (create == -1) {
            monitor.writeMessage("Изменить размер файла не удалось, " +
                    "так как недостаточно свободного места");
        } else if (create == -2) {
            monitor.writeMessage("Файл не был найден");
        } else if (create == 1){
            monitor.writeMessage("Новая длина файла соответсвует текущей");
        }
        monitor.writeMessage(MethodsForFunctions.saveSystem(fs));

    }


    @Override
    public void readParameters() {
        this.fileName = monitor.readString("Введите имя файла");
        this.fileLength = monitor.readFileSize("Введите длину файла");

    }
}