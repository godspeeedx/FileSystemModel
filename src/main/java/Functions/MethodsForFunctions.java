package Functions;

import Structure.struct.DataRecord;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class MethodsForFunctions {

    public static int takeFileLength(FileSystem fs, String fileName) {
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) {
                if (fs.segments.get(i).dataRecords.get(j).type &&
                        fs.segments.get(i).dataRecords.get(j).getName().equals(fileName)) {
                    return fs.segments.get(i).dataRecords.get(j).size;
                }
            }
        }
        return -1;
    }

    public static int[] takeFilePosition(FileSystem fs, String fileName) {
        int[] positions = {-1, -1};
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) {
                if (fs.segments.get(i).dataRecords.get(j).type &&
                        fs.segments.get(i).dataRecords.get(j).getName().equals(fileName)) {
                    positions[0] = i;
                    positions[1] = j;
                    return positions;
                }
            }
        }
        return positions;
    }

    public static boolean checkFileExist(FileSystem fs, String fileName) {
        boolean findName = false;
        flag:
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) {
                if (fs.segments.get(i).dataRecords.get(j).type &&
                        fs.segments.get(i).dataRecords.get(j).getName().equals(fileName)) {
                    findName = true;
                    break flag;
                }
            }
        }
        return findName;
    }

    //максимальное место для вставки
    public static int maxToInsert(FileSystem fs) {
        int maximum = 0;
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) {
                if (fs.segments.get(i).dataRecords.get(j).type == false) {
                    if (maximum < fs.segments.get(i).dataRecords.get(j).size) {
                        maximum = fs.segments.get(i).dataRecords.get(j).size;
                    }
                }
            }
        }
        if(maxToInsertInEnd(fs) > maximum){
            return maxToInsertInEnd(fs);
        }
        return maximum;
    }

    public static int maxToInsertInEnd(FileSystem fs) {
        int max = 0;
        if (fs.segments.size() != 0) {
            int occupied = 0;
            for (int i = 0; i < fs.segments.size(); i++) {
                for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) {
                    occupied += fs.segments.get(i).dataRecords.get(j).size;
                }
            }
            int i = fs.segments.size() - 1;
            int j = fs.segments.get(i).dataRecords.size() - 1;
            if(!fs.segments.get(i).dataRecords.get(j).type){
                occupied -= fs.segments.get(i).dataRecords.get(j).size;
            }
            max = FileSystem.systemSize - occupied;
            return max;
        }
        max = FileSystem.systemSize;
        return max;
    }

    // сколько всего места
    public static int howMuchSpace(FileSystem fs) {
        int space = 0;
        for (int i = 0; i < fs.segments.size(); i++) { // пробег по всем сегментам
            for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) { // пробег по записям  в сегменте
                if (fs.segments.get(i).dataRecords.get(j).type) {
                    space += fs.segments.get(i).dataRecords.get(j).size;
                }
            }
        }
        return FileSystem.systemSize - space;
    }

    public static boolean possibleToInsert(FileSystem fs, int length) {
            if (length <= MethodsForFunctions.maxToInsert(fs)) {
                for (int i = 0; i < fs.segments.size(); i++) { // пробег по всем сегментам
                    for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) { // пробег по сзаписям  в сегменте
                        if (!fs.segments.get(i).dataRecords.get(j).type) { // вставляем на удалённое, когда всё совпало
                            if (length == fs.segments.get(i).dataRecords.get(j).size) {
                                return true;
                            } // случ когда вставляем в самый конец при том, что она была удалена
                            else if (length < fs.segments.get(i).dataRecords.get(j).size) {
                                if ((j != fs.maxDataNum - 1 && fs.segments.get(i).dataRecords.size() - j == 1) ||
                                        (j == fs.maxDataNum - 1 && fs.segments.size() - i == 1)) {
                                    return true;
                                } // если меньшк и следующий удалён
                                else {
                                    if (j + 1 < fs.segments.get(i).dataRecords.size()
                                            && !fs.segments.get(i).dataRecords.get(j + 1).type) {
                                       return true;
                                    }
                                }

                            } else {
                                if (i + 1 == fs.segments.size() && j + 1 == fs.segments.get(i).dataRecords.size()) {
                                    if (MethodsForFunctions.howMuchSpace(fs) >= length) {
                                       return true;
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
                if (fs.segments.get(segmentSize).dataRecords.size() != fs.maxDataNum) {
                    return true; // файл успешно создан
                }
                // Если добавляем в новый сегмент
                else {
                    if (fs.segments.size() < fs.maxSegmentNum) {
                        return true; // файл успешно создан

                    }
                    else {
                        return false; // не хватило места
                    }
                }
            } else {
                return false;// не хватило места
            }

        }


    public static boolean fileSizeLogic(int fileLength) {
        if (fileLength < 0 || fileLength > FileSystem.systemSize) {
            System.out.println("Длина файла некорректна");
            return true;
        } else
            return false;
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

    public static double averageLengthToInsert(FileSystem fs) {
        if (maxToInsert(fs) == howMuchSpace(fs)) return maxToInsert(fs);
        int sum = 0;
        int n = 0;
        for (int i = 0; i < fs.segments.size(); i++) { // пробег по всем сегментам
            for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) { // пробег по записям  в сегменте
                if (!fs.segments.get(i).dataRecords.get(j).type) {
                    sum += fs.segments.get(i).dataRecords.get(j).size;
                    n++;
                }
            }
        }
        return n != 0 ? (double) sum / n : 0;
    }

    public static boolean checkDef(FileSystem fs) {
        return defragExt(fs) > 0.15;
    }

    public static double defragExt(FileSystem fs) {
        int space = MethodsForFunctions.howMuchSpace(fs);
        double maxLength = MethodsForFunctions.maxToInsert(fs);
        double averageLength = MethodsForFunctions.averageLengthToInsert(fs);
        if (maxLength == 0) return 0;
        double a;
        try {
            a = 1 - (((maxLength / space) + averageLength / maxLength) / 2);
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException occurred!");  //неправильный вывод
            a = 0;
        }
        BigDecimal bd = new BigDecimal(Double.toString(a));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

}
