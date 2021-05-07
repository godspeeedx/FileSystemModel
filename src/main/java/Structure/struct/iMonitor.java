package Structure.struct;

public interface iMonitor {
    void writeMessage(String userMessage);

    String readString(String userMessage);

    int readFileSize(String userMessage);

    int readSystemSize(String userMessage);

    int readMaxSegmentNum(String userMessage);

    int readMaxDataNum(String userMessage);
}
