package Structure.struct;

public class Data {
    boolean type;
    String name;
    int size;

    public Data(boolean type, String name, int size) {
        this.type = type;
        this.name = name;
        this.size = size;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Data{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
