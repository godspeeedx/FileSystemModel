package Functions;

import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;

public class Print extends BaseCommand implements iCommand {

    public Print(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String toString(FileSystem fs) {

        if (fs.segments.size() == 0) {
            return "Файловая система пуста";
        } else {
            boolean isFile = false;
            StringBuilder string = new StringBuilder();
            for (int i = 0; i < fs.segments.size(); i++) {
                string.append("Сегмент ").append(i).append("\n");
                for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) {
                    if (fs.segments.get(i).dataRecords.get(j).type) {
                        isFile = true;
                        string.append(fs.segments.get(i).dataRecords.get(j).name).append(" ").append(fs.segments.get(i).dataRecords.get(j).size).append("\n");
                    }
                }
            }
            if (isFile) {
                return string.toString();
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
