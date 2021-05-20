package Monitor;

import Structure.struct.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Monitor.RegistredCommands.registeredCommands;

public class MonitorClass implements iMonitor {
    FileSystem fs;

    public MonitorClass(FileSystem fs) {
        this.fs = fs;
    }

    public iCommand runStart(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (commandName.equals("СОЗДАТЬ СИСТЕМУ") || commandName.equals("ЗАГРУЗИТЬ СИСТЕМУ"))
            return runFunction(commandName);
        return null;
    }

    public iCommand runFunction(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String command = registeredCommands.get(commandName);
        if (command==null){
            writeMessage("Команда не найдена, попробуйте ещё раз или введите ПОМОГИТЕ");
            return null;
        }
        return (iCommand) Class.forName(command)
                                .getConstructor(iMonitor.class, FileSystem.class)
                                .newInstance(this, fs);
    }

    @Override
    public void writeMessage(String userMessage) {
        System.out.println(userMessage);
    }

    @Override
    public String readString(String userMessage) {
        Scanner sc = new Scanner(System.in);
        String str;
        System.out.println(userMessage);
        str = sc.nextLine();
        return str;
    }

    public boolean readFileSizeLogic(int fileLength) {
            if (fileLength < 0 || fileLength > FileSystem.systemSize) {
                System.out.println("Длина файла некорректна");
                System.out.println("Введите новую длину файла");
                return true;
            }
            else
                return false;
    }


    @Override
    public int readInt(String userMessage){
        System.out.println(userMessage);
        Scanner sc = new Scanner(System.in);
        int number = 1;
        do {
            if(number <= 0)
            System.out.println("Некорректные данные! Повторите попытку");
            while (!sc.hasNextInt()) {
                System.out.println("Некорректные данные! Повторите попытку");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number <= 0);
        return number;
    }


}
