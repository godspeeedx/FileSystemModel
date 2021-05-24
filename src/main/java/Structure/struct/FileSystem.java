package Structure.struct;

import java.util.ArrayList;

public class FileSystem {
    public String systemName; // имя файловой системы
    public static int systemSize; // количество блоков
    public int maxSegmentNum; // максимальное число сегментов
    public ArrayList<Segment> segments; // Массив сегментов
    public int maxDataNum; // Число записей в каждом сегменте

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        /*if (!CreateFile.class.isAssignableFrom(obj.getClass())) {
            return false;
        }*/

        final FileSystem other = (FileSystem) obj;

        if(!this.systemName.equals(other.systemName)){
            return false;
        }

        if(this.systemSize != other.systemSize){
            return false;
        }

        if(this.maxDataNum != other.maxDataNum){
            return false;
        }

        //If both datas are not equal return false
        if(this.segments.size()!= other.segments.size()){
            return false;
        }
        for (int i = 0; i < this.segments.size(); i++) {
            if(!this.segments.get(i).equals(other.segments.get(i))){
                return false;
            }
        }

        return true;
    }


    public FileSystem(String systemName, int systemSize, int maxSegmentNum, int maxDataNum) {
        this.systemName = systemName;

        FileSystem.systemSize = systemSize;

        this.maxSegmentNum = maxSegmentNum;
        this.segments = new ArrayList<>();
        this.maxDataNum = maxDataNum;
    }

    public void copy(FileSystem buf) {
        this.systemName = buf.systemName;
        this.maxSegmentNum = buf.maxSegmentNum;
        this.maxDataNum = buf.maxDataNum;
        this.segments = buf.segments;
    }

    @Override
    public String toString() {
        return "Название системы: " + systemName + "\nРазмер диска: " + systemSize
                + "\nМаксимальное число сегментов: " + maxSegmentNum
                + "\nМаксимальное число записей в каждом сегменте: " + maxDataNum;

    }
}
