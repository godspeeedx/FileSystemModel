package Tests;

import Functions.CreateFile;
import Functions.DeleteFile;
import Functions.Print;
import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrintTest {

    //Проверка, когда система пуста и не заполнялась
    @Test
    public void check1(){
        //Проверка функции Print
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        //Проверка, когда система пуста и не заполнялась
        System.out.println("- Вывод инфорамции, когда файловая система пуста и она не заполнялась");
        expected = "Файловая система пуста";
        Assert.assertEquals(expected, Print.toString(actual));
    }

    // Проверка, когда система пуста и заполнялась до этого
    @Test
    public void check2(){
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        // Проверка, когда система пуста и заполнялась до этого
        System.out.println("- Вывод инфорамции, когда файловая система пуста и она заполнялась");
        expected = "Файловая система пуста";
        CreateFile.createFile(actual, "first", 1);
        DeleteFile.deleteFile(actual, "first");
        Assert.assertEquals(expected, Print.toString(actual));
    }

    // Проверка, когда добавлен только один элемент
    @Test
    public void check3(){
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        System.out.println("- Вывод инфорамции, когда добавлен один элемент");
        CreateFile.createFile(actual, "first", 1);
        expected = "Сегмент " + 0 + "\n" + "first" +
                " " + 1 + "\n";
        Assert.assertEquals(expected, Print.toString(actual));

    }


    // Проверка, когда больше одного сегмента
    @Test
    public void check4(){
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        CreateFile.createFile(actual, "first", 1);
        expected = "Сегмент " + 0 + "\n" + "first" +
                " " + 1 + "\n";
        System.out.println("- Вывод инфорамции, когда в системе больше одного сегмента");
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected += "second" + " " + 2 + "\n" + "Сегмент " + 1 + "\n" + "third" + " " + 3 + "\n";
        Assert.assertEquals(expected, Print.toString(actual));

    }

    //Проверка, когда в системе есть удалённые элементы
    @Test
    public void check5(){
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        System.out.println("- Вывод инфорамции, когда в системе есть удалённые элементы");
        DeleteFile.deleteFile(actual, "second");
        expected = "Сегмент " + 0 + "\n" + "first" +
                " " + 1 + "\n" + "Сегмент " + 1 + "\n" + "third" + " " + 3 + "\n";
        Assert.assertEquals(expected, Print.toString(actual));
    }


    //Проверка, когда есть пустой сегмент
    @Test
    public void check6(){
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        DeleteFile.deleteFile(actual, "second");
        System.out.println("- Вывод инфорамции, когда в системе есть пустой сегмент");
        expected = "Сегмент " + 0 + "\n" + "Сегмент " + 1 + "\n" + "third" + " " + 3 + "\n";
        DeleteFile.deleteFile(actual, "first");
        Assert.assertEquals(expected, Print.toString(actual));
    }

    /*
    @Test
    public void testToString() {
        //Проверка функции Print
        System.out.println("Тестирование функции вывода всех данных в системе: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        String expected = "";
        //Проверка, когда система пуста и не заполнялась
        System.out.println("- Вывод инфорамции, когда файловая система пуста и она не заполнялась");
        expected = "Файловая система пуста";
        Assert.assertEquals(expected, Print.toString(actual));

        // Проверка, когда система пуста и заполнялась до этого
        System.out.println("- Вывод инфорамции, когда файловая система пуста и она заполнялась");
        expected = "Файловая система пуста";
        CreateFile.createFile(actual, "first", 1);
        DeleteFile.deleteFile(actual, "first");
        Assert.assertEquals(expected, Print.toString(actual));

        // Проверка, когда добавлен только один элемент
        System.out.println("- Вывод инфорамции, когда добавлен один элемент");
        CreateFile.createFile(actual, "first", 1);
        expected = "Сегмент " + 0 + "\n" + "first" +
                " " + 1 + "\n";
        Assert.assertEquals(expected, Print.toString(actual));

        // Проверка, когда больше одного сегмента
        System.out.println("- Вывод инфорамции, когда в системе больше одного сегмента");
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected += "second" + " " + 2 + "\n" + "Сегмент " + 1 + "\n" + "third" + " " + 3 + "\n";
        Assert.assertEquals(expected, Print.toString(actual));

        //Проверка, когда в системе есть удалённые элементы
        System.out.println("- Вывод инфорамции, когда в системе есть удалённые элементы");
        DeleteFile.deleteFile(actual, "second");
        expected = "Сегмент " + 0 + "\n" + "first" +
                " " + 1 + "\n" + "Сегмент " + 1 + "\n" + "third" + " " + 3 + "\n";
        Assert.assertEquals(expected, Print.toString(actual));

        //Проверка, когда есть пустой сегмент
        System.out.println("- Вывод инфорамции, когда в системе есть пустой сегмент");
        expected = "Сегмент " + 0 + "\n" + "Сегмент " + 1 + "\n" + "third" + " " + 3 + "\n";
        DeleteFile.deleteFile(actual, "first");
        Assert.assertEquals(expected, Print.toString(actual));


    }*/
}