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
            String string = "";
            for (int i = 0; i < fs.segments.size(); i++) {
                string += "Сегмент " + i + "\n";
                for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                    string += fs.segments.get(i).datas.get(j).name +
                    " " + fs.segments.get(i).datas.get(j).size +  "\n";
                }
            }
            return string;
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
