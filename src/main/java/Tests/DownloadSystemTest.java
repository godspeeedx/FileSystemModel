package Tests;

import Functions.CreateFile;
import Functions.DownloadSystem;
import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DownloadSystemTest {
    @Test
    public void check(){
        System.out.println("Проверка загрузки системы");
        FileSystem actual = new FileSystem("1",1,1,1);
        FileSystem expected = new FileSystem("testic",10,2,2);
        CreateFile.createFile(expected,"first",1);
        CreateFile.createFile(expected,"second",2);
        CreateFile.createFile(expected,"third",3);
        DownloadSystem.downloadSystemStatic("testic.txt", actual);
        Assert.assertEquals(expected,actual);
        System.out.println("- Успешно");
    }

}