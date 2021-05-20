package Tests;

import Functions.CreateSystem;
import Functions.MethodsForFunctions;
import Monitor.ActualProgram;
import Monitor.MonitorClass;
import Monitor.RegistredCommands;
import Structure.struct.FileSystem;
import Structure.struct.iStreamActions;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ActualProgramTest {
    static FileSystem fs = new FileSystem("", 0, 0, 0);
    static MonitorClass monitor = new MonitorClass(fs);

    @Test
    public void checkMainRealization1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegistredCommands.init();
        StreamActionsFake stream = new StreamActionsFake();

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("(Если не помните команду, вводите ПОМОГИТЕ)");
        expected.add("$");
        expected.add("(Если не помните команду, вводите ПОМОГИТЕ)");

        stream.stringListInput.add("БЕЛЕБЕРДА");
        stream.stringListInput.add("ВЫЙТИ");


        ActualProgram.mainRealization(stream);
        //Assert.assertEquals(stringListOutput.get);


    }
    //Проверка ввода Белеберда и СОЗДАТЬ СИСТЕМУ
    @Test
    public void checkMainInit1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegistredCommands.init();
        StreamActionsFake stream = new StreamActionsFake();
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("\nДоброе утро! Вас приветствует группа С18-501!" + "\n" + "Загружаем систему или создаем новую?");
        expected.add("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");
        expected.add("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");
        expected.add("Введите название системы:");
        expected.add("Введите размер диска:");
        expected.add("Введите максимальное число сегментов:");
        expected.add("Введите максимальное число записей в каждом сегменте:");
        expected.add("Готово!");
        expected.add("Система сохранена");
        stream.stringListInput.add("БЕЛЕБЕРДА");
        stream.stringListInput.add("СОЗДАТЬ СИСТЕМУ");
        stream.stringListInput.add("Диск1");
        stream.stringListInput.add("2");
        stream.stringListInput.add("4");
        stream.stringListInput.add("8");


        ActualProgram.init(stream);
        Assert.assertEquals(expected, stream.stringListOutput);
    }


    //Проверка ввод ЗАГРУЗИТЬ СИСТЕМУ
    @Test
    public void checkMainInit2() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegistredCommands.init();
        StreamActionsFake stream = new StreamActionsFake();
        MethodsForFunctions.saveSystem(new FileSystem("Диск1", 2, 4, 8));

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("\nДоброе утро! Вас приветствует группа С18-501!" + "\n" + "Загружаем систему или создаем новую?");
        expected.add("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");
        expected.add("Введите название системы для загрузки:");
        expected.add("Не удалось загрузить систему, введите повторное имя системы");
        expected.add("Система сохранена");

        stream.stringListInput.add("ЗАГРУЗИТЬ СИСТЕМУ");
        stream.stringListInput.add("ДИСКЕТА101");
        stream.stringListInput.add("Диск1");


        ActualProgram.init(stream);
        Assert.assertEquals(expected, stream.stringListOutput);
    }
}
