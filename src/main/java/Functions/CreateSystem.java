package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;


public class CreateSystem extends BaseCommand implements iCommand {

    protected String systemName;
    protected int systemSize;
    protected int maxSegmentNum;
    protected int maxDataNum;

    public CreateSystem(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        fs.copy(new FileSystem(systemName, systemSize, maxSegmentNum, maxDataNum)); //
        monitor.writeMessage("Готово!");
        monitor.writeMessage(MethodsForFunctions.saveSystem(fs));
    }

    @Override
    public void readParameters() {
        this.systemName = monitor.readString("Введите название системы:");
        this.systemSize = monitor.readSystemSize("Введите размер диска:");
        this.maxSegmentNum = monitor.readMaxSegmentNum("Введите максимальное число сегментов:");
        this.maxDataNum = monitor.readMaxDataNum("Введите максимальное число записей в каждом сегменте:");
    }
}
