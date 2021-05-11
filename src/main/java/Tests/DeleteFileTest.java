package Tests;

import Functions.CreateFile;
import Functions.DeleteFile;
import Structure.struct.*;
import org.junit.Assert;
import org.junit.Test;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.List;


public class DeleteFileTest {


   /* public class MonitorMock implements iMonitor{

        private List<String> readResults = new ArrayList<>();
        private List<String> writeResults = new ArrayList<>();

        public MonitorMock AddReadResult(String result){
            readResults.add(result);
            return this;
        }

        public String GetWriteResult(){

            return writeResults.get(0);
        }

        @Override
        public void writeMessage(String userMessage) {
            writeResults.add(userMessage);
        }

        @Override
        public String readString(String userMessage) {
            var result =  readResults.get(0);
            readResults.remove(0);
            return result;
        }

        @Override
        public int readFileSize(String userMessage) {
            return 0;
        }

        @Override
        public int readSystemSize(String userMessage) {
            return 0;
        }

        @Override
        public int readMaxSegmentNum(String userMessage) {
            return 0;
        }

        @Override
        public int readMaxDataNum(String userMessage) {
            return 0;
        }
    }*/

    @Test
    public void test() {
        //Создадим  файловую систему
        FileSystem actual = new FileSystem("Test2", 11, 2, 2);
        ArrayList<Segment> expected = new ArrayList<>();

        //Заполнмим нашу систему
        CreateFile.createFile(actual, "first", 1);
        CreateFile.createFile(actual, "second", 2);
        CreateFile.createFile(actual, "third", 3);
        CreateFile.createFile(actual, "forth", 4);
        expected.add(new Segment(2));
        expected.add(new Segment(2));
        expected.get(0).datas.add(new Data("first", 1));
        expected.get(0).datas.add(new Data("second", 2));
        expected.get(0).currentDataNum = 2;
        expected.get(1).datas.add(new Data("third", 3));
        expected.get(1).datas.add(new Data("forth", 4));
        expected.get(1).currentDataNum = 2;

        //удалим один элемент с конца
        System.out.println("удалим один элемент с конца");
        DeleteFile.deleteFile(actual, "forth");
        expected.get(1).datas.get(1).type = false;
        expected.get(1).currentDataNum = 1;
        Assert.assertEquals(expected, actual.segments);

        //удалим эллемент с самого первого сегмента
        System.out.println("удалим эллемент с самого первого сегмента");
        expected.get(0).datas.get(0).type = false;
        expected.get(0).currentDataNum = 1;
        DeleteFile.deleteFile(actual, "first");
        Assert.assertEquals(expected, actual.segments);

        //попробуем удалить элемент, которого нет
        System.out.println("попробуем удалить элемент, которого нет");
        DeleteFile.deleteFile(actual, "lol");
        Assert.assertEquals(expected, actual.segments);

        //удалим всю систему
        System.out.println("Удалим всю систему");
        DeleteFile.deleteFile(actual,"second");
        DeleteFile.deleteFile(actual,"third");
        expected.get(0).datas.get(1).type = false;
        expected.get(0).currentDataNum = 0;
        expected.get(1).datas.get(0).type = false;
        expected.get(1).currentDataNum = 0;
        Assert.assertEquals(expected, actual.segments);

        // попробуем удалить удалённый
        System.out.println(" попробуем удалить удалённый");
        DeleteFile.deleteFile(actual,"second");
        Assert.assertEquals(expected, actual.segments);
    }

    @Test
    public void deleteFile() {
    }

    @Test
    public void execute() {
    }

    @Test
    public void readParameters() {
    }
}
