package test;

import jsql.JSql;
import jsql.JSqlDatabase;

import java.util.Scanner;

public class Test2 {
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

        JSql jSql = new JSql(jSqlDatabase);
        //jSql.ExecuteCode("select name from user;", jSqlDatabase.getName());
        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.print("$JSql: ");
            String sql = sc.nextLine();
            if(sql.equals("exit")){
                System.out.println("Bye~");
                break;
            }
            jSql.ExecuteCode(sql, jSqlDatabase.getName()); //单独数据库

        }


    }
}
