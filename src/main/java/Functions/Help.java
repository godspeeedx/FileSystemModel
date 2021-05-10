package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

import static Monitor.RegistredCommands.registredCommands;

public class Help extends BaseCommand implements iCommand {

    protected String commandName;

    public Help(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {
        monitor.writeMessage("В системе предусмотрен целый ряд команд:");
        for (String key : registredCommands.keySet()) {
            monitor.writeMessage("* " + key);
        }
        monitor.writeMessage("Если нужно подробнее об одной из них, введите её название. Если нет - введите ВЫХОД.");
        while(true){
            readParameters();
            switch (commandName) {
                case "ИЗМЕНИТЬ РАЗМЕР":
                    monitor.writeMessage(commandName + " - " + "Изменение длины существующего файла. Поиск по имени.");
                    monitor.writeMessage("В качестве аргументов команда принимает Имя файла в виде строки и Новую длину файла в виде целочисленного значения.");
                    break;
                case "СОЗДАТЬ ФАЙЛ":
                    monitor.writeMessage(commandName + " - " + "Создание нового файла с заданием его имени и длины.");
                    monitor.writeMessage("В качестве аргументов команда принимает Имя нового файла в виде строки и Длину нового файла в виде целочисленного значения.");
                    break;
                case "СОЗДАТЬ СИСТЕМУ":
                    monitor.writeMessage(commandName + " - " + "Создание новой файловой системы с заданием новых параметров.");
                    monitor.writeMessage("В качестве аргументов команда принимает Имя новой системы в виде строки, Размер диска в виде целочисленного значения,");
                    monitor.writeMessage("Максимальное число сегментов в виде целочисленного значения и Максимальное число записей в каждом сегменте в виде целочисленного значения.");
                    break;
                case "ДЕФРАГМЕНТАЦИЯ":
                    monitor.writeMessage(commandName + " - " + "Проведение дефрагментации системы с целью оптимизации свободного места.");
                    monitor.writeMessage("У этой команды нет аргументов.");
                    break;
                case "УДАЛИТЬ ФАЙЛ":
                    monitor.writeMessage(commandName + " - " + "Удаление существующего файла. Поиск по имени.");
                    monitor.writeMessage("В качестве аргумента команда принимает Имя удаляемого файла в виде строки.");
                    break;
                case "ЗАГРУЗИТЬ СИСТЕМУ":
                    monitor.writeMessage(commandName + " - " + "Загрузка сохранённой файловой системы с загрузко установленных в ней параметров.");
                    monitor.writeMessage("В качестве аргумента команда принимает Имя загружаемой системы в виде строки.");
                    break;
                case "НАПЕЧАТАТЬ":
                    monitor.writeMessage(commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны");
                    monitor.writeMessage("У этой команды нет аргументов.");
                    break;
                case "НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ":
                    monitor.writeMessage(commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны, остортированного в алфавитном порядке по названию.");
                    monitor.writeMessage("У этой команды нет аргументов.");
                    break;
                case "ПОМОГИТЕ":
                    monitor.writeMessage(commandName + " - " + "Вы сейчас здесь :) Вывод списка команд и функциональности каждой.");
                    monitor.writeMessage("У этой команды нет аргументов.");
                    break;
                case "ИНФОРМАЦИЯ":
                    monitor.writeMessage(commandName + " - " + "Вывод информации о системе: сколько осталось места, степень фрагментации и другая");
                    monitor.writeMessage("У этой команды нет аргументов.");
                    break;
                case "ВЫХОД":
                    return;

                default:
                    monitor.writeMessage("Кажется по введённой вами команде ещё нет справки. Или вы ошиблись вводом. Попробуем ещё раз?");
            }
            monitor.writeMessage("Если нужна информация по команде, введите её название. Если нет - введите ВЫХОД.");
        }

    }

    @Override
    public void readParameters() {
        this.commandName = monitor.readString("Введите команду:");
    }
}
