package Monitor;

import Structure.struct.FileSystem;

import java.util.Scanner;

public class ActualProgram {
    static MonitorClass monitor = new MonitorClass();

    public static void main(String[] args) {
        initFileSystem();

        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if (command.equals("СОЗДАТЬ"))
                monitor.createFile();
            if (command.equals("ЗАКРЫТЬ")){
                monitor.exit();
                break;
            }
        }
    }

    public static void initFileSystem() {
        //инициализация из файла тоже тут, просто тут пока инициализация сразу из пользователя
        System.out.println("Ку! Го инициализировать систему ");
        monitor.createSystem();
        System.out.println("Ладно го загрузим");
        monitor.downloadSystem();
    }
}
