package Functions;

import Structure.struct.Data;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import Structure.struct.iCommand;

public class CreateFile implements iCommand {

    public static boolean createFile(FileSystem fs, String filename, int length) {
        boolean check = methodsForFunctions.checkFileExist(fs, filename);
        if (check = true) {
            return false;
        } else {

            if (methodsForFunctions.checkPossibleAdd(fs, length)) {
                if (methodsForFunctions.checkAddInLastSegment(fs, length)) {
                    int segmentSize = fs.segments.size() - 1;
                    fs.segments.get(segmentSize).datas.add(new Data(filename, length));
                    fs.segments.get(segmentSize).currentDataNum += 1;
                    Segment.lastBlockNumber += length;
                    return true;
                } else {
                    if (fs.segments.size() < fs.maxSegmentNum) {
                        fs.segments.add(new Segment(fs.maxDataNum));
                        int segmentSize = fs.segments.size() - 1;
                        Segment.lastBlockNumber = segmentSize * Segment.lengthSegment;
                        fs.segments.get(segmentSize).datas.add(new Data(filename, length));
                        fs.segments.get(segmentSize).currentDataNum += 1;
                        Segment.lastBlockNumber += length;
                    }


                }


            }
            return false;
        }
    }

    @Override
    public void Execute(FileSystem fs) {
        //fs.segments.get(0).datas.get(0).setName("Проверка");
        System.out.println("heeeeerr!!!!!!!!");
    }

    @Override
    public void ReadParameters() {

    }
}
