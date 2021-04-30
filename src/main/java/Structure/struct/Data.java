package Structure.struct;

public class Data {
    public boolean type; // существует или удалён
    public String name;
    public int size;// кол-во блоков

    //Создали
    public Data(String name, int size) {
        this.type = true;
        this.name = name;
        this.size = size;
    }

    //Прочитали
    public boolean isType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        return "Data{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }

    //Изменили
    public void setType(boolean type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Увеличить (уменьшить) размер файла на заданную велечину
     *
     * @param size
     */
    public void addSize(int size) {
        this.size += size;
    }
}
