package Monitor;

import Functions.*;
import Structure.struct.*;

public class MonitorClass {
    FileSystem fs;
    iCommand actualCommand ;

    public void createSystem(){
        actualCommand = new CreateSystem();
        actualCommand.Execute(fs);
    }
    public void downloadSystem(){
        actualCommand = new DownloadSystem();
        actualCommand.Execute(fs);
    }

    public void createFile(){
        actualCommand = new CreateFile();
        actualCommand.Execute(fs);
    }

    public void help() {

    }

    public void info() {

    }

    public void exit() {
        actualCommand = new SaveSystem();
        actualCommand.Execute(fs);
    }



}
