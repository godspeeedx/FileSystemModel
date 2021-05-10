package Monitor;

import Structure.struct.FileSystem;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class ActualProgram {

    static MonitorClass monitor = new MonitorClass(new FileSystem("", 0, 0, 0));

    public static void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nДоброе утро! Вас приветствует группа С18-501!");
        System.out.println("Загружаем систему или создаем новую?");

        String choice = sc.nextLine();
        var commandObject = monitor.runStart(choice);
        commandObject.execute(monitor.fs);
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Scanner sc = new Scanner(System.in);
        RegistredCommands.init();

        init();

        while (true) {
            System.out.print("$");
            String command = sc.nextLine();

            if (command.equals("ВЫЙТИ")) {
                break;
            }

            var commandObject = monitor.runFunction(command);
            commandObject.execute(monitor.fs);

        }
    }


}
