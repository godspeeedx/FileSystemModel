package Functions;

import Structure.struct.FileSystem;
import Structure.struct.Segment;
import Structure.struct.iCommand;

import java.util.Scanner;

/*
 public String systemName; // имя файловой системы
    public int systemSize; // количество блоков

    public int maxSegmentNum; // максимальное число сегментов
    public ArrayList<Segment> segments; // Массив сегментов
    public int maxDataNum; // Число записей в каждом сегменте(максимальная длина сегмента )
 */
public class ChangeFileSize implements iCommand {

    @Override
    public void Execute(FileSystem fs) {
        boolean check = true;
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя файла");
        String fileName = in.nextLine();
        int oldLength = 0;
        int newLength = 0;
        while (check) {
            System.out.println("Введите новую длину файла");
            newLength = in.nextInt();
            if (newLength < 0 || newLength > fs.maxDataNum) {
                System.out.println("Длина файла некорректна");
            } else {
                check = false;
            }
        }

        if (methodsForFunctions.checkFileExist(fs, fileName) == false) {
            System.out.println("Файл с данным именем не найден");
            return;
        } else {


        }
    }


    @Override
    public void ReadParameters() {

    }
}