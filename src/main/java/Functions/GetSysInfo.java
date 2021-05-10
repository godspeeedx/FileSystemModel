package Functions;
import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class GetSysInfo extends BaseCommand implements iCommand {
    public GetSysInfo(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }
    /*public static void getSysInfo(FileSystem fs){

    }*/
    @Override
    public void execute(FileSystem fs) {
        int space = MethodsForFunctions.howMuchSpace(fs);
        int maxLength = MethodsForFunctions.maxLengthToInsert(fs);
        monitor.writeMessage("Осталось вот столько памяти "+space);
        monitor.writeMessage("Максимальный кусок,который можно вставить "+maxLength);
        try {
            monitor.writeMessage("Степень фрагментации " + (1 - (double) maxLength/space));
        } catch (ArithmeticException ae) {
            monitor.writeMessage("Степень фрагментации " + 0);
        }

    }

    @Override
    public void readParameters() {

    }
}
