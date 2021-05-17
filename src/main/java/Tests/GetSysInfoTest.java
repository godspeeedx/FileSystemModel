package Tests;
import Functions.CreateFile;
import Functions.DeleteFile;
import Functions.GetSysInfo;
import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

public class GetSysInfoTest {
    @Test
    public void get1() {
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);

        String expected = """
                Осталось вот столько памяти 20
                Максимальный кусок,который можно вставить 20
                Степень фрагментации 0.0
                Дефрагментация не необходима""";

        Assert.assertEquals(expected, GetSysInfo.getInfo(actual));
    }
    @Test
    public void get2(){
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        CreateFile.createFile(actual, "1", 6);

        String expected = """
                Осталось вот столько памяти 14
                Максимальный кусок,который можно вставить 14
                Степень фрагментации 0.0
                Дефрагментация не необходима""";

        Assert.assertEquals(expected, GetSysInfo.getInfo(actual));
    }
    @Test
    public void get3(){
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);

        CreateFile.createFile(actual, "1", 6);
        CreateFile.createFile(actual, "2", 5);
        CreateFile.createFile(actual, "3", 1);
        CreateFile.createFile(actual, "4", 5);

        String expected = """
                Осталось вот столько памяти 3
                Максимальный кусок,который можно вставить 0
                Степень фрагментации 0.0
                Дефрагментация не необходима""";

        Assert.assertEquals(expected, GetSysInfo.getInfo(actual));
    }
    @Test
    public void get4(){
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
        CreateFile.createFile(actual, "1", 6);
        CreateFile.createFile(actual, "2", 5);
        CreateFile.createFile(actual, "3", 1);
        CreateFile.createFile(actual, "4", 5);

        DeleteFile.deleteFile(actual,"2");
        String expected = """
                Осталось вот столько памяти 8
                Максимальный кусок,который можно вставить 5
                Степень фрагментации 0.1875
                Дефрагментация необходима""";

        Assert.assertEquals(expected, GetSysInfo.getInfo(actual));
    }
    @Test
    public void get5(){
        FileSystem actual = new FileSystem("Test1", 20, 2, 2);
       CreateFile.createFile(actual, "1", 6);
        CreateFile.createFile(actual, "2", 5);
        CreateFile.createFile(actual, "3", 1);
        CreateFile.createFile(actual, "4", 5);
        DeleteFile.deleteFile(actual,"2");
        DeleteFile.deleteFile(actual,"1");

        GetSysInfo.getInfo(actual);
        String expected = """
                Осталось вот столько памяти 14
                Максимальный кусок,который можно вставить 6
                Степень фрагментации 0.3274
                Дефрагментация необходима""";

        Assert.assertEquals(expected, GetSysInfo.getInfo(actual));
    }
}
