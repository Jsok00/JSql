package ast.statement;

import ast.Expr;

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
    public LinkedList<String[]> gen(){

        if (tableName.exprs.size()==1) {
            LinkedList<String[]> gen = new LinkedList<>();
            for (Expr value : selectList.exprs) {
                String str= "select "+value.factor.value+" from "+tableName.factor.value;
                gen.add(str.split(" "));
            }
            return gen;
        }
    return null;
    }
}
