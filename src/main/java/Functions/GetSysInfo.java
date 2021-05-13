package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class GetSysInfo extends BaseCommand implements iCommand {
    public GetSysInfo(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }
    public static String getInfo(FileSystem fs){
        int space = MethodsForFunctions.howMuchSpace(fs);
        int maxLength = MethodsForFunctions.maxLengthToInsert(fs);
        return "Осталось вот столько памяти " + space +
                "\nМаксимальный кусок,который можно вставить " + maxLength +
                "\nСтепень фрагментации " + MethodsForFunctions.defragExt(fs);
    }

    @Override
    public void execute(FileSystem fs) {
        monitor.writeMessage(fs.toString());
        monitor.writeMessage(GetSysInfo.getInfo(fs));
    }

    @Override
    public void readParameters() {

    }
}
