package Functions;

import Structure.struct.FileSystem;
import Structure.struct.Segment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;


public class MethodsForFunctions {

    public static boolean checkFileExist(FileSystem fs, String fileName) {
        boolean findName = false;
        flag:
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                if (fs.segments.get(i).datas.get(j).type &&
                        fs.segments.get(i).datas.get(j).getName().equals(fileName)) {
                    findName = true;
                    break flag;
                }
            }
        }
        return findName;
    }

    public static int howMuchSpace(FileSystem fs) {
        int space = 0;
        for (int i = 0; i < fs.segments.size(); i++) { // пробег по всем сегментам
            for (int j = 0; j < fs.segments.get(i).datas.size(); j++) { // пробег по записям  в сегменте
                if (!fs.segments.get(i).datas.get(j).type) {
                    space += fs.segments.get(i).datas.get(j).size;
                }
            }
        }
        space += FileSystem.systemSize - Segment.lastBlockNumber + 1;
        return space;
    }

    public static boolean possibleToInsert(FileSystem fs, int length) {
        if (length <= MethodsForFunctions.howMuchSpace(fs)) {
            for (int i = 0; i < fs.segments.size(); i++) { // пробег по всем сегментам
                for (int j = 0; j < fs.segments.get(i).datas.size(); j++) { // пробег по сзаписям  в сегменте
                    if (!fs.segments.get(i).datas.get(j).type) { // вставляем на удалённое, когда всё совпало
                        if (length == fs.segments.get(i).datas.get(j).size) {
                            return true;
                        } // случ когда вставляем в самый конец при том, что она была удалена
                        else if (length < fs.segments.get(i).datas.get(j).size) {
                            if ((j != fs.maxDataNum - 1 && fs.segments.get(i).datas.size() - j == 1) ||
                                    (j == fs.maxDataNum - 1 && fs.segments.size() - i == 1)) {
                                return true; // файл успешно создан
                            } // если меньшк и следующий удалён
                            else {
                                if (j + 1 < fs.segments.get(i).datas.size()
                                        && !fs.segments.get(i).datas.get(j + 1).type) {
                                    return true; // файл успешно создан
                                }
                            }

                        } else {
                            if (i + 1 == fs.segments.size() && j + 1 == fs.segments.get(i).datas.size()) {
                                if (FileSystem.systemSize - Segment.lastBlockNumber -
                                        fs.segments.get(i).datas.get(j).size > length) {
                                    return true; // файл успешно создан
                                }
                            }
                        }
                    }
                }
            }
            // Если добавляем в последний сегмент
            if (fs.segments.size() == 0) {
                return true;
            }
            int segmentSize = fs.segments.size() - 1;
            if (fs.segments.get(segmentSize).datas.size() != fs.maxDataNum) {
                return true;
            }
            // Если добавляем в новый сегмент
            else {
                return fs.segments.size() < fs.maxSegmentNum; // файл успешно создан
            }
        } else {
            return false;// не хватило места
        }

    }

    public static String saveSystem(FileSystem fs) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT);

        Gson gson = gsonBuilder.create();

        String json = gson.toJson(fs);

        //System.out.println(json);

        try {
            FileWriter writer = new FileWriter(fs.systemName + ".txt");
            writer.write(json);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return "Система сохранена";
    }

    public static int maxLengthToInsert(FileSystem fs) {
        int allSpace = howMuchSpace(fs);
        for (int i = allSpace; i > 0; i--) {
            if (possibleToInsert(fs, i)) {
                return i;
            }
        }
        return 0;
    }

    public static boolean checkDef(FileSystem fs) {
        return false;
    }

    public static int defragExt(FileSystem fs) {
        return 0;
    }

}
