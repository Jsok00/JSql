package jsql;

import bplustree.BPlusTree;
import table.Field;
import table.RowTable;
import table.Table;

import java.io.Serializable;
import java.util.HashMap;


//这是一个数据库，一张表的数据存放在一棵树里
public class JSqlDatabase implements Serializable {
    //存储表的B+树
    public BPlusTree<Table,Integer> bPlusTree = new BPlusTree<>(4);
    //数据库名称
    private String name;
    private HashMap<String,Table> tables;

    public BPlusTree<Table, Integer> getbPlusTree() {
        return bPlusTree;
    }

    public void setbPlusTree(BPlusTree<Table, Integer> bPlusTree) {
        this.bPlusTree = bPlusTree;
    }

    //获得数据库名称
    public String getName() {
        return name;
    }

    /**
     * 根据名称创建数据库
     * @param name
     */
    public JSqlDatabase(String name){
        this.name=name;
        this.tables=new HashMap<>();
    }

    public void createTable(String name){
        Table table = new Table(name);
        table.initRowTable();
        tables.put(name,table);
    }

    public void addTableField(String fieldName, String fieldType, int fieldSize, String tableName, boolean isKey){
        Table table = this.tables.get(tableName);
        table.addField(fieldName,fieldType,fieldSize, isKey);
    }

    public void removeTableField(String fieldName, String tableName){
        Table table = this.tables.get(tableName);
        table.removeField(fieldName);
    }

    public void addRowTableField(String fieldName, Object value, String tableName){
        Table table = this.tables.get(tableName);
        table.addRowTableField(fieldName, value);
    }

    public void insertRowTable(String tableName){
        Table table = this.tables.get(tableName);
        table.insertRowTable();

    }

    public Object[] selectAll(String tableName){
        Table table = this.tables.get(tableName);
        return table.selectAll();
    }

    public Object selectByKey(String tableName, Object key){
        Table table = this.tables.get(tableName);
        return table.selectByKey((Integer) key);
    }

    public Object[] selectByName(String tableName, String fieldName){
        Table table = this.tables.get(tableName);

        return table.selectByName(fieldName);
    }


    public Object[] selectByName2(String name) throws Exception {
        int index = 0;
        Object[] tables =  bPlusTree.findAll();
        Field[] fields = new Field[tables.length-1];
        for (int j =0; j<((Table)tables[0]).getFields().size(); j++){
            if(((Table)tables[0]).getFields().get(j).getName().equals(name)){
                index=j;
            }
        }


        for (int i = 1; i<tables.length; i++){
            if((Table)tables[i] != null)
            fields[i-1]=((Table)tables[i]).getFields().get(index);
        }
        return fields;
    }


}
