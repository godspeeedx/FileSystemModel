package Monitor;

import Functions.*;
import Structure.struct.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static Monitor.RegistredCommands.registredCommands;

public class MonitorClass implements iMonitor {
    FileSystem fs;
    iCommand actualCommand;

    MonitorClass(FileSystem fs) {
        this.fs = fs;
    }

    public iCommand runFunction(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var commandClassName = registredCommands.get(commandName);
        var constr = Class.forName(commandClassName).getConstructor(iMonitor.class, FileSystem.class);
        var command = (iCommand) constr.newInstance(this, fs); //fs,
        return command;
    }

    public void changeFileSize() {
        actualCommand = new ChangeFileSize(this, fs);
        actualCommand.execute(fs);
    }

    public void createFile() {
        actualCommand = new CreateFile(this, fs);
        actualCommand.execute(fs);
    }

    public void createSystem() {
        actualCommand = new CreateSystem(this, fs);
        //КОСТЫЛЬ
        fs = new FileSystem("", 0, 0, 0);
        //КОСТЫЛЬ
        actualCommand.execute(fs);
    }

    public void defragmentation() {
        actualCommand = new Defragmentation(this, fs);
        actualCommand.execute(fs);
    }

    public void deleteFile() {
        actualCommand = new DeleteFile(this, fs);
        actualCommand.execute(fs);
    }

    public void downloadSystem() {
        actualCommand = new DownloadSystem(this, fs);
        actualCommand.execute(fs);
    }

    public void printSystem() {
        actualCommand = new Print(this, fs);
        actualCommand.execute(fs);
    }

    public void printSystemInOrder() {
        actualCommand = new PrintInAlphabetOrder(this, fs);
        actualCommand.execute(fs);
    }

    public void saveSystem() {
        actualCommand = new SaveSystem(this, fs);
        actualCommand.execute(fs);
    }

    @Override
    public void writeMessage(String userMessage) {
        System.out.println(userMessage);
    }

    @Override
    public String readString(String userMessage) {
        ////временно без проверок
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
        //временно без проверок
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readMaxSegmentNum(String userMessage) {
        //временно без проверок
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readMaxDataNum(String userMessage) {
        //временно без проверок
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

}
