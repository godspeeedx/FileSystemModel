package Tests;

import Functions.CreateSystem;
import Monitor.ActualProgram;
import Monitor.MonitorClass;
import Monitor.RegistredCommands;
import Structure.struct.FileSystem;
import Structure.struct.iStreamActions;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ActualProgramTest {
    static FileSystem fs = new FileSystem("", 0, 0, 0);
    static MonitorClass monitor = new MonitorClass(fs);


/**
* Ради всего святого поправьте тут форматирование и вообще откомпилируйте это ._.
  */
@Test
public void checkMainRealization1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        StreamActionsFake stream = new StreamActionsFake();

ArrayList<String> expected = new ArrayList<String>();
expected.add("(Если не помните команду, вводите ПОМОГИТЕ)");
expected.add("$");
expected.add("(Если не помните команду, вводите ПОМОГИТЕ)");

stringListInput.add("БЕЛЕБЕРДА");
stringListInput.add("ВЫЙТИ");


  ActualProgram.mainRealization(stream);
                        //Assert.assertEquals(stringListOutput.get);


                            }

//Проверка ввода Белеберда и СОЗДАТЬ СИСТЕМУ
@Test
    public void checkMainInit1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        StreamActionsFake stream = new StreamActionsFake();
ArrayList<String> expected = new ArrayList<String>();
     expected.add("\nДоброе утро! Вас приветствует группа С18-501!" + "\n" + "Загружаем систему или создаем новую?");
                        expected.add("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");
      expected.add("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");                                  
                                                              expected.add("Введите название системы:");
                                                                             expected.add("Введите размер диска:");
                                                                                                expected.add("Введите максимальное число сегментов:");
                                                                                                                   expected.add("Введите максимальное число записей в каждом сегменте:");
                                                                                                                                      expected.add("Готово!");
                                                                                                                                                         expected.add("Система сохранена");
stringListInput.add("БЕЛЕБЕРДА");
stringListInput.add("СОЗДАТЬ СИСТЕМУ");
stringListInput.add("Диск1");
stringListInput.add("2");
stringListInput.add("4");
stringListInput.add("8");
    

ActualProgram.init(stream);
Assert.assertEquals(stringListOutput.get(), expected);
 }


//Проверка ввод ЗАГРУЗИТЬ СИСТЕМУ
@Test
    public void checkMainInit1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    StreamActionsFake stream = new StreamActionsFake();
MethodsForFunctions.saveSystem(new FileSystem("Диск1", 2, 4, 8);

ArrayList<String> expected = new ArrayList<String>();
     expected.add("\nДоброе утро! Вас приветствует группа С18-501!" + "\n" + "Загружаем систему или создаем новую?");
                        expected.add("(Введите СОЗДАТЬ СИСТЕМУ или ЗАГРУЗИТЬ СИСТЕМУ)");
      expected.add("Введите название системы для загрузки:");                    
expected.add("Не удалось загрузить систему, введите повторное имя системы" );
                                                                                                                                                         expected.add("Система сохранена");

stringListInput.add("ЗАГРУЗИТЬ СИСТЕМУ");
stringListInput.add("ДИСКЕТА101");
stringListInput.add("Диск1");


ActualProgram.init(stream);
Assert.assertEquals(stringListOutput.get(), expected);
 }
