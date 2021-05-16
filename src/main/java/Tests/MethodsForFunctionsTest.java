package Tests;

import Functions.CreateFile;
import Functions.DownloadSystem;
import Functions.MethodsForFunctions;
import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MethodsForFunctionsTest {


    @Test
    public void saveSystem() {

        System.out.println("Проверка сохранения системы");
        FileSystem actual = new FileSystem("1",1,1,1);
        FileSystem expected = new FileSystem("saveSys",10,2,2);
        CreateFile.createFile(expected,"first",1);
        CreateFile.createFile(expected,"second",2);
        CreateFile.createFile(expected,"third",3);

        MethodsForFunctions.saveSystem(expected);
        DownloadSystem.downloadSystemStatic("saveSys.txt", actual);

        Assert.assertEquals(expected,actual);
        System.out.println("- Успешно");
    }
}