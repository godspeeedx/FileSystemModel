package Structure.struct;

import java.util.ArrayList;

public class FileSystem {
    public String systemName;
    public int systemSize;

    public int maxSegmentNum;
    public ArrayList<Segment> segments;
    public int maxDataNum;

    public FileSystem(int maxSegmentNum, String systemName, int systemSize, int maxDataNum) {
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
