package Tests;

import Monitor.MonitorClass;
import org.junit.Assert;
import org.junit.Test;
import Structure.struct.FileSystem;

import java.util.Random;

public class MonitorClassTest {
    static MonitorClass monitor = new MonitorClass(new FileSystem("", 0, 0, 0));

    /**
     * Проверяем правильно ли логика работает
     */
    @Test
    public void checkReadFileSizeLogic() {
        int     command = new Random().nextInt(); //создаём случайное число
        boolean actual = monitor.readFileSizeLogic(command), //загоняем его в функцию приёма размера файла
                expected=command < 0 || command > FileSystem.systemSize; //показываем, что если функции не нравится ввод она отдаёт Тру на переввод.
        Assert.assertEquals(expected, actual); //сравниваем

    }//тут я хрен знает чё писать
    @Test
    public void checkRunFunction(){

    }
    @Test
    public void checkRunStart(){

    }
}
