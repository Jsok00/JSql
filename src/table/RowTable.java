package table;


import java.util.HashMap;

public class RowTable extends Table{

    public RowTable(String name) {
        super(name);
        this.fields=new HashMap<>();

    }


    @Override
    public String toString() {
        return "RowTable{" +
                "fields=" + fields +
                '}';
    }
}
