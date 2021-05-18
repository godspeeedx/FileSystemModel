package Tests;

import Functions.ChangeFileSize;
import Functions.CreateFile;
import Structure.struct.DataRecord;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ChangeFileSizeTest {

    // Изменим размер файла, когда новый размер больше предыдущего и памяти хватает
    @Test
    public void check1() {
        //Создадим  файловую систему
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");
        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        System.out.println("- Изменение размера файла, при условии," +
                " что новый размер больше предыдущего и памяти хватает");
        expected.get(0).dataRecords.get(0).type = false;
        expected.get(1).dataRecords.add(new DataRecord("first", 4));
        ChangeFileSize.changeFileSize(actual, "first", 4);
        expected.get(0).currentDataNum = 1;
        expected.get(1).currentDataNum = 2;
        Assert.assertEquals(expected, actual.segments);
    }

    // Изменим размер файла, когда новый размер меньше предыдущего и памяти хватает
    @Test
    public void check2() {
        //Создадим  файловую систему
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");
        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        expected.get(1).dataRecords.add(new DataRecord("first", 4));
        ChangeFileSize.changeFileSize(actual, "first", 4);
        // Проверим нашу систему
        System.out.println("- Изменение размера файла, при условии" +
                " что новый размер меньше предыдущего и памяти хватает");
        expected.get(1).dataRecords.get(0).type = false;
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 1;
        expected.get(0).dataRecords.get(0).name = "third";
        expected.get(0).dataRecords.get(0).type = true;
        expected.get(0).dataRecords.get(0).size = 1;
        ChangeFileSize.changeFileSize(actual, "third", 1);
        Assert.assertEquals(expected, actual.segments);
    }


    @Test
    //   Изменим размер файла, когда новый размер совпадает со старым
    public void check3() {
        //Создадим  файловую систему
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");
        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        expected.get(1).dataRecords.add(new DataRecord("first", 4));
        ChangeFileSize.changeFileSize(actual, "first", 4);
        System.out.println("- Изменение размера файла, при условии" +
                " что новый размер меньше предыдущего и памяти хватает");
        expected.get(1).dataRecords.get(0).type = false;
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 1;
        expected.get(0).dataRecords.get(0).name = "third";
        expected.get(0).dataRecords.get(0).type = true;
        expected.get(0).dataRecords.get(0).size = 1;
        ChangeFileSize.changeFileSize(actual, "third", 1);
        // Проверим нашу систему
        System.out.println("- Неуспешная попытка изменения, при условии" +
                " что новый размер совпадает с предыдущем размером файла");
        ChangeFileSize.changeFileSize(actual, "second", 2);
        Assert.assertEquals(expected, actual.segments);
    }


    @Test
    // Изменим размер больше и памяти нет
    public void check4() {
//Создадим  файловую систему
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");

        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 1;
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        System.out.println("- Неуспешная попытка изменения размера файла, при условии" +
                " что новый размер больше предыдущего и свободной памяти нет");
        ChangeFileSize.changeFileSize(actual, "first", 23);
        Assert.assertEquals(expected, actual.segments);
    }

    // Изменим размер когда свободная память есть, но свободных сегментов нет
    @Test
    public void check5(){
        FileSystem actual = new FileSystem("Test2", 25, 2, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");

        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        CreateFile.createFile(actual, "forth", 4);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 2;
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        expected.get(1).dataRecords.add(new DataRecord("forth",4));
        System.out.println("- Неуспешная попытка изменения размера файла, при условии" +
                " что свободная память есть, но позиции для этого файла нет");
        ChangeFileSize.changeFileSize(actual, "first", 10);
        Assert.assertEquals(expected, actual.segments);
    }

    // Изменяем длину файла, когда его нет
    @Test
    public void check6(){
        FileSystem actual = new FileSystem("Test2", 25, 2, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");

        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 1;
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        System.out.println("- Неуспешная попытка изменения размера файла, которого не сущетсвует");
        ChangeFileSize.changeFileSize(actual, "dsffsd", 4);
        Assert.assertEquals(expected, actual.segments);
    }

    /*@Test
    public void changeFileSizeTest() {
        //Создадим  файловую систему
        FileSystem actual = new FileSystem("Test2", 25, 3, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        System.out.println("Тестирование функции изменения размера файла: " + "\n");

        //Заполним нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 1));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 1;
        expected.get(1).dataRecords.add(new DataRecord("third", 3));

        // Изменим размер файла, когда новый размер больше предыдущего и памяти хватает
        System.out.println("- Изменение размера файла, при условии," +
                " что новый размер больше предыдущего и памяти хватает");
        expected.get(0).dataRecords.get(0).type = false;
        expected.get(1).dataRecords.add(new DataRecord("first", 4));
        ChangeFileSize.changeFileSize(actual, "first", 4);
        expected.get(0).currentDataNum = 1;
        expected.get(1).currentDataNum = 2;
        Assert.assertEquals(expected, actual.segments);

        // Изменим размер файла, когда новый размер меньше предыдущего и памяти хватает
        System.out.println("- Изменение размера файла, при условии" +
                " что новый размер меньше предыдущего и памяти хватает");
        expected.get(1).dataRecords.get(0).type = false;
        expected.get(0).currentDataNum = 2;
        expected.get(1).currentDataNum = 1;
        expected.get(0).dataRecords.get(0).name = "third";
        expected.get(0).dataRecords.get(0).type = true;
        expected.get(0).dataRecords.get(0).size = 1;
        ChangeFileSize.changeFileSize(actual, "third", 1);
        Assert.assertEquals(expected, actual.segments);

        //  Изменим размер файла, когда новый размер совпадает со старым
        System.out.println("- Неуспешная попытка изменения, при условии" +
                " что новый размер совпадает с предыдущем размером файла");
        ChangeFileSize.changeFileSize(actual, "second", 2);
        Assert.assertEquals(expected, actual.segments);

        // Изменим размер больше и памяти нет
        System.out.println("- Неуспешная попытка изменения размера файла, при условии" +
                " что новый размер больше предыдущего и свободной памяти нет");
        ChangeFileSize.changeFileSize(actual, "second", 24);
        expected.get(0).currentDataNum -= 1;
        expected.get(0).dataRecords.get(1).type = false;
        Assert.assertEquals(expected, actual.segments);

        // Изменим размер когда свободная память есть, но свободных сегментов нет
        System.out.println("- Неуспешная попытка изменения размера файла, при условии" +
                " что свободная память есть, но позиции для этого файла нет");
        expected.add(new Segment(2));
        expected.get(2).dataRecords.add(new DataRecord("5", 4));
        expected.get(2).currentDataNum = 1;
        expected.get(2).dataRecords.add(new DataRecord("6", 4));
        expected.get(2).dataRecords.get(0).type = false;
        CreateFile.createFile(actual, "5", 4);
        CreateFile.createFile(actual, "6", 4);
        ChangeFileSize.changeFileSize(actual, "5", 5);
        Assert.assertEquals(expected, actual.segments);

        //Изменяем длину файла, когда его нет
        System.out.println("- Неуспешная попытка изменения размера файла, которого не сущетсвует");
        ChangeFileSize.changeFileSize(actual, "had;kcvjln", 5);
        Assert.assertEquals(expected, actual.segments);
    } */

}