package Monitor;

import Structure.struct.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Monitor.RegistredCommands.registeredCommands;

public class MonitorClass implements iMonitor {
    FileSystem fs;

    MonitorClass(FileSystem fs) {
        this.fs = fs;
    }

    public iCommand runStart(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (commandName.equals("СОЗДАТЬ СИСТЕМУ") || commandName.equals("ЗАГРУЗИТЬ СИСТЕМУ")) {
            var commandClassName = registeredCommands.get(commandName);
            var constr = Class.forName(commandClassName).getConstructor(iMonitor.class, FileSystem.class);
            return (iCommand) constr.newInstance(this, fs);
        }
        return null;
    }


    public iCommand runFunction(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var commandClassName = registeredCommands.get(commandName);
        var constr = Class.forName(commandClassName).getConstructor(iMonitor.class, FileSystem.class);
        return (iCommand) constr.newInstance(this, fs);
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

    @Override
    public int readFileSize(String userMessage) {
        Scanner in = new Scanner(System.in);
        System.out.println(userMessage);
        boolean check = true;
        int fileLength = 0;
        while (check) {
            fileLength = in.nextInt();
            if (fileLength < 0 || fileLength > FileSystem.systemSize) {
                System.out.println("Длина файла некорректна");
                System.out.println("Введите новую длину файла");
            } else {
                check = false;
            }
        }
        return fileLength;
    }

    @Override
    public int readSystemSize(String userMessage) {
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readMaxSegmentNum(String userMessage) {
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readMaxDataNum(String userMessage) {
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

}
