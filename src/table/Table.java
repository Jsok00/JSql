package table;



import bplustree.BPlusTree;

import java.io.Serializable;
import java.util.HashMap;

public class Table implements Serializable {

    //表所含字段数量
    private Integer number;
    //表名
    private String name;
    //字段存储
    protected HashMap<String, Field> fields;
    //主键名称
    private String primaryKey;

    private RowTable rowTable;

    //存储表的B+树
    public BPlusTree<RowTable,Integer> rowTableBPlusTree;




    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public HashMap<String,Field> getFields() {
        return fields;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public RowTable getRowTable() {
        return rowTable;
    }


    public Table(String name){
        this.name = name;
        this.number = 0;
        fields = new HashMap<String, Field>();
        this.rowTableBPlusTree = new BPlusTree<>(80); //80
    }
    public void initRowTable(){
        this.rowTable=new RowTable(this.name);
    }

    public Object[] removeNullInArray(Object[] array){
        int index=0;
        for (Object object: array){
            if (object != null){
                index++;
            }
        }
        Object[] newArray = new Object[index];
        for (int i = 0; i < index; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
    //增加字段结构
    public void addField(String name, String type, int size, boolean isPrimaryKey){
        this.fields.put(name, new Field(name, type, size));
        this.number++;
        if(isPrimaryKey){
            this.primaryKey = name;
        }
    }

    //删除字段结构
    public void removeField(String name){
        this.fields.remove(name);
        this.number--;
    }

    //增加待插入行列表的字段数据
    public void addRowTableField(String fieldName, Object value){
        Field field = fields.get(fieldName);
        Field rowField = new Field(field.getName(),field.getType(),field.getSize(),value);
        this.rowTable.getFields().put(fieldName,rowField);
    }

    //插入待插入行列表
    public void insertRowTable(){
        if(this.rowTable.getFields().size() == this.number){
            if (rowTable.getFields().get(this.primaryKey).getValue().getClass().toString().equals("class java.lang.Integer")) {
                this.rowTableBPlusTree.insert(rowTable, (Integer) rowTable.getFields().get(this.primaryKey).getValue());
                //消除行列表里面的字段数据
            }else{
                this.rowTableBPlusTree.insert(rowTable, Integer.parseInt((String) rowTable.getFields().get(this.primaryKey).getValue()));
                //消除行列表里面的字段数据
            }
            this.rowTable = new RowTable(name);
        }

    }

    //根据主键值寻找行
    public Object selectByKey(Integer primaryKey){
        return this.rowTableBPlusTree.find(primaryKey);
    }

    public Object[] selectAll(){
        return removeNullInArray(this.rowTableBPlusTree.findAll());
    }

    //根据字段名寻找行
    public Object[] selectByName(String fieldName){
        Object[] rowTables = selectAll();
        Field[] fields = new Field[rowTables.length];
        for (int i =0; i<rowTables.length; i++){
            if(rowTables[i]!= null) {
                fields[i] = ((RowTable) rowTables[i]).getFields().get(fieldName);
            }
        }
        return fields;
    }

    @Override
    public String toString() {
        return "Table{" +
                "fields=" + fields.get("name").getName() +
                '}';
    }
}
