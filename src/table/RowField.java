package table;

public class RowField extends Field {
    private Object value;

    public Object getValue() {
        return value;
    }

    public RowField(String name, String type, String size) {
        super(name, type, size);
    }

    public RowField(String name, String type, String size, Object value) {
        super(name, type, size);
        this.value=value;
    }
}
