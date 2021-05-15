package Tests;

import Functions.CreateFile;
import Functions.DeleteFile;
import Structure.struct.DataRecord;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CreateFileTest {

    // добавим первый элемент
    @Test
    public void check1() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим первый элемент
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).currentDataNum = 1;
        CreateFile.createFile(actual, "first", 4);
        System.out.println("- Добавление в систему первого элемента");
        Assert.assertEquals(expected, actual.segments);
    }


    //добавление эллементов, чтобы максимально заполнить сегмент
    @Test
    public void check2() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).currentDataNum = 1;
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        System.out.println("- Добавление эллементов, полностью заполняющих сегмент");
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        Assert.assertEquals(expected, actual.segments);
    }


    //Добавление элементов, заполняющих более одного сегмента
    @Test
    public void check3() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).currentDataNum = 1;
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        System.out.println("- Добавление элементов, заполняющих более одного сегмента ");
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        Assert.assertEquals(expected, actual.segments);
    }

    // добавим элемент, который есть в системе
    @Test
    public void check4() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        System.out.println("- Неуспешная попытка добавления в систему элеменета " +
                "с именем, совпадающим с именем элемента, уже имеющегося в системе");
        CreateFile.createFile(actual, "forth", 5);
        Assert.assertEquals(expected, actual.segments);
    }

    //добавим элемент, который больше свободного места
    @Test
    public void check5() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        System.out.println("- Неуспешная попытка добавления в систему элемента, размер которого больше" +
                "свободного места в системе");
        CreateFile.createFile(actual, "fifth", 19);
        Assert.assertEquals(expected, actual.segments);
    }

    // Добавим элемент на место удалённого
    @Test
    public void check6() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        System.out.println("- Добавление элемента в систему на место удалённого");
        DeleteFile.deleteFile(actual, "second");
        CreateFile.createFile(actual, "newsecond", 2);
        expected.get(0).dataRecords.set(1, new DataRecord("newsecond", 2));
        Assert.assertEquals(expected, actual.segments);
    }

    // Добавим элемент меньший чем удалённый, если после него идёт пустой
    @Test
    public void check7() {
//Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        System.out.println("- Добавление в систему элемента, размер которого меньше, чем размер " +
                "удалённого ранее файла, при том что, после этого файла идёт удалённый файл");
        DeleteFile.deleteFile(actual, "second"); // был 2
        DeleteFile.deleteFile(actual, "first"); // был 4
        CreateFile.createFile(actual, "newfirst", 2);
        expected.get(0).dataRecords.get(1).type = false;
        expected.get(0).dataRecords.set(0, new DataRecord("newfirst", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(0).dataRecords.get(1).size = 4;
        Assert.assertEquals(expected, actual.segments);
    }

    // когда добавляли прошлый, в след место увеличилось, добавим туда элемент
    @Test
    public void check8() {
//Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        DeleteFile.deleteFile(actual, "second"); // был 2
        DeleteFile.deleteFile(actual, "first"); // был 4
        CreateFile.createFile(actual, "newfirst", 2);
        expected.get(0).dataRecords.get(1).type = false;
        expected.get(0).dataRecords.set(0, new DataRecord("newfirst", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(0).dataRecords.get(1).size = 4;
        System.out.println("- Добавление в систему элемента на место удалённого файла, размер которого был " +
                "увеличен из-за добавления в предыдущей итарации добавления");
        expected.get(0).dataRecords.get(1).name = "newSecond2";
        expected.get(0).dataRecords.get(1).type = true;
        expected.get(0).currentDataNum = 3;
        CreateFile.createFile(actual, "newSecond2", 4);
        Assert.assertEquals(expected, actual.segments);

    }


    //добавим элемент, который займёт всю память
    @Test
    public void check9() {
//Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        DeleteFile.deleteFile(actual, "second"); // был 2
        DeleteFile.deleteFile(actual, "first"); // был 4
        CreateFile.createFile(actual, "newfirst", 2);
        expected.get(0).dataRecords.get(1).type = false;
        expected.get(0).dataRecords.set(0, new DataRecord("newfirst", 2));
        expected.get(0).dataRecords.get(1).size = 4;
        expected.get(0).dataRecords.get(1).name = "newSecond2";
        expected.get(0).dataRecords.get(1).type = true;
        expected.get(0).currentDataNum = 3;
        CreateFile.createFile(actual, "newSecond2", 4);
        System.out.println("- Добавление в систему элемента, размер которого равен размеру свободной памяти");
        CreateFile.createFile(actual, "fifth", 7);
        expected.get(1).dataRecords.add(new DataRecord("fifth", 7));
        expected.get(1).currentDataNum = 2;
        Assert.assertEquals(expected, actual.segments);
    }

    // попробуем добавить ещё один элемент, когда вся память занята
    @Test
    public void check10() {
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        ArrayList<Segment> expected = new ArrayList<>();
        // добавим элементы
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(1).dataRecords.add(new DataRecord("third", 3));
        CreateFile.createFile(actual, "first", 4);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        CreateFile.createFile(actual, "forth", 4);
        expected.get(0).currentDataNum = 2;
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 2;
        System.out.println("- Неуспешная попытка добавления ещё одного элемента, когда свободной памяти нет");
        CreateFile.createFile(actual, "sadfsvdd", 1);
        Assert.assertEquals(expected, actual.segments);
    }

    // Полностью заполним систему
    @Test
    public void check11() {
//Создадим ещё одну файловую систему
        FileSystem actual2 = new FileSystem("Test2", 11, 2, 2);
        ArrayList<Segment> expected2 = new ArrayList<>();

        // Добавить во всю систему
        System.out.println("- Полное заполнение файловой системы");
        CreateFile.createFile(actual2, "first", 1);
        CreateFile.createFile(actual2, "second", 2);
        CreateFile.createFile(actual2, "third", 3);
        CreateFile.createFile(actual2, "forth", 4);
        expected2.add(new Segment(2));
        expected2.add(new Segment(2));
        expected2.get(0).dataRecords.add(new DataRecord("first", 1));
        expected2.get(0).dataRecords.add(new DataRecord("second", 2));
        expected2.get(0).currentDataNum = 2;
        expected2.get(1).dataRecords.add(new DataRecord("third", 3));
        expected2.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected2.get(1).currentDataNum = 2;
        Assert.assertEquals(expected2, actual2.segments);
        System.out.println("P.S. Система зполнена в связи с тем, что все сегменты заполнены (т.е записано" +
                "максимальное количество файлов в сегментах)");
    }

    // попробовать добавить ещё один элемент, когда все сегменты в системе заполнены
    @Test
    public void check12() {
//Создадим ещё одну файловую систему
        FileSystem actual2 = new FileSystem("Test2", 11, 2, 2);
        ArrayList<Segment> expected2 = new ArrayList<>();

        // Добавить во всю систему
        System.out.println("- Неуспешная попытка добавления ещё одного элемента, когда свободного сегмента нет");
        CreateFile.createFile(actual2, "first", 1);
        CreateFile.createFile(actual2, "second", 2);
        CreateFile.createFile(actual2, "third", 3);
        CreateFile.createFile(actual2, "forth", 4);
        expected2.add(new Segment(2));
        expected2.add(new Segment(2));
        expected2.get(0).dataRecords.add(new DataRecord("first", 1));
        expected2.get(0).dataRecords.add(new DataRecord("second", 2));
        expected2.get(0).currentDataNum = 2;
        expected2.get(1).dataRecords.add(new DataRecord("third", 3));
        expected2.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected2.get(1).currentDataNum = 2;
        CreateFile.createFile(actual2, "ggvj", 1);
        Assert.assertEquals(expected2, actual2.segments);
    }

    //Так как система заполнена, но память ещё есть, удалим последний и добавим больший файл
    @Test
    public void check13() {
//Создадим ещё одну файловую систему
        FileSystem actual2 = new FileSystem("Test2", 19, 2, 2);
        ArrayList<Segment> expected2 = new ArrayList<>();

        CreateFile.createFile(actual2, "first", 1);
        CreateFile.createFile(actual2, "second", 2);
        CreateFile.createFile(actual2, "third", 3);
        CreateFile.createFile(actual2, "forth", 4);
        expected2.add(new Segment(2));
        expected2.add(new Segment(2));
        expected2.get(0).dataRecords.add(new DataRecord("first", 1));
        expected2.get(0).dataRecords.add(new DataRecord("second", 2));
        expected2.get(0).currentDataNum = 2;
        expected2.get(1).dataRecords.add(new DataRecord("third", 3));
        expected2.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected2.get(1).currentDataNum = 2;
        System.out.println("P.S. Система зполнена в связи с тем, что все сегменты заполнены (т.е записано" +
                "максимальное количество файлов в сегментах)");
    }

    //система заполнена, удалим последний и добавим меньший
    @Test
    public void check14() {
        FileSystem actual2 = new FileSystem("Test2", 11, 2, 2);
        ArrayList<Segment> expected2 = new ArrayList<>();

        CreateFile.createFile(actual2, "first", 1);
        CreateFile.createFile(actual2, "second", 2);
        CreateFile.createFile(actual2, "third", 3);
        CreateFile.createFile(actual2, "forth", 4);
        expected2.add(new Segment(2));
        expected2.add(new Segment(2));
        expected2.get(0).dataRecords.add(new DataRecord("first", 1));
        expected2.get(0).dataRecords.add(new DataRecord("second", 2));
        expected2.get(0).currentDataNum = 2;
        expected2.get(1).dataRecords.add(new DataRecord("third", 3));
        expected2.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected2.get(1).currentDataNum = 2;
        //система заполнена, удалим последний и добавим меньший
        System.out.println("- Удаление из полностью заполненной системы последнего файла и " +
                "добавление файла, размер которого меньше размера удалённого");
        DeleteFile.deleteFile(actual2, "forth");
        CreateFile.createFile(actual2, "forth", 3);
        expected2.get(1).dataRecords.get(1).size = 3;
        Assert.assertEquals(expected2, actual2.segments);
    }

   /* @Test
    public void createFile() {
        //Создали одну файловую систему
        System.out.println("Тестирование функции добавления файла в систему: " + "\n");
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();

        // добавим первый элемент
        expected.add(new Segment(3));
        expected.get(0).dataRecords.add(new DataRecord("first", 4));
        expected.get(0).currentDataNum = 1;
        CreateFile.createFile(actual, "first", 4);
        System.out.println("- Добавление в систему первого элемента");
        Assert.assertEquals(expected, actual.segments);

        // добавим ещё 2 элемента
        expected.get(0).dataRecords.add(new DataRecord("second", 2));
        expected.get(0).dataRecords.add(new DataRecord("third", 3));
        System.out.println("- Добавление в систему ещё двух элементов");
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        Assert.assertEquals(expected, actual.segments);

        //добавим 4-й эллемент
        System.out.println("- Добавление в систему 4-го элемента, который должен быть записан в новый сегмент");
        expected.add(new Segment(3));
        expected.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        Assert.assertEquals(expected, actual.segments);

        // добавим элемент, который есть в системе
        System.out.println("- Неуспешная попытка добавления в систему элеменета " +
                "с именем, совпадающим с именем элемента, уже имеющегося в системе");
        CreateFile.createFile(actual, "forth", 5);
        Assert.assertEquals(expected, actual.segments);

        //добавим элемент, который больше свободного места
        System.out.println("- Неуспешная попытка добавления в систему элемента, размер которого больше" +
                "свободного места в системе");
        CreateFile.createFile(actual, "fsff", 10);
        Assert.assertEquals(expected, actual.segments);

        // Добавим элемент на место удалённого
        System.out.println("- Добавление элемента в систему на место удалённого");
        DeleteFile.deleteFile(actual, "second");
        CreateFile.createFile(actual, "newsecond", 2);
        expected.get(0).dataRecords.set(1, new DataRecord("newsecond", 2));
        Assert.assertEquals(expected, actual.segments);

        // Добавим элемент меньший чем удалённый, если после него идёт пустой
        System.out.println("- Добавление в систему элемента, размер которого меньше, чем размер " +
                "удалённого ранее файла, при том что, после этого файла идёт удалённый файл");
        DeleteFile.deleteFile(actual, "newsecond"); // был 2
        DeleteFile.deleteFile(actual, "first"); // был 4
        CreateFile.createFile(actual, "newfirst", 2);
        expected.get(0).dataRecords.get(1).type = false;
        expected.get(0).dataRecords.set(0, new DataRecord("newfirst", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(0).dataRecords.get(1).size = 4;
        Assert.assertEquals(expected, actual.segments);

        // когда добавляли прошлый, в след место увеличилось, добавим туда эллемент
        System.out.println("- Добавление в систему элемента на место удалённого файла, размер которого был " +
                "увеличен из-за добавления в предыдущей итарации добавления");
        expected.get(0).dataRecords.get(1).name = "newSecond2";
        expected.get(0).dataRecords.get(1).type = true;
        expected.get(0).currentDataNum = 3;
        CreateFile.createFile(actual, "newSecond2", 4);
        Assert.assertEquals(expected, actual.segments);

        //добавим эллемент, который займёт всю память
        System.out.println("- Добавление в систему элемента, размер которого равен размеру свободной памяти");
        CreateFile.createFile(actual, "fifth", 7);
        expected.get(1).dataRecords.add(new DataRecord("fifth", 7));
        expected.get(1).currentDataNum = 2;
        Assert.assertEquals(expected, actual.segments);

        // попробуем добавить ещё один эллемент
        System.out.println("- Неуспешная попытка добавления ещё одного элемента, когда свободной памяти нет");
        CreateFile.createFile(actual, "sadfsvdd", 1);
        Assert.assertEquals(expected, actual.segments);

        //Создадим ещё одну файловую систему
        FileSystem actual2 = new FileSystem("Test2", 11, 2, 2);
        ArrayList<Segment> expected2 = new ArrayList<>();

        // Добавить во всю систему
        System.out.println("- Полное заполнение файловой системы");
        CreateFile.createFile(actual2, "first", 1);
        CreateFile.createFile(actual2, "second", 2);
        CreateFile.createFile(actual2, "third", 3);
        CreateFile.createFile(actual2, "forth", 4);
        expected2.add(new Segment(2));
        expected2.add(new Segment(2));
        expected2.get(0).dataRecords.add(new DataRecord("first", 1));
        expected2.get(0).dataRecords.add(new DataRecord("second", 2));
        expected2.get(0).currentDataNum = 2;
        expected2.get(1).dataRecords.add(new DataRecord("third", 3));
        expected2.get(1).dataRecords.add(new DataRecord("forth", 4));
        expected2.get(1).currentDataNum = 2;
        Assert.assertEquals(expected2, actual2.segments);

        // попробовать добавить ещё один
        System.out.println("- Неуспешная попытка добавления ещё одного элемента, когда система полностью заполнена, " +
                "но свободная память ещё осталась");
        CreateFile.createFile(actual2, "ffft", 4);
        Assert.assertEquals(expected2, actual2.segments);
        System.out.println("P.S. Система зполнена в связи с тем, что все сегменты заполнены (т.е записано" +
                "максимальное количество файлов в сегментах)");


        //Так как система заполнена, но память ещё есть, удалим последний и добавим больший файл
        System.out.println("- Удаление из полностью заполненной системы последнего файла и " +
                "добавление файла, размер которого больше размера удалённого");
        DeleteFile.deleteFile(actual2, "forth");
        CreateFile.createFile(actual2, "forth", 5);
        expected2.get(1).dataRecords.get(1).size = 5;
        Assert.assertEquals(expected2, actual2.segments);

        //система заполнена, удалим последний и добавим меньший
        System.out.println("- Удаление из полностью заполненной системы последнего файла и " +
                "добавление файла, размер которого меньше размера удалённого");
        DeleteFile.deleteFile(actual2, "forth");
        CreateFile.createFile(actual2, "forth", 3);
        expected2.get(1).dataRecords.get(1).size = 3;
        Assert.assertEquals(expected2, actual2.segments);

    }*/

}