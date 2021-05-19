package Tests;

import Functions.*;
import Monitor.*;
import Structure.struct.iMonitor;
import org.junit.Assert;
import org.junit.Test;
import Structure.struct.FileSystem;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static Monitor.RegistredCommands.registeredCommands;

public class MonitorClassTest {
    static FileSystem fs = new FileSystem("", 0, 0, 0);
    static MonitorClass monitor = new MonitorClass(fs);

    /**
     * Проверяем правильно ли логика работает
     */
    @Test
    public void checkReadFileSizeLogic() {
        int     command = new Random().nextInt(); //создаём случайное число
        boolean actual = monitor.readFileSizeLogic(command), //загоняем его в функцию приёма размера файла
                expected=command < 0 || command > FileSystem.systemSize; //показываем, что если функции не нравится ввод она отдаёт Тру на переввод.
        Assert.assertEquals(expected, actual); //сравниваем

    }

    /**
     * Проверяем функцию выдачи класса старта системы на команду СОЗДАТЬ СИСТЕМУ
     */
    @Test
    public void checkRunStart1() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String command="СОЗДАТЬ СИСТЕМУ";
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runStart(command);
        Assert.assertTrue(actual instanceof CreateSystem); //проверяем выдался ли нам нужный класс
    }
    /**
     * Проверяем функцию выдачи класса старта системы на команду ЗАГРУЗИТЬ СИСТЕМУ
     */
    @Test
    public void checkRunStart2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="ЗАГРУЗИТЬ СИСТЕМУ";
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runStart(command);
        Assert.assertTrue(actual instanceof DownloadSystem); //проверяем выдался ли нам нужный класс
    }
    /**
     * Проверяем функцию выдачи класса старта системы на неправильную команду
     */
    @Test
    public void checkRunStart3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String command="БЕЛЕБЕРДА";
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runStart(command);
        Assert.assertNull(actual);
    }

    @Test
    /**
     * Проверяем функцию выдачи класса функции системы на правильную команду
     */
    public void checkRunFunction0() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("ИЗМЕНИТЬ РАЗМЕР"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof ChangeFileSize); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("СОЗДАТЬ ФАЙЛ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof CreateFile); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("СОЗДАТЬ СИСТЕМУ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof CreateSystem); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("ДЕФРАГМЕНТАЦИЯ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof Defragmentation); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction4() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("УДАЛИТЬ ФАЙЛ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof DeleteFile); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction5() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("ЗАГРУЗИТЬ СИСТЕМУ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof DownloadSystem); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction6() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("НАПЕЧАТАТЬ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof Print); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction7() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof PrintInAlphabetOrder); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction8() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("ПОМОГИТЕ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof Help); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction9() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("ИНФОРМАЦИЯ"); //запустили нашу функцию
        Assert.assertTrue(actual instanceof GetSysInfo); //проверили что достался нужный клас
    }
    /**
     * Проверяем функцию выдачи класса функции системы на некорректную команду
     */
    @Test
    public void checkRunFunction10() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init(); //инициализируем список всех команд
        String  commandName = "БЕЛЕБЕРДА"; //некоректная команда
        var actual = monitor.runFunction(commandName); //запустили нашу функцию
        Assert.assertNull(actual); //проверили что null
    }
}
