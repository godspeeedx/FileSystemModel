package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class DownloadSystem extends BaseCommand implements iCommand {

    public DownloadSystem(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {

    }

    @Override
    public void readParameters() {

    }
}