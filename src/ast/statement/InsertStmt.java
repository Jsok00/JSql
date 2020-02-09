package ast.statement;

import ast.Expr;

import java.util.ArrayList;

public class InsertStmt extends Statement {
    public Expr tableName;
    public Expr fieldName;
    public Expr value;

    public InsertStmt(Expr tableName, Expr fieldName, Expr value){
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.value = value;
    }
    public ArrayList<String[]> gen(){
        return null;
    }

}
