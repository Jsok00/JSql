package jsql;

import bplustree.BPlusTree;
import table.Table;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSql(Table table, String name){
        bPlusTree.insert(table,table.getIndex());
        this.name=name;

    }

    public void insert(Table table){
        bPlusTree.insert(table,(Integer) table.getFields()[table.getPrimaryKeyNum()].getValue());
    }
}
