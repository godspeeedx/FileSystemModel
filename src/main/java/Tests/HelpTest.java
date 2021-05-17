package Tests;

import Functions.*;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HelpTest {

    //Создание списка задействанных команд. Временный, потому что почему-то не работает импорт из Monitor.RegistredCommands
    public static Map<String, String> registeredCommands = new HashMap<>();
    public static void init() {
        registeredCommands.put("ИЗМЕНИТЬ РАЗМЕР", ChangeFileSize.class.getName());
        registeredCommands.put("СОЗДАТЬ ФАЙЛ", CreateFile.class.getName());
        registeredCommands.put("СОЗДАТЬ СИСТЕМУ", CreateSystem.class.getName());
        registeredCommands.put("ДЕФРАГМЕНТАЦИЯ", Defragmentation.class.getName());
        registeredCommands.put("УДАЛИТЬ ФАЙЛ", DeleteFile.class.getName());
        registeredCommands.put("ЗАГРУЗИТЬ СИСТЕМУ", DownloadSystem.class.getName());
        registeredCommands.put("НАПЕЧАТАТЬ", Print.class.getName());
        registeredCommands.put("НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ", PrintInAlphabetOrder.class.getName());
        registeredCommands.put("ПОМОГИТЕ", Help.class.getName());
        registeredCommands.put("ИНФОРМАЦИЯ", GetSysInfo.class.getName());
    }


    @Test
    public void checkList() {
        System.out.println("Тестирование вывода правильной подсказки из списка: " + "\n");
        init(); //инициализация списка задействанных команд
        for (String key : registeredCommands.keySet()) { //для каждой команды в списке
            String  commandName = key, //достаём её имя
                    actual = Help.stringHelpPerCommand(commandName), //запускаем нашу функцию по имени команды, должны в ответ получить то, что выведется на экран
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
                    }; //в свитче ищем что для этой функции реально должно было вывестись на экран (код скопирован из Help.java)
            System.out.println(commandName); //поприколу выводим функцию
            Assert.assertEquals(expected, actual); //сравниваем что выдало, что ожидали
        }
    }

    //генерация случайной строки в 20 букв
    public String generatingRandomString() {
        byte[] array = new byte[20]; // масив в 20букв
        new Random().nextBytes(array); //в каждый кладём случайное число
        return new String(array, Charset.forName("UTF-8")); //преобразуем число в букву по utf-8
    }

    /**
     * Генерируем случайную строку и загоняем её. Должны получить отсутствие справки
     */
    @Test
    public void checkRandom() {
        System.out.println("Тестирование вывода подсказки по несуществующей команде: " + "\n");
        String  commandName = generatingRandomString(), //в качестве команды задаём случайную строку
                actual = Help.stringHelpPerCommand(commandName), //запихиваем её в Хелп. Должны получить отсутсвие справки
                expected = "Кажется по введённой вами команде ещё нет справки. Или вы ошиблись вводом. Попробуем ещё раз?"; //чё должны получить
        System.out.println(commandName); //выводим по приколу
        Assert.assertEquals(expected, actual); //сравниваем
    }
    /**
     * Загоняем ВЫХОД. Должны получить нул (выход).
     */
    @Test
    public void checkExit() {
        System.out.println("Тестирование вывода подсказки по несуществующей команде: " + "\n");
        String  commandName = "ВЫХОД",
                actual = Help.stringHelpPerCommand(commandName); //загоняем ВЫХОД в Хелп.
        System.out.println(commandName);
        Assert.assertNull(actual); //сравниваем, должны получить нуль
    }
}
