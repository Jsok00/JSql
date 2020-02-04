package table;

import java.util.ArrayList;

public class RowTable extends Table {
    //字段数量
    private Integer number;
    //表名
    private String name;
    //字段存储
    private ArrayList<Field> fields;
    //主键名称
    private String primaryKey;
    //行索引
    private Integer index;

    public RowTable(String name, ArrayList<Field> fields, String primaryKey, Integer index) {
        super(name, fields, primaryKey, index);
    }







}
