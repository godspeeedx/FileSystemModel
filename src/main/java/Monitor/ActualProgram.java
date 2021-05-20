package Monitor;

import Structure.struct.FileSystem;
import Structure.struct.iStreamActions;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class ActualProgram {

    static MonitorClass monitor = new MonitorClass(new FileSystem("", 0, 0, 0));

    public static void init(iStreamActions stream) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        stream.println("\nДоброе утро! Вас приветствует группа С18-501!" + "\n" + "Загружаем систему или создаем новую?");
        do {
            stream.println("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)")
        }while(initialization(stream.getLine()));
        while (init_flag == false) {
            stream.println(w"(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");
            String choice = stream.getLine();
            init_flag = initialization(choice);
            if (init_flag == false)
                stream.println("Пожалуйста, соберитесь и дайте нормальный и связанный ответ.");
        }
    }

    public static boolean initialization(String choice) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var commandObject = monitor.runStart(choice);
        if (commandObject == null) {
            return false;
        }
        commandObject.execute(monitor.fs);
        return true;
    }

    public static void mainRealization(iStreamActions stream) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stream.println("(Если не помните команду, вводите ПОМОГИТЕ)");
        while (true) {
            stream.print("$");
            String command = stream.getLine();

            if (command.equals("ВЫЙТИ"))
                break;

            var commandObject = monitor.runFunction(command);
            if (commandObject != null)
                commandObject.execute(monitor.fs);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegistredCommands.init();
        iStreamActions stream = new StreamActions();
        init(stream);
        mainRealization(stream);
    }
}


