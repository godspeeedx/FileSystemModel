package Functions;

import Structure.struct.FileSystem;
import Structure.struct.Segment;

public class MethodsForFunctions {

    public static boolean checkPossibleAdd(FileSystem fs, int length) {
        if (fs.segments.get(0).getLastBlockNumber() + length < fs.maxDataNum) {
            return true;
        }
        return false;
    }



    public static boolean checkAddInLastSegment(FileSystem fs, int length) {
        int segmentSize = fs.segments.size() - 1;
        if (fs.segments.get(segmentSize).currentDataNum == 3) {
            return false;
        } else {
            int occupied = 0;
            for (int i = 0; i < fs.segments.get(segmentSize).datas.size(); i++) {
                occupied += fs.segments.get(segmentSize).datas.get(i).getSize();
            }
            if (Segment.lengthSegment - occupied < length) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkFileExist(FileSystem fs, String fileName) {
        boolean findName = false;
        flag:
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                if (fs.segments.get(i).datas.get(j).getName().equals(fileName)) {
                    findName = true;
                    break flag;
                }
            }
        }
        return findName;
    }

}
