package Structure.struct;

import java.util.ArrayList;

public class Segment {
    static int lastBlockNumber = 0; // Последний зан я тый блок во всей файловой системе
    static public final  int lengthSegment = 10;
    public int firstBlockNumber; //Номер блока с которого начинаются файлы сегмента

    public ArrayList<Data> datas; // Массив записей о файле
    public int currentDataNum; //Число занятых записей

    public int getLastBlockNumber() {
        return lastBlockNumber;
    }

    public Segment(int maxDataNum) {
        firstBlockNumber = lastBlockNumber + 1;

        this.datas = new ArrayList<Data>(maxDataNum);
        currentDataNum = 0;
    }
}
