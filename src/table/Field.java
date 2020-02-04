package table;

public class Field {
    private String name;
    private String type;
    private String size;
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
    public String getSize() {
        return size;
    }
    //字段的值
    public Object getValue() {
        return value;
    }

    public Field(String name, String type, String size,Object value){
        this.name=name;
        this.type=type;
        this.size=size;
        this.value=value;
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
