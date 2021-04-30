package Tests;

import Functions.DeleteFile;
import Structure.struct.FileSystem;
import Structure.struct.iCommand;
import Structure.struct.iMonitor;
import org.junit.Assert;
import org.junit.Test;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.List;


public class DeleteFileTest {


    public class MonitorMock implements iMonitor{

        private List<String> readResults = new ArrayList<>();
        private List<String> writeResults = new ArrayList<>();

        public MonitorMock AddReadResult(String result){
            readResults.add(result);
            return this;
        }

        public String GetWriteResult(){

            return writeResults.get(0);
        }

        @Override
        public void writeMessage(String userMessage) {
            writeResults.add(userMessage);
        }

        @Override
        public String readString(String userMessage) {
            var result =  readResults.get(0);
            readResults.remove(0);
            return result;
        }
    }

    @Test
    public void test() {
        //Arrange
        FileSystem fs = new FileSystem("", 0, 0, 0);
        var im = (new MonitorMock())
                .AddReadResult("f1");
        iCommand delete = new DeleteFile(im, fs);
        //Monitor moc = new MonitorClass();
        //Act
        delete.readParameters();
        delete.execute(fs);
        //Assert
        var message = "Файл не был найден";
        var actual = im.GetWriteResult();
        Assert.assertEquals(message, actual);
    }

    @Test
    public void deleteFile() {
    }

    @Test
    public void execute() {
    }

    @Test
    public void readParameters() {
    }
}
