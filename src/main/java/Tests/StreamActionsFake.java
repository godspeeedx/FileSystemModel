package Tests;

import Structure.struct.iStreamActions;

import java.util.ArrayList;
import java.util.Scanner;

public class StreamActionsFake implements iStreamActions {

    ArrayList<String> stringListInput = new ArrayList<String>();
    ArrayList<String> stringListOutput = new ArrayList<String>();

    public void println(String value) {
        stringListOutput.add(value);
        stringListOutput.add("");
    }

    public void print(String value) {
        if      (stringListOutput.size() != 0 &&
                stringListOutput.get(stringListOutput.size() - 1).isEmpty())
            stringListOutput.add(value);
        else
            stringListOutput.add(stringListOutput.size() - 1, value);
    }

    public String getLine() {
        if (stringListInput.size()==0)
            return "ВЫЙТИ";
        return stringListInput.remove(stringListInput.size() - 1);
    }


}
