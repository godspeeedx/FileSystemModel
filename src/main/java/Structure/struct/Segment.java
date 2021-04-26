package Structure.struct;

import java.util.ArrayList;

public class Segment {
    static int lastBlockNumber = 0;

    public int firstBlockNumber;

    public ArrayList<Data> datas;
    public int currentDataNum;


    public Segment(int maxDataNum) {
        firstBlockNumber = lastBlockNumber + 1;

        this.datas = new ArrayList<Data>(maxDataNum);
        currentDataNum = 0;
    }
}
