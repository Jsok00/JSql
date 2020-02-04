package test;
import bplustree.BPlusTree;
import jsql.JSql;
import table.Field;
import table.RowField;
import table.RowTable;
import table.Table;

import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) throws Exception {
        Field name = new Field("name","String","11",null);
        Field ID=new Field("ID","Integer","11",null);
        Field age=new Field("age","Integer","11",null);
        ArrayList<Field> fields = new ArrayList<>();
        fields.add(name);
        fields.add(ID);
        fields.add(age);
        Table table=new Table("person",fields,"ID",1); //表结构



        Field name1=new RowField("name","String","11","蒋卓伦");
        Field ID1=new RowField("ID","Integer","11",2);
        Field age1=new Field("age","Integer","11",21);
        ArrayList<Field> fields1 = new ArrayList<>();
        fields1.add(name1);
        fields1.add(ID1);
        fields1.add(age1);

        RowTable table1=new RowTable("person",fields1,"ID",1);

        Field name2=new RowField("name","String","11","周杰伦");
        Field ID2=new RowField("ID","Integer","11",9);
        Field age2=new Field("age","Integer","11",45);
        ArrayList<Field> fields2 = new ArrayList<>();
        fields2.add(name2);
        fields2.add(ID2);
        fields2.add(age2);

        RowTable table2=new RowTable("person",fields2,"ID",1);




        JSql jSql=new JSql(table,"demo");
        //jSql.getbPlusTree().insert(table1,2);
        jSql.insert(table1);
        jSql.insert(table2);
        System.out.println(jSql.selectAll()[0]);
        System.out.println(jSql.selectByName("ID")[1]);
        //System.out.println(table1.getPrimaryKeyNum());


    }
}
