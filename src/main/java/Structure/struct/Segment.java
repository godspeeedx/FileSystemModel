package Structure.struct;

import java.util.ArrayList;

public class Segment {
   // static public int lastBlockNumber = 1; // Последний занятый блок во всей файловой системе
    //static public final int lengthSegment = 10;
    public int firstBlockNumber; //Номер блока с которого начинаются файлы сегмента

    public ArrayList<DataRecord> dataRecords; // Массив записей о файле
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
        if(this.dataRecords.size()!= other.dataRecords.size()){
            return false;
        }
        for (int i = 0; i < this.dataRecords.size(); i++) {
            if(!this.dataRecords.get(i).equals(other.dataRecords.get(i))){
                return false;
            }
        }

        return true;

    }

    public Segment(int maxDataNum) {
      //  firstBlockNumber = lastBlockNumber ;

        this.dataRecords = new ArrayList<>(maxDataNum);
        currentDataNum = 0;
    }
}
