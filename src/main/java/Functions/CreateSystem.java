package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;

public class CreateSystem implements iCommand {
    @Override
    public void Execute(FileSystem fs) {
        System.out.println("heeeeerr");
    }

    @Override
    public void ReadParameters() {

    }
}
