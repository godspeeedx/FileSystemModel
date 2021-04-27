package Structure.struct;

import java.util.ArrayList;

public class Segment {
    static int lastBlockNumber = 0; // Последний занятый блок во всей файловой системе

    public int firstBlockNumber; //Номер блока с которого начинаются файлы сегмента

    public ArrayList<Data> datas; // Массив записей о файле
    public int currentDataNum; //Число занятых записей


    public Segment(int maxDataNum) {
        firstBlockNumber = lastBlockNumber + 1;

        this.datas = new ArrayList<Data>(maxDataNum);
        currentDataNum = 0;
    }
}
