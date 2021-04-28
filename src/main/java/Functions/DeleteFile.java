package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;

import java.util.Scanner;

public class DeleteFile implements iCommand {
    public static boolean deleteFile(FileSystem fs, String fileName){
        boolean findName = false;
        flag:
        for (int i = 0; i < fs.segments.size(); i++) {
            for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                if (fs.segments.get(i).datas.get(j).getName().equals(fileName)) {
                    fs.segments.get(i).datas.get(j).setType(false);
                    findName = true;
                    break flag;
                }
            }
        }
        return findName;
    }
    @Override
    public void Execute(FileSystem fs) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя файл");
        String fileName = in.nextLine();
        if(deleteFile(fs, fileName)){
            System.out.println("Файл удалён");
        } else {
            System.out.println("Файл не был найден");
        }
    }

    @Override
    public void ReadParameters() {

    }
}
