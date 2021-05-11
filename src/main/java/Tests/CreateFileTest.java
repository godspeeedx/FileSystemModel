package Tests;

import Functions.CreateFile;
import Functions.DeleteFile;
import Structure.struct.Data;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateFileTest {

    @Test
    public void createFile() {
        //Создали одну файловую систему
        FileSystem actual = new FileSystem("Test1", 20, 3, 3);
        ArrayList<Segment> expected = new ArrayList<>();

        // добавим первый элемент
        expected.add(new Segment(3));
        expected.get(0).datas.add(new Data("first", 4));
        expected.get(0).currentDataNum = 1;
        CreateFile.createFile(actual, "first", 4);
        System.out.println("Добавим первый элемент");
        Assert.assertEquals(expected, actual.segments);

        // добавим ещё 2 элемента
        expected.get(0).datas.add(new Data("second", 2));
        expected.get(0).datas.add(new Data("third", 3));
        System.out.println("Добавим ещё два");
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        expected.get(0).currentDataNum = 3;
        Assert.assertEquals(expected, actual.segments);

        //добавим 4-й эллемент
        System.out.println("добавим 4-й эллемент");
        expected.add(new Segment(3));
        expected.get(1).datas.add(new Data("forth", 4));
        expected.get(1).currentDataNum = 1;
        CreateFile.createFile(actual, "forth", 4);
        Assert.assertEquals(expected, actual.segments);

        // добавим элемент, который есть в системе
        System.out.println("добавим элемент, который есть в системе");
        CreateFile.createFile(actual, "forth", 5);
        Assert.assertEquals(expected, actual.segments);

        //добавим элемент, который больше свободного места
        System.out.println("добавим элемент, который больше свободного места");
        CreateFile.createFile(actual, "fsff", 10);
        Assert.assertEquals(expected, actual.segments);

        // Добавим элемент на место удалённого
        System.out.println("Добавим элемент на место удалённого");
        DeleteFile.deleteFile(actual,"second");
        CreateFile.createFile(actual,"newsecond",2);
        expected.get(0).datas.set(1,new Data("newsecond",2));
        Assert.assertEquals(expected, actual.segments);

        // Добавим элемент меньший чем удалённый, если после него идёт пустой
        System.out.println("Добавим элемент меньший чем удалённый, если после него идёт удалённый");
        DeleteFile.deleteFile(actual,"newsecond"); // был 2
        DeleteFile.deleteFile(actual,"first"); // был 4
        CreateFile.createFile(actual,"newfirst", 2);
        expected.get(0).datas.get(1).type = false;
        expected.get(0).datas.set(0, new Data("newfirst", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(0).datas.get(1).size = 4;
        Assert.assertEquals(expected, actual.segments);

        // когда добавляли прошлый, в след место увеличилось, добавим туда эллемент
        System.out.println("когда добавляли прошлый, в след месте увеличилось, добавим туда элемент");
        expected.get(0).datas.get(1).name = "newSecond2";
        expected.get(0).datas.get(1).type = true;
        expected.get(0).currentDataNum = 3;
        CreateFile.createFile(actual, "newSecond2",4);
        Assert.assertEquals(expected, actual.segments);

        //добавим эллемент, который займёт всю память
        System.out.println("добавим элемент, который займёт всю оставшуюся память");
        CreateFile.createFile(actual, "fifth", 7);
        expected.get(1).datas.add(new Data("fifth", 7));
        expected.get(1).currentDataNum = 2;
        Assert.assertEquals(expected, actual.segments);

        // попробуем добавить ещё один эллемент
        System.out.println("попробуем добавить ещё один эллемент");
        CreateFile.createFile(actual, "sadfsvdd", 1);
        Assert.assertEquals(expected, actual.segments);

        //Создадим ещё одну файловую систему
        FileSystem actual2 = new FileSystem("Test2", 11, 2, 2);
        ArrayList<Segment> expected2 = new ArrayList<>();

        // Добавить во всю систему
        System.out.println("Система полностью заполнена");
        CreateFile.createFile(actual2, "first", 1);
        CreateFile.createFile(actual2, "second", 2);
        CreateFile.createFile(actual2, "third", 3);
        CreateFile.createFile(actual2, "forth", 4);
        expected2.add(new Segment(2));
        expected2.add(new Segment(2));
        expected2.get(0).datas.add(new Data("first", 1));
        expected2.get(0).datas.add(new Data("second", 2));
        expected2.get(0).currentDataNum = 2;
        expected2.get(1).datas.add(new Data("third", 3));
        expected2.get(1).datas.add(new Data("forth", 4));
        expected2.get(1).currentDataNum = 2;
        Assert.assertEquals(expected2, actual2.segments);

        // попробовать добавить ещё один
        System.out.println("попробовать добавить ещё один");
        CreateFile.createFile(actual2, "ffft", 4);
        Assert.assertEquals(expected2, actual2.segments);

        //Так как система заполнена, но память ещё есть, удалим последний и добавим больший файл
        System.out.println("Так как система заполнена, но память ещё есть, удалим последний и добавим больший файл");
        DeleteFile.deleteFile(actual2, "forth");
        CreateFile.createFile(actual2, "forth", 5);
        expected2.get(1).datas.get(1).size = 5;
        Assert.assertEquals(expected2, actual2.segments);

        //система заполнена, удалим последний и добавим меньший
        System.out.println("Так как система заполнена, но память ещё есть, удалим последний и добавим больший файл");
        DeleteFile.deleteFile(actual2, "forth");
        CreateFile.createFile(actual2, "forth", 3);
        expected2.get(1).datas.get(1).size = 3;
        Assert.assertEquals(expected2, actual2.segments);

    }

    @Test
    public void execute() {
    }

    @Test
    public void readParameters() {
    }
}