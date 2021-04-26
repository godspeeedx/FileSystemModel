package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;

public class CreateFile implements iCommand {
    @Override
    public void Execute(FileSystem fs) {
        //fs.segments.get(0).datas.get(0).setName("Проверка");
        System.out.println("heeeeerr!!!!!!!!");
    }

    @Override
    public void ReadParameters() {

    }
}
