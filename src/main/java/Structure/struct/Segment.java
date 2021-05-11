package Structure.struct;

import java.util.ArrayList;

public class Segment {
    static public int lastBlockNumber = 1; // Последний занятый блок во всей файловой системе
    //static public final int lengthSegment = 10;
    public int firstBlockNumber; //Номер блока с которого начинаются файлы сегмента

    public ArrayList<Data> datas; // Массив записей о файле
    public int currentDataNum; //Число занятых записей

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        /*if (!CreateFile.class.isAssignableFrom(obj.getClass())) {
            return false;
        }*/
        final Segment other = (Segment) obj;

        //If both currentDataNum are not equal return false
        if (this.currentDataNum != other.currentDataNum) {
            return false;
        }
        //If both datas are not equal return false
        if(this.datas.size()!= other.datas.size()){
            return false;
        }
        for (int i = 0; i < this.datas.size(); i++) {
            if(!this.datas.get(i).equals(other.datas.get(i))){
                return false;
            }
        }

        return true;

    }

    public Segment(int maxDataNum) {
        firstBlockNumber = lastBlockNumber ;

        this.datas = new ArrayList<>(maxDataNum);
        currentDataNum = 0;
    }
}
