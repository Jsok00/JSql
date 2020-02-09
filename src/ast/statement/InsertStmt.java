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
    public ArrayList<String[]> gen() throws Exception {
        ArrayList<String[]> gen = new ArrayList<>();
        if (fieldName.exprs.size() == value.exprs.size()){
            for (int i = 0; i < fieldName.exprs.size(); i++){
                String str = "insert into "+tableName.factor.value+" "+fieldName.exprs.get(i).factor.value+" values "+value.exprs.get(i).factor.value;
                gen.add(str.split(" "));
            }
            return gen;

        }else {
            throw new Exception();
        }
    }

}
