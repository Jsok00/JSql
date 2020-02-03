package ast.stmt;

import ast.Expr;
import ast.Factor;

public class AssignStmt extends Stmt {
    public Factor left;
    public Expr righr;

    public AssignStmt(Factor left,Expr right){
        this.left=left;
        this.righr =right;
    }
}
