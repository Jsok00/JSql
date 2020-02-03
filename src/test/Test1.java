package test;
import bplustree.BPlusTree;
import jsql.JSql;
import table.Field;
import table.RowField;
import table.Table;

public class Test1 {
    public static void main(String[] args) {
        Field name=new Field("name","String","11");
        Field ID=new Field("ID","Integer","11");
        Field[] fields=new Field[2];
        fields[0]=name;
        fields[1]=ID;
        Table table=new Table("person",fields,"ID",1);



        Field name1=new RowField("name","String","11","蒋卓伦");
        Field ID1=new RowField("ID","Integer","11",2);
        Field[] fields1=new RowField[2];
        fields1[0]= name1;
        fields1[1]= ID1;
        Table table1=new Table("person",fields1,"ID",2);



        JSql jSql=new JSql(table,"demo");
        //jSql.getbPlusTree().insert(table1,2);
        jSql.insert(table1);
        System.out.println(jSql.getbPlusTree().find(2).getFields()[0].getValue()==null);
        //System.out.println(table1.getPrimaryKeyNum());


    }
}
