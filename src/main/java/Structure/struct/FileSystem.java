package Structure.struct;

import java.util.ArrayList;

public class FileSystem {
    public String systemName; // имя файловой системы
    public int systemSize; // количество блоков
    public int maxSegmentNum; // максимальное число сегментов
    public ArrayList<Segment> segments; // Массив сегментов
    public int maxDataNum; // Число записей в каждом сегменте(максимальная длина сегмента )

    public FileSystem(String systemName, int systemSize, int maxSegmentNum, int maxDataNum) {
        this.systemName = systemName;
        this.systemSize = systemSize;

        this.maxSegmentNum = maxSegmentNum;
        this.segments = new ArrayList<>();
        this.maxDataNum = maxDataNum;
    }

    @Override
    public String toString() {
        return "FileSystem{" +
                "systemName='" + systemName + '\'' +
                ", systemSize=" + systemSize +
                ", maxSegmentNum=" + maxSegmentNum +
                ", maxDataNum=" + maxDataNum +
                '}';
    }
}
