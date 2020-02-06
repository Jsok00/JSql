package test;
import ast.statement.Statement;
import jsql.JSqlDatabase;
import parser.Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;


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


            FileOutputStream fos = new FileOutputStream("C:\\Users\\Jsok\\Desktop\\database.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(jSqlDatabase);
            oos.close();
            fos.close();
            System.out.println("序列化ok");

        FileInputStream fis = new FileInputStream("C:\\Users\\Jsok\\Desktop\\database.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        jSqlDatabase = (JSqlDatabase) ois.readObject();
        ois.close();
        fis.close();

        Parser parser = new Parser();
        LinkedList<Statement> stmts=parser.Parse("select age from user ;");
        LinkedList<String[]>str= stmts.get(0).gen();
        for(String[] s : str){
            if (s.length == 4){
                for (Object str2:jSqlDatabase.selectByName(s[3], s[1]))
                System.out.println(str2);
            }
        }
    }
}
