package Monitor;

import Structure.struct.*;

import java.lang.reflect.InvocationTargetException;

import static Monitor.RegistredCommands.registeredCommands;

public class MonitorClass implements iMonitor {
    FileSystem fs;
    public iStreamActions stream;

    public MonitorClass(FileSystem fs, iStreamActions stream) {
        this.fs = fs;
        this.stream = stream;
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
        stream.println(userMessage);
    }

    @Override
    public String readString(String userMessage) {
        writeMessage(userMessage);
        return stream.getLine();
    }

    public boolean readFileSizeLogic(int fileLength) {
            if (fileLength < 0 || fileLength > FileSystem.systemSize) {
                writeMessage("Длина файла некорректна");
                writeMessage("Введите новую длину файла");
                return true;
            }
            else
                return false;
    }

    @Override
    public int readFileSize(String userMessage) {
        writeMessage(userMessage);
        boolean check = true;
        int fileLength = 0;
        do{
            fileLength = stream.nextInt();
        } while (readFileSizeLogic(fileLength));
        return fileLength;
    }

    @Override
    public int readInt(String userMessage){
        writeMessage(userMessage);
        return stream.nextInt();
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
