package Structure.struct;

import java.util.ArrayList;

public class FileSystem {
    /***
     * Имя тома
     */
    String systemName;
    int maxSegmentNum;
    ArrayList<Segment> segments;
    int systemSize;
    int dataNum;

    public FileSystem(String systemName, int maxSegmentNum, int systemSize, int dataNum) {
        this.systemName = systemName;
        this.maxSegmentNum = maxSegmentNum;
        this.segments = new ArrayList<Segment>();
        this.systemSize = systemSize;
        this.dataNum = dataNum;
    }
}
