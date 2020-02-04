package jsql;

import bplustree.BPlusTree;
import table.Field;
import table.RowTable;
import table.Table;

import java.util.ArrayList;

public class JSql {
    //存储表的B+树
    public BPlusTree<Table,Integer> bPlusTree = new BPlusTree<>(4);
    //数据库名称
    private String name;

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

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param headTable 表结构
     * @param name 表名
     */
    public JSql(Table headTable, String name){
        bPlusTree.insert(headTable,headTable.getIndex());
        this.name=name;
    }
    /**
     * 插入行
     * @param rowTable
     */
    public void insert(RowTable rowTable){
        bPlusTree.insert(rowTable,(Integer) rowTable.getFields().get(rowTable.getIndex()).getValue());
    }

    public Object selectByKey(Object key){
        return bPlusTree.find((Integer) key).getFields();
    }

    public Object[] selectAll(){
        return bPlusTree.findAll();
    }
    public Object[] selectByName(String name) throws Exception {
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
