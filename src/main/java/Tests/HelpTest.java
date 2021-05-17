package Tests;

import Functions.Help;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Random;

import static Monitor.RegistredCommands.registeredCommands;

public class HelpTest {

    /**
     * Загоняем каждую команду и проверяем, что выводится чё надо.
     */
    @Test
    public void checkList() {
        System.out.println("Тестирование вывода правильной подсказки из списка: " + "\n");

        for (String key : registeredCommands.keySet()) {
            String  commandName = key,
                    actual = Help.stringHelpPerCommand(commandName),
                    expected = switch (commandName) {
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
                        default -> "null";
                    };
            System.out.println(commandName);
            Assert.assertEquals(expected, actual);
        }
    }

    public String generatingRandomString() {
        byte[] array = new byte[20]; // length is bounded by 20
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    /**
     * Генерируем случайную строку и загоняем её. Должны получить отсутствие справки
     */
    @Test
    public void checkRandom() {
        System.out.println("Тестирование вывода подсказки по несуществующей команде: " + "\n");
            String  commandName = generatingRandomString(),
                    actual = Help.stringHelpPerCommand(commandName),
                    expected = "Кажется по введённой вами команде ещё нет справки. Или вы ошиблись вводом. Попробуем ещё раз?";
            System.out.println(commandName);
            Assert.assertEquals(expected, actual);
    }
    /**
     * Загоняем ВЫХОД. Должны получить нул (выход).
     */
    @Test
    public void checkExit() {
        System.out.println("Тестирование вывода подсказки по несуществующей команде: " + "\n");
        String  commandName = "ВЫХОД",
                actual = Help.stringHelpPerCommand(commandName),
                expected = null;
        System.out.println(commandName);
        Assert.assertEquals(expected, actual);
    }
}
