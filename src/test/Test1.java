package test;
import ast.statement.Statement;
import bplustree.BPlusTree;
import jsql.JSql;
import jsql.JSqlDatabase;
import parser.Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class Test1 {

    public static void main(String[] args) throws Exception {
        JSqlDatabase jSqlDatabase = new JSqlDatabase("demoDatabase");
        jSqlDatabase.createTable("user");
        jSqlDatabase.addTableField("id","int", 11, "user", true);
        jSqlDatabase.addTableField("name","string", 11, "user", false);
        jSqlDatabase.addTableField("age","int", 11, "user", false);

        jSqlDatabase.addRowTableField("id", 1, "user");
        jSqlDatabase.addRowTableField("name", "蒋卓伦", "user");
        jSqlDatabase.addRowTableField("age", 21, "user");
        jSqlDatabase.insertRowTable("user");

        jSqlDatabase.addRowTableField("id", 2, "user");
        jSqlDatabase.addRowTableField("name", "周杰伦", "user");
        jSqlDatabase.addRowTableField("age", 40, "user");
        jSqlDatabase.insertRowTable("user");


        jSqlDatabase.addRowTableField("id", 3, "user");
        jSqlDatabase.addRowTableField("name", "蒋卓伦", "user");
        jSqlDatabase.addRowTableField("age", 7, "user");
        jSqlDatabase.insertRowTable("user");
        jSqlDatabase.addRowTableField("id", 4, "user");
        jSqlDatabase.addRowTableField("name", "蒋卓伦", "user");
        jSqlDatabase.addRowTableField("age", 8, "user");
        jSqlDatabase.insertRowTable("user");
        jSqlDatabase.addRowTableField("id", 5, "user");
        jSqlDatabase.addRowTableField("name", "蒋卓伦", "user");
        jSqlDatabase.addRowTableField("age", 5, "user");
        jSqlDatabase.insertRowTable("user");
        Date startInsert = new Date();

        for (int i =6 ;i<10000;i++){
            jSqlDatabase.addRowTableField("id", i, "user");
            jSqlDatabase.addRowTableField("name", "蒋卓伦", "user");
            jSqlDatabase.addRowTableField("age", i, "user");
            jSqlDatabase.insertRowTable("user");
        }
        Date startInput = new Date();



        JSql jSql = new JSql();
        jSql.saveDatabase(jSqlDatabase);

        Date startOutput = new Date();

        JSqlDatabase jSqlDatabase1 = jSql.getDatabase(jSqlDatabase.getName());

        Date startSelectAll = new Date();

        Parser parser = new Parser();
        LinkedList<Statement> stmts=parser.Parse("select age from user ;");
        ArrayList<String[]> str= stmts.get(0).gen();
        for(String[] s : str){
            if (s.length == 4){
                for (Object str2:jSqlDatabase.selectByName(s[3], s[1]))
                System.out.println(str2);
            }
        }

        Date selectByKey = new Date();

        jSqlDatabase.selectByKey("user",9999);


        Date e = new Date();
        long interval1 = (startInput.getTime() - startInsert.getTime())/1000;
        System.out.println("插入时间相差"+interval1+"秒");//会打印出相差3秒

        long interval2 = (startOutput.getTime() - startInput.getTime())/1000;

        System.out.println("序列化时间相差"+interval2+"秒");//会打印出相差3秒

        long interval3 = (startSelectAll.getTime() - startOutput.getTime())/1000;
        System.out.println("反序列化时间相差"+interval3+"秒");//会打印出相差3秒

        long interval4 = (selectByKey.getTime() - startSelectAll.getTime())/1000;
        System.out.println("查找所有数据时间相差"+interval4+"秒");//会打印出相差3秒

        long interval5 = (e.getTime() - selectByKey.getTime())/1000;
        System.out.println("查找单个数据时间相差"+interval5+"秒");//会打印出相差3秒

    }
}
