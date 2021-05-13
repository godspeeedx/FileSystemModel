package Tests;
import Functions.CreateFile;
import Functions.Defragmentation;
import Functions.DeleteFile;
import Structure.struct.Data;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class DefragmentationTest {

    @Test
    public void OneFileReplacedWithOneFile() {
        FileSystem actual = new FileSystem("Test1", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование дефрагментации: " + "\n");


        /*
        Заполнение данных
         */
        CreateFile.createFile(actual, "1", 5);
        CreateFile.createFile(actual, "2", 6);
        CreateFile.createFile(actual, "3", 1);
        CreateFile.createFile(actual, "4", 6);
        CreateFile.createFile(actual, "5", 2);
        CreateFile.createFile(actual, "6", 3);

        /* заполняем сегменты как они будут после дефрагментации */
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.add(new Segment(1));

        expected.get(0).datas.add(new Data("1", 5));
        expected.get(0).datas.add(new Data("4", 6));
        expected.get(1).datas.add(new Data("3", 1));
        expected.get(1).datas.add(new Data("5", 2));
        expected.get(2).datas.add(new Data("6", 3));

        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 2;
        expected.get(2).currentDataNum = 1;
        /*
        зона выполнения кода
         */
        DeleteFile.deleteFile(actual,"2");
        Defragmentation.defragmentation(actual);

        // проверка
        Assert.assertEquals(expected, actual.segments);

    }

    @Test
    public void OneFileReplacedWithTwoFiles() {
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование дефрагментации: " + "\n");


        /*
        Заполнение данных
         */
        CreateFile.createFile(actual, "1", 5);
        CreateFile.createFile(actual, "2", 5);
        CreateFile.createFile(actual, "3", 1);
        CreateFile.createFile(actual, "4", 6);
        CreateFile.createFile(actual, "5", 2);
        CreateFile.createFile(actual, "6", 3);

        /* заполняем сегменты как они будут после дефрагментации */
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.add(new Segment(1));

        expected.get(0).datas.add(new Data("1", 5));
        expected.get(0).datas.add(new Data("5", 2));
        expected.get(1).datas.add(new Data("6", 3));
        expected.get(1).datas.add(new Data("3",1));
        expected.get(2).datas.add(new Data("4", 6));


        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 2;
        expected.get(2).currentDataNum = 1;
        /*
        зона выполнения кода
         */
        DeleteFile.deleteFile(actual,"2");
        Defragmentation.defragmentation(actual);

        // проверка
        Assert.assertEquals(expected, actual.segments);
    }

    @Test
    public void SystemIsSqueezed() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование дефрагментации: " + "\n");


        /*
        Заполнение данных
         */
        CreateFile.createFile(actual, "1", 5);
        CreateFile.createFile(actual, "2", 5);
        CreateFile.createFile(actual, "3", 1);
        CreateFile.createFile(actual, "4", 6);
        CreateFile.createFile(actual, "5", 2);
        CreateFile.createFile(actual, "6", 3);

        /* заполняем сегменты как они будут после дефрагментации */
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.add(new Segment(1));

        expected.get(0).datas.add(new Data("1", 5));
        expected.get(0).datas.add(new Data("2", 6));
        expected.get(1).datas.add(new Data("4", 6));
        expected.get(1).datas.add(new Data("5", 2));
        expected.get(2).datas.add(new Data("6", 3));

        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 2;
        expected.get(2).currentDataNum = 1;
        /*
        зона выполнения кода
         */
        DeleteFile.deleteFile(actual,"3");
        Defragmentation.defragmentation(actual);

        // проверка
        Assert.assertEquals(expected, actual.segments);

    }
}
