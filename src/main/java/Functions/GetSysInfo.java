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
        String check;
        if (MethodsForFunctions.checkDef(fs)){
            check = "Дефрагментация необходима";
        }else {
            check = "Дефрагментация не необходима";
        }
        return "Осталось вот столько памяти " + space +
                "\nМаксимальный файл,который можно вставить " + maxLength +
                "\nСтепень фрагментации " + MethodsForFunctions.defragExt(fs)+
        "\n" + check;
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
