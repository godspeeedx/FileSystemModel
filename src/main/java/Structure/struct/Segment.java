package Structure.struct;

import java.util.ArrayList;

public class Segment {
    int ID;
    int firstBlockNumber;
    ArrayList<Data> datas;

    public Segment(int ID, int firstBlockNumber) {
        this.ID = ID;
        this.firstBlockNumber = firstBlockNumber;
        this.datas = new ArrayList<Data>();
    }

    public int getID() {
        return ID;
    }

    public int getFirstBlockNumber() {
        return firstBlockNumber;
    }

    public ArrayList<Data> getDatas() {
        return datas;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFirstBlockNumber(int firstBlockNumber) {
        this.firstBlockNumber = firstBlockNumber;
    }

    public void setDatas(ArrayList<Data> datas) {
        this.datas = datas;
    }
}
