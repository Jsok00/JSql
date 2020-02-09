package jsql;

import ast.statement.Statement;
import parser.Parser;
import table.Field;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class JSql {
    private HashMap<String,JSqlDatabase> jSqlDatabaseHashMap;

    public JSql(){

    }

    public JSql(JSqlDatabase jSqlDatabase){
        jSqlDatabaseHashMap = new HashMap<>();
        jSqlDatabaseHashMap.put(jSqlDatabase.getName(),jSqlDatabase);
    }

    public void ExecuteCode(String sql, String databaseName) throws Exception {
        Parser parser = new Parser();
        LinkedList<Statement> stmts=parser.Parse(sql);
        ArrayList<String[]>sqls = stmts.get(0).gen();
        int index = 0;
        JSqlDatabase jSqlDatabase = jSqlDatabaseHashMap.get(databaseName);
        while (index < sqls.size()){
            String keyword = sqls.get(index)[0];
            switch (keyword){
                case "select":{
                    String[] singleSql = sqls.get(index);
                    index++;
                    if (singleSql.length == 4){
                        Object[] objects = jSqlDatabase.selectByName(singleSql[3], singleSql[1]);
                        for(Object object: objects){
                            System.out.println(object);
                        }
                    }else if(singleSql.length > 4){
                        System.out.println("功能没写全");
                    }
                    break;
                }
                default:{
                    throw new Exception("sql错误");
                }
            }
        }
    }

    static public String printBlank(int maxLength, int minLength){
        String line = "| ";
        for (int i =0; i < maxLength-minLength; i++){
            line += " ";
        }
        return line;
    }

    static public void printSingleField(Object[] objects){
        int length = ((Field)objects[0]).getName().length();
        for(Object object: objects){
            if(length < object.toString().length()){
                length = object.toString().length();
            }
        }
        String line = "";
        for (int i =0;i < length+2; i++){
            line += "-";
        }
        System.out.println("+"+line+"+");
        System.out.println(printBlank(length,((Field)objects[0]).getName().toString().length())+((Field)objects[0]).getName()+" |");
        System.out.println("+"+line+"+");
        for(Object object: objects){
            System.out.println(printBlank(length,object.toString().length())+object+" |");
        }
        System.out.println("+"+line+"+");

    }

    //?
    private void readObject(ObjectInputStream inputStream) throws IOException,ClassNotFoundException{
        inputStream.defaultReadObject();
        System.out.println("ok");
    }

    public JSqlDatabase getDatabase(String databaseName) throws Exception {
        try{
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("Database\\"+databaseName+".ser"));
            ObjectInputStream ois = new ObjectInputStream(bis);
            JSqlDatabase jSqlDatabase = (JSqlDatabase) ois.readObject();
            ois.close();
            bis.close();
            return jSqlDatabase;
        }catch (Exception e){
            throw new Exception("数据库不存在");
        }
    }

    public void saveDatabase(JSqlDatabase jSqlDatabase) throws Exception {
        try {
            String databaseName=jSqlDatabase.getName();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("Database\\"+databaseName+".ser"));
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(jSqlDatabase);
            oos.close();
            bos.close();
        }catch (Exception e){
            throw new Exception("数据库不存在");
        }
    }


}
