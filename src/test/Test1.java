package test;
import bplustree.BPlusTree;
import jsql.JSqlDatabase;
import table.Field;
import table.RowField;
import table.RowTable;
import table.Table;

import java.util.ArrayList;

public class Test1 {
     public static void print(String... s ){
        System.out.println(s[0]);
        System.out.println(s[1]);
        System.out.println(s[2]);
    }
    public static void main(String[] args) throws Exception {
        JSqlDatabase jSqlDatabase = new JSqlDatabase("demoDataBase");
        jSqlDatabase.createTable("user");
        jSqlDatabase.addTableField("id","int", 11, "user", true);
        jSqlDatabase.addTableField("name","string", 11, "user", false);
        jSqlDatabase.addTableField("age","int", 11, "user", false);
        jSqlDatabase.addRowTableField("id", 5, "user");
        jSqlDatabase.addRowTableField("name", "蒋卓伦", "user");
        jSqlDatabase.addRowTableField("age", 21, "user");
        jSqlDatabase.insertRowTable("user");

        jSqlDatabase.addRowTableField("id", 6, "user");
        jSqlDatabase.addRowTableField("name", "周杰伦", "user");
        jSqlDatabase.addRowTableField("age", 40, "user");
        jSqlDatabase.insertRowTable("user");

        System.out.println(jSqlDatabase.selectByKey("user",5));




    }
}
