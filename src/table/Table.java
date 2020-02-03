package table;

import bplustree.BPlusTree;

public class Table {
    //字段数量
    private Integer number;
    //表名
    private String name;
    //字段存储
    private Field[] fields;
    //主键名称
    private String primaryKey;
    //行索引
    private Integer index;

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Field[] getFields() {
        return fields;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getPrimaryKeyNum(){
        for(int i=0;i<fields.length;i++){
            if(fields[i].getName()==primaryKey){
                return i;
            }
        }
        return -1;
    }

    public Table(String name, Field[] fields, String primaryKey, Integer index){
        this.name=name;
        this.fields=fields;
        this.primaryKey=primaryKey;
        this.number=fields.length;
        this.index=index;
    }
}
