package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

import java.util.Scanner;

/*
 public String systemName; // имя файловой системы
    public int systemSize; // количество блоков

    public int maxSegmentNum; // максимальное число сегментов
    public ArrayList<Segment> segments; // Массив сегментов
    public int maxDataNum; // Число записей в каждом сегменте(максимальная длина сегмента )
 */
public class ChangeFileSize extends BaseCommand implements iCommand {
    protected String fileName;
    protected int fileLength;

    public ChangeFileSize(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }
    @Override
    public void execute(FileSystem fs) {
     if(DeleteFile.deleteFile(fs, fileName)){
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
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя файла");
        this.fileName = in.nextLine();
        System.out.println("Введите длину файла");
        boolean check = true;
        this.fileLength = 0;
        while (check) {
            System.out.println("Введите новую длину файла");
            this.fileLength = in.nextInt();
            if (this.fileLength < 0 || this.fileLength < FileSystem.systemSize) {
                System.out.println("Длина файла некорректна");
            } else {
                check = false;
            }
        }
    }
}