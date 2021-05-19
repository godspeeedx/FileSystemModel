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
    public int readFileSize(String userMessage) {
        Scanner in = new Scanner(System.in);
        System.out.println(userMessage);
        boolean check = true;
        int fileLength = 0;
        do{
            fileLength = in.nextInt();
        } while (readFileSizeLogic(fileLength));
        return fileLength;
    }

    @Override
    public int readInt(String userMessage){
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readSystemSize(String userMessage) {
        return readInt(userMessage);
    }

    @Override
    public int readMaxSegmentNum(String userMessage) {
        return readInt(userMessage);
    }

    @Override
    public int readMaxDataNum(String userMessage) {
        return readInt(userMessage);
    }

}
