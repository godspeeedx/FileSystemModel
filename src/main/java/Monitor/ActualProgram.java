package Monitor;

import Structure.struct.FileSystem;

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

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean correct = false;

        init();

        while (true) {
            System.out.print("$");
            String command = sc.nextLine();

            if (command.equals("ИЗМЕНИТЬ РАЗМЕР")) {
                monitor.changeFileSize();
                correct = true;
            }
            if (command.equals("СОЗДАТЬ ФАЙЛ")) {
                monitor.createFile();
                correct = true;
            }
            if (command.equals("СОЗДАТЬ СИСТЕМУ")) {
                tryToSave();
                monitor.createSystem();
                correct = true;
            }
            if (command.equals("ДЕФРАГМЕНТАЦИЯ")) {
                monitor.defragmentation();
                correct = true;
            }

            if (command.equals("УДАЛИТЬ")) {
                monitor.deleteFile();
                correct = true;
            }
            if (command.equals("ЗАГРУЗИТЬ СИСТЕМУ")) {
                tryToSave();
                monitor.downloadSystem();
                correct = true;
            }
            if (command.equals("НАПЕЧАТАТЬ")) {
                monitor.printSystem();
                correct = true;
            }
            if (command.equals("НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ")) {
                monitor.printSystemInOrder();
                correct = true;
            }
            if (command.equals("ИНФА")) {
                monitor.info();
                correct = true;
            }
            if (command.equals("ПОМОГИТЕ")) {
                monitor.help();
                correct = true;
            }
            if (command.equals("ВЫЙТИ")) {
                tryToSave();
                break;
            }
            if (!correct) {
                System.out.println("Команды " + command + " не существует. Пожалуйста, попробуйте еще раз.\n" +
                        "Информация о командах доступна по команде ПОМОГИТЕ.");

            }
            correct = false;
        }
    }


}
