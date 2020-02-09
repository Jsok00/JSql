package test;

import ast.statement.Statement;
import parser.Parser;

import java.util.ArrayList;
import java.util.LinkedList;

public class Test3 {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        LinkedList<Statement> stmts=parser.Parse("insert into a (b,c) values(d,e) ;");
        ArrayList<String[]> str= stmts.get(0).gen();
        System.out.println(str.get(0)[0]);
        System.out.println(str.get(0)[1]);
        System.out.println(str.get(0)[2]);
        System.out.println(str.get(0)[3]);
        System.out.println(str.get(0)[4]);
        System.out.println(str.get(0)[5]);
    }
}
