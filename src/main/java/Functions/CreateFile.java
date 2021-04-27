package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;

public class CreateFile implements iCommand {
    public static boolean checkPossibleAdd(FileSystem fs, int length){
        if(fs.segments.get(0).getLastBlockNumber() + length < fs.maxDataNum){
            return true;
        }
        return false;
    }

    public static boolean createFile (FileSystem fs, String filename, int length){
       boolean check =  ChangeFileSize.checkFileExsist(fs, filename);
       if (check = true){
           return false;
       } else {

           if(checkPossibleAdd(fs, length)){
             int segmentSize =  fs.segments.size() - 1;

           }
           return true;
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
