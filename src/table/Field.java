package table;

import java.io.Serializable;

public class Field implements Serializable {
    private String name;
    private String type;
    private int size;
    private Object value;

    //字段名称
    public String getName() {
        return name;
    }
    //字段类型
    public String getType() {
        return type;
    }
    //字段大小
    public int getSize() {
        return size;
    }
    //字段的值
    public Object getValue() {
        return value;
    }

    public Field(String name, String type, int size,Object value){
        this.name=name;
        this.type=type;
        this.size=size;
        this.value=value;
    }

    public Field(String name, String type, int size){
        this.name=name;
        this.type=type;
        this.size=size;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", value=" + value +
                '}';
    }
}
