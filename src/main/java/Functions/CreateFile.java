package Functions;

import Structure.struct.Data;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import Structure.struct.iCommand;

import java.util.Scanner;

public class CreateFile implements iCommand {

    public static int createFile(FileSystem fs, String filename, int length) {
        boolean check = MethodsForFunctions.checkFileExist(fs, filename);
        if (check = true) {
            return 1; // файл существует
        } else {

            if (MethodsForFunctions.checkPossibleAdd(fs, length)) {
                if (MethodsForFunctions.checkAddInLastSegment(fs, length)) {
                    int segmentSize = fs.segments.size() - 1;
                    fs.segments.get(segmentSize).datas.add(new Data(filename, length));
                    fs.segments.get(segmentSize).currentDataNum += 1;
                    Segment.lastBlockNumber += length;
                    return 0; // файл создан
                } else {
                    if (fs.segments.size() < fs.maxSegmentNum) {
                        fs.segments.add(new Segment(fs.maxDataNum));
                        int segmentSize = fs.segments.size() - 1;
                        //Segment.lastBlockNumber = segmentSize * Segment.lengthSegment;
                        fs.segments.get(segmentSize).datas.add(new Data(filename, length));
                        fs.segments.get(segmentSize).currentDataNum += 1;
                        Segment.lastBlockNumber += length;
                        return 0;
                    } else {
                        return -1; // Нет места для записи файла
                    }
                }

            }
            return -1; // Нет места для записи файла
        }
    }

    @Override
    public void execute(FileSystem fs) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя файла");
        String filename = in.nextLine();
        System.out.println("Введите длину файла");
        boolean check = true;
        int length = 0;
        while (check) {
            System.out.println("Введите новую длину файла");
            length = in.nextInt();
            if (length < 0 || length > fs.maxDataNum) {
                System.out.println("Длина файла некорректна");
            } else {
                check = false;
            }
        }
        int isFileCreate = createFile(fs, filename, length);
        if (isFileCreate == 0) {
            System.out.println("Файл успешно создан");
        } else if (isFileCreate == -1) {
            System.out.println("Недостаточно свободного места");
        } else if (isFileCreate == 1) {
            System.out.println("Файл уже существует");
        }
    }

    @Override
    public void readParameters() {
    }
}
