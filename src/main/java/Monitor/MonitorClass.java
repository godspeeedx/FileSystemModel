package Monitor;

import Functions.*;
import Structure.struct.*;

import java.util.Scanner;

public class MonitorClass implements iMonitor{
    FileSystem fs;
    iCommand actualCommand ;

    MonitorClass(FileSystem fs) {
        this.fs = fs;
    }

    public void changeFileSize(){
        actualCommand = new ChangeFileSize(this,fs);
        actualCommand.execute(fs);
    }

    public void createFile(){
        actualCommand = new CreateFile(this,fs);
        actualCommand.execute(fs);
    }

    public void createSystem(){
        actualCommand = new CreateSystem(this,fs);
        //КОСТЫЛЬ
        fs = new FileSystem("",0,0,0);
        //КОСТЫЛЬ
        actualCommand.execute(fs);
    }

    public void defragmentation(){
        actualCommand = new Defragmentation(this,fs);
        actualCommand.execute(fs);
    }

    public void deleteFile(){
        actualCommand = new DeleteFile(this,fs);
        actualCommand.execute(fs);
    }

    public void downloadSystem(){
        actualCommand = new DownloadSystem(this,fs);
        actualCommand.execute(fs);
    }

    public void printSystem(){
        actualCommand = new Print(this,fs);
        actualCommand.execute(fs);
    }

    public void printSystemInOrder(){
        actualCommand = new PrintInAlphabetOrder(this,fs);
        actualCommand.execute(fs);
    }

    public void saveSystem(){
        actualCommand = new SaveSystem(this,fs);
        actualCommand.execute(fs);
    }


    public void help() {

    }

    public void info() {

    }

    @Override
    public void writeMessage(String userMessage) {
        System.out.println(userMessage);
    }

    @Override
    public String readString(String userMessage) {
        ////временно без проверок
        Scanner sc = new Scanner(System.in);
        String str;
        System.out.println(userMessage);
        str = sc.nextLine();
        return str;
    }

    @Override
    public int readFileSize(String userMessage) {
        //вот тута. пример в createSystem
        return 0;
    }

    @Override
    public int readSystemSize(String userMessage) {
        //временно без проверок
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readMaxSegmentNum(String userMessage) {
        //временно без проверок
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

    @Override
    public int readMaxDataNum(String userMessage) {
        //временно без проверок
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.println(userMessage);
        num = sc.nextInt();
        return num;
    }

}
