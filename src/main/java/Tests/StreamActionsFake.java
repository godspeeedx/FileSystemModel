package Tests;

import Structure.struct.iStreamActions;

import java.util.ArrayList;
import java.util.Scanner;

public class StreamActionsFake implements iStreamActions {

    ArrayList<String> stringListInput = new ArrayList<String>();
    ArrayList<String> stringListOutput = new ArrayList<String>();

    public void println(String value) {
        print(value);
        stringListOutput.add("");

    }
        

    public void print(String value) {
        if (stringListOutput.isEmpty()) stringListOutput.add("");

        stringListOutput.add(stringListOutput.remove(stringListOutput.size()-1)+value);

       
    }

    public String getLine() {
        if (stringListInput.isEmpty())
            return "ВЫЙТИ";
        return stringListInput.remove(stringListInput.size() - 1);
    }


}
