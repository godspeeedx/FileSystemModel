package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

import java.util.Scanner;

public class ChangeFileSize extends BaseCommand implements iCommand {
    protected String fileName;
    protected int fileLength;

    public ChangeFileSize(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        if (DeleteFile.deleteFile(fs, fileName)) {
            int isFileCreate = CreateFile.createFile(fs, this.fileName, this.fileLength);
            if (isFileCreate == 0) {
                monitor.writeMessage("Файл успешно создан");
            } else if (isFileCreate == -1) {
                monitor.writeMessage("Недостаточно свободного места");
            } else if (isFileCreate == 1) {
                monitor.writeMessage("Файл уже существует");
            }
        } else {
            monitor.writeMessage("Файл не был найден");
        }

    }


    @Override
    public void readParameters() {
        this.fileName = monitor.readString("Введите имя файла");
        this.fileLength = monitor.readFileSize("Введите длину файла");

    }
}