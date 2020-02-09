package ast.statement;

import ast.Expr;

import java.util.ArrayList;
import java.util.LinkedList;

public class SelectStmt extends Statement {
    public Expr selectList;
    public Expr tableName;
    public Expr limitState;

    public SelectStmt(Expr selectList, Expr tableName, Expr limitState){
        this.selectList=selectList;
        this.tableName=tableName;
        this.limitState=limitState;
    }
    public ArrayList<String[]> gen(){

        if (tableName.exprs.size()==1) {
            ArrayList<String[]> gen = new ArrayList<>();
            for (Expr value : selectList.exprs) {
                String str= "select "+value.factor.value+" from "+tableName.factor.value;
                gen.add(str.split(" "));
            }
            return gen;
        }
    return null;
    }
}
