package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class Print extends BaseCommand implements iCommand {

    public Print(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }
    public static String toString(FileSystem fs){

        if (fs.segments.size() == 0){
            return "Файловая система пуста";
        } else {
            boolean isFile = false;
            String string = "";
            for (int i = 0; i < fs.segments.size(); i++) {
                string += "Сегмент " + i + "\n";
                for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                    if (fs.segments.get(i).datas.get(j).type) {
                        isFile = true;
                        string += fs.segments.get(i).datas.get(j).name +
                                " " + fs.segments.get(i).datas.get(j).size + "\n";
                    }
                }
            }
            if(isFile) {
                return string;
            } else {
                return "Файловая система пуста";
            }
        }

    }

    @Override
    public void execute(FileSystem fs) {
        monitor.writeMessage(toString(fs));
    }

    @Override
    public void readParameters() {
    }
}
