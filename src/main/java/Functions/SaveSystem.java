package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class SaveSystem extends BaseCommand implements iCommand {

    public SaveSystem(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {

    }

    @Override
    public void readParameters() {

    }
}
