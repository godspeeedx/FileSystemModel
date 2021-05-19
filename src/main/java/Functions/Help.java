package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

import static Monitor.RegistredCommands.registeredCommands;

public class Help extends BaseCommand implements iCommand {

    protected String classCommandName;

    public Help(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public void allCommandsHelpShowing() {
        monitor.writeMessage("В системе предусмотрен целый ряд команд:");
        for (String key : registeredCommands.keySet()) {
            monitor.writeMessage("* " + key);
        }
        monitor.writeMessage("* " + "ВЫЙТИ");
    }

    public static String stringHelpPerCommand(String commandName) {
        return switch (commandName) {
            case "ВЫЙТИ" -> commandName + " - " + "Выход из программы" + "\n" +
                    "У этой команды нет аргументов.";
            case "ИЗМЕНИТЬ РАЗМЕР" -> commandName + " - " + "Изменение длины существующего файла. Поиск по имени." + "\n" +
                    "В качестве аргументов команда принимает Имя файла в виде строки и Новую длину файла в виде целочисленного значения.";
            case "СОЗДАТЬ ФАЙЛ" -> commandName + " - " + "Создание нового файла с заданием его имени и длины." + "\n" +
                    "В качестве аргументов команда принимает Имя нового файла в виде строки и Длину нового файла в виде целочисленного значения.";
            case "СОЗДАТЬ СИСТЕМУ" -> commandName + " - " + "Создание новой файловой системы с заданием новых параметров." + "\n" +
                    "В качестве аргументов команда принимает Имя новой системы в виде строки, Размер диска в виде целочисленного значения," +
                    "Максимальное число сегментов в виде целочисленного значения и Максимальное число записей в каждом сегменте в виде целочисленного значения.";
            case "ДЕФРАГМЕНТАЦИЯ" -> commandName + " - " + "Проведение дефрагментации системы с целью оптимизации свободного места." + "\n" +
                    "У этой команды нет аргументов.";
            case "УДАЛИТЬ ФАЙЛ" -> commandName + " - " + "Удаление существующего файла. Поиск по имени." + "\n" +
                    "В качестве аргумента команда принимает Имя удаляемого файла в виде строки.";
            case "ЗАГРУЗИТЬ СИСТЕМУ" -> commandName + " - " + "Загрузка сохранённой файловой системы с загрузко установленных в ней параметров." + "\n" +
                    "В качестве аргумента команда принимает Имя загружаемой системы в виде строки.";
            case "НАПЕЧАТАТЬ" -> commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны" + "\n" +
                    "У этой команды нет аргументов.";
            case "НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ" -> commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны, остортированного в алфавитном порядке по названию." + "\n" +
                    "У этой команды нет аргументов.";
            case "ПОМОГИТЕ" -> commandName + " - " + "Вы сейчас здесь :) Вывод списка команд и функциональности каждой." + "\n" +
                    "У этой команды нет аргументов.";
            case "ИНФОРМАЦИЯ" -> commandName + " - " + "Вывод информации о системе: сколько осталось места, степень фрагментации и другая" + "\n" +
                    "У этой команды нет аргументов.";
            case "ВЫХОД" -> null;
            default -> "Кажется по введённой вами команде ещё нет справки. Или вы ошиблись вводом. Попробуем ещё раз?";
        };
    }

    public boolean showStringHelpPerCommand(String stringHelpPerCommand){
        if (stringHelpPerCommand == null)
            return false;

        monitor.writeMessage(stringHelpPerCommand + "\n" +
                "Если нужна информация по команде, введите её название. Если нет - введите ВЫХОД.");
        return true;
    }

    @Override
    public void execute(FileSystem fs) {
        allCommandsHelpShowing();

        monitor.writeMessage("Если нужно подробнее об одной из них, введите её название. Если нет - введите ВЫХОД.");

        String stringHelpPerCommand;
        do{
            readParameters();
            stringHelpPerCommand=stringHelpPerCommand(classCommandName);
        }

        while (showStringHelpPerCommand(stringHelpPerCommand));
    }

    @Override
    public void readParameters() {
        this.classCommandName = monitor.readString("Введите команду:");
    }
}
