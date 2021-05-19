package Tests;

import Functions.CreateSystem;
import Monitor.ActualProgram;
import Monitor.MonitorClass;
import Monitor.RegistredCommands;
import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ActualProgramTest {
    static FileSystem fs = new FileSystem("", 0, 0, 0);
    static MonitorClass monitor = new MonitorClass(fs);

    @Test
    void checkInitialization1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="СОЗДАТЬ СИСТЕМУ";
        var actual = ActualProgram.initialization(command);
        Assert.assertTrue(actual); //
    }
    @Test
    void checkInitialization2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="ЗАГРУЗИТЬ СИСТЕМУ";
        var actual = ActualProgram.initialization(command);
        Assert.assertTrue(actual); //
    }
    @Test
    void checkInitialization3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="Белеберда";
        var actual = ActualProgram.initialization(command);
        Assert.assertFalse(actual); //
    }
}