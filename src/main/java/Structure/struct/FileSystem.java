package Structure.struct;

import java.util.ArrayList;

public class FileSystem {
    public String systemName;
    public int systemSize;

    public int maxSegmentNum;
    public ArrayList<Segment> segments;
    public int maxDataNum;

    public FileSystem(int maxSegmentNum, String systemName) {
        this.systemName = systemName;
        this.systemSize = systemSize;

        this.maxSegmentNum = maxSegmentNum;
        this.segments = new ArrayList<Segment>();
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
