package Tests;

import Functions.Help;
import Monitor.MonitorClass;
import Structure.struct.FileSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class MonitorClassTest {
    static MonitorClass monitor = new MonitorClass(new FileSystem("", 0, 0, 0));

    /**
     * Проверяем правильно ли логика работает
     */
    @Test
    public void checkReadFileSizeLogic() {
        int     command = new Random().nextInt();
        boolean actual = monitor.readFileSizeLogic(command),
                expected;

        if (command < 0 || command > FileSystem.systemSize)
            expected=true;
        else
            expected=false;

        Assert.assertEquals(expected, actual);

    }
    @Test
    public void checkRunFunction(){

    }
    @Test
    public void checkRunStart(){

    }
}
