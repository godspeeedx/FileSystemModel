package Tests;

import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateSystemTest {

    //
    @Test
    public void check(){
        System.out.println("Проверка создания системы");
        FileSystem actual = new FileSystem("Name",30,2,2);
        FileSystem expected = new FileSystem("Test",3,2,1);
        actual.copy(expected);
        Assert.assertEquals(expected, actual);
        System.out.println("Система успешна создана" );

    }

}