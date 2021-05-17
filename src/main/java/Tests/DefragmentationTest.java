package Tests;

import Functions.CreateFile;
import Functions.Defragmentation;
import Functions.DeleteFile;
import Functions.MethodsForFunctions;
import Structure.struct.DataRecord;
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

        expected.get(0).dataRecords.add(new DataRecord("1", 5));
        expected.get(0).dataRecords.add(new DataRecord("4", 6));
        expected.get(1).dataRecords.add(new DataRecord("3", 1));
        expected.get(1).dataRecords.add(new DataRecord("5", 2));
        expected.get(2).dataRecords.add(new DataRecord("6", 3));


        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 2;
        expected.get(2).currentDataNum = 1;
        /*
        зона выполнения кода
         */



        // проверка
        DeleteFile.deleteFile(actual, "2");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        // проверка
        Assert.assertNotEquals(a,b);
        System.out.println("Before " + a+", "+ b);

    }

    @Test
    public void OneFileReplacedWithTwoFiles() {
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
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


        /*
        зона выполнения кода
         */
        DeleteFile.deleteFile(actual, "2");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        // проверка

        Assert.assertNotEquals(a,b);
        System.out.println("Before " + a+", "+ b);
    }

    @Test
    public void SystemIsSqueezed() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
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


        /*
        зона выполнения кода
         */


        DeleteFile.deleteFile(actual, "3");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        // проверка
       // Assert.assertEquals(expected, actual.segments);
        Assert.assertNotEquals(a,b);
        System.out.println("Before " + a+", "+ b);

    }

    @Test
    public void test4() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
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


        /*
        зона выполнения кода
         */

        DeleteFile.deleteFile(actual, "3");
        DeleteFile.deleteFile(actual,"5");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        System.out.println("Before " + a+", "+ b);
        // проверка

        Assert.assertNotEquals(a,b);

    }

    @Test
    public void test5() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
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


        /*
        зона выполнения кода
         */

        DeleteFile.deleteFile(actual, "1");
        DeleteFile.deleteFile(actual,"5");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        System.out.println("Before " + a+", After "+ b);
        // проверка

        Assert.assertNotEquals(a,b);

    }

    @Test
    public void test6() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
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


        /*
        зона выполнения кода
         */

        DeleteFile.deleteFile(actual, "4");
        DeleteFile.deleteFile(actual,"5");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        System.out.println("Before " + a+", "+ b);
        // проверка

        Assert.assertNotEquals(a,b);

    }

    @Test
    public void test7() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
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


        /*
        зона выполнения кода
         */
        DeleteFile.deleteFile(actual, "1");
        DeleteFile.deleteFile(actual, "2");
        DeleteFile.deleteFile(actual, "3");
        DeleteFile.deleteFile(actual,"5");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        System.out.println("Before " + a+", "+ b);
        // проверка

        Assert.assertNotEquals(a,b);

    }

    @Test
    public void test8() {
        FileSystem actual = new FileSystem("Test3", 25, 3, 2);
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


        /*
        зона выполнения кода
         */
        DeleteFile.deleteFile(actual, "1");
        DeleteFile.deleteFile(actual, "2");
        DeleteFile.deleteFile(actual, "3");
        DeleteFile.deleteFile(actual, "4");
        DeleteFile.deleteFile(actual,"5");
        DeleteFile.deleteFile(actual, "6");
        double a = MethodsForFunctions.defragExt(actual);
        Defragmentation.defragmentation(actual);
        double b = MethodsForFunctions.defragExt(actual);
        System.out.println("Before " + a+", After "+ b);
        // проверка

        //Assert.assertEquals(a,b);

    }
}

