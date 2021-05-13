package Functions;

import Structure.struct.Data;
import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

import java.util.*;

public class PrintInAlphabetOrder extends BaseCommand implements iCommand {

    public PrintInAlphabetOrder(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }


    public static String toString(FileSystem fs) {
        if (fs.segments.size() == 0) {
            return "Файловая система пуста";
        } else {
            ArrayList<Data> arrayList = new ArrayList<>();
            for (int i = 0; i < fs.segments.size(); i++) {
                for (int j = 0; j < fs.segments.get(i).datas.size(); j++) {
                    if (fs.segments.get(i).datas.get(j).type) {
                        String name = fs.segments.get(i).datas.get(j).name;
                        int length = fs.segments.get(i).datas.get(j).size;
                        arrayList.add(new Data(name, length));
                    }
                }
            }
            if (arrayList.size() == 0) {
                return "Файловая система пуста";
            } else {
                StringBuilder files = new StringBuilder();
                arrayList.sort(Comparator.comparing(Data::getName));
                for (Data data : arrayList) {
                    files.append(data.name).append(" ").append(data.size).append("\n");
                }
                return files.toString();
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
