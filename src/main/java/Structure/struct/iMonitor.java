package Structure.struct;

public interface iMonitor {
    void writeMessage(String userMessage);

    int readInt(String userMessage);
    String readString(String userMessage);

    @Deprecated
    int readFileSize(String userMessage);
    @Deprecated
    int readSystemSize(String userMessage);
    @Deprecated
    int readMaxSegmentNum(String userMessage);
    @Deprecated
    int readMaxDataNum(String userMessage);
}
