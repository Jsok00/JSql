package table;

public class Field {
    private String name;
    private String type;
    private String size;
    private Object value;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public Object getValue() {
        return value;
    }

    public Field(String name, String type, String size){
        this.name=name;
        this.type=type;
        this.size=size;
    }
    public Field(String name, String type, String size,Object value){
        this.name=name;
        this.type=type;
        this.size=size;
        this.value=value;
    }


}
