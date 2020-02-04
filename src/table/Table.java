package table;

import bplustree.BPlusTree;

import java.util.ArrayList;

public class Table {
    //表所含字段数量
    private Integer number;
    //表名
    private String name;
    //字段存储
    private ArrayList<Field> fields;
    //主键名称
    private String primaryKey;
    //行索引 主键在数组中的位置 ???
    private Integer index;

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Integer getIndex() {
        return index;
    }


    public Table(String name, ArrayList<Field> fields, String primaryKey, Integer index){
        this.name=name;
        this.fields=fields;
        this.primaryKey=primaryKey;
        this.number=fields.size();
        this.index=index;
    }

    @Override
    public String toString() {
        return "Table{" +
                "fields=" + fields +
                '}';
    }
}
