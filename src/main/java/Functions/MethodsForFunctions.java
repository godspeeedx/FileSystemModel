package Functions;

import Structure.struct.FileSystem;
import Structure.struct.Segment;

public class MethodsForFunctions {

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
