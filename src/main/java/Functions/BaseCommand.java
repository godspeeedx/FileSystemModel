package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iMonitor;

public class BaseCommand {

    protected iMonitor monitor;
    protected FileSystem fileSystem;

    public BaseCommand(iMonitor im, FileSystem fileSystem) {
        this.monitor = im;
        this.fileSystem = fileSystem;
    }

    public BaseCommand(){
        monitor = null;
        fileSystem = null;
    }

}
