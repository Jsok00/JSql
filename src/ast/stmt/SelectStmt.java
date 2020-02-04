package ast.stmt;

import ast.Expr;

public class SelectStmt extends Stmt {
    public Expr selectList;
    public Expr tableName;
    public Expr limitState;

    public SelectStmt(Expr selectList,Expr tableName,Expr limitState){
        this.selectList=selectList;
        this.tableName=tableName;
        this.limitState=limitState;
    }
    public void gen(){
        System.out.println(tableName.exprs.size());
        if (tableName.exprs.size()==1) {
            for (Expr value : selectList.exprs) {
                System.out.println("select "+value.factor.value+" from "+tableName.factor.value);
            }
        }



    }
}
