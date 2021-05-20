package Monitor;

import Structure.struct.iStreamActions;

import java.util.Scanner;

public class StreamActions implements iStreamActions {

    public void println(String value){
        System.out.println(value);
    }
    public void print(String value){
        System.out.print(value);
    }
    public String getLine(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }
    public int nextInt(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

}


