package Monitor;

import Structure.struct.FileSystem;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class ActualProgram {

    //ВОЗМОЖНЫЙ КОСТЫЛЬ
    static MonitorClass monitor = new MonitorClass(new FileSystem("", 0, 0, 0));
    //ВОЗМОЖНЫЙ КОСТЫЛЬ

    public static void init() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Доброе утро! Вас приветствует группа С18-501!");
        System.out.println("Загружаем систему или создаем новую?");


        while (true) {
            String choice = sc.nextLine();
            if (choice.equals("СОЗДАЕМ")) {
                monitor.createSystem();
                break;
            } else if (choice.equals("ЗАГРУЖАЕМ")) {
                monitor.downloadSystem();
                break;
            } else {
                System.out.println("Ничего не понятно! Пожалуйста, соберитесь с мыслями и попробуйте еще раз!");
            }
        }
    }

    private static void tryToSave() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Сохранить текущую систему? Все изменения будут потеряны.");

        while (true) {
            String choice = sc.nextLine();
            if (choice.equals("ДА")) {
                monitor.saveSystem();
                break;
            } else if (choice.equals("НЕТ")) {
                break;
            } else {
                System.out.println("Пожалуйста, соберитесь и дайте нормальный и связный ответ.");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Scanner sc = new Scanner(System.in);
        RegistredCommands.init();
        boolean correct = false;

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
