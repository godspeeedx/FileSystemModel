package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class Help extends BaseCommand implements iCommand {

    public Help(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {

    }

    @Override
    public void readParameters() {

    }
}
