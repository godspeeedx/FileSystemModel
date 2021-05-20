package Tests;

import Functions.CreateSystem;
import Monitor.ActualProgram;
import Monitor.MonitorClass;
import Monitor.RegistredCommands;
import Structure.struct.FileSystem;
import Structure.struct.iStreamActions;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ActualProgramTest {
    static FileSystem fs = new FileSystem("", 0, 0, 0);
    static MonitorClass monitor = new MonitorClass(fs);

    @Test
    public void checkMainRealization1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        StreamActionsFake stream = new StreamActionsFake();
        ActualProgram.mainRealization(stream);
        //Assert.assertEquals(stringListOutput.get);
    }









    /**
     * Не работает, потому что Junit не умеет обрабатывать ввод пользователя
     */
    @Test
    public void checkInitialization1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="СОЗДАТЬ СИСТЕМУ";
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = ActualProgram.initialization(command);
        Assert.assertTrue(actual); //
    }
    @Test
    public void checkInitialization2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="ЗАГРУЗИТЬ СИСТЕМУ";
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = ActualProgram.initialization(command);
        Assert.assertTrue(actual); //
    }
    @Test
    public void checkInitialization3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="Белеберда";
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = ActualProgram.initialization(command);
        Assert.assertFalse(actual); //
    }
}