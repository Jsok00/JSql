package ast.stmt;

import ast.Expr;
import ast.Factor;
import ast.Symbols;

public class AssignStmt extends Stmt {
    public Factor left;
    public Expr right;


    public AssignStmt(Factor left,Expr right){
        this.left=left;
        this.right =right;
    }
    public void gen(Symbols symbols){
        if (this.right.op!=null){
            this.right.gen(symbols);
        }
        System.out.println(left.lValue() + "=" + this.right.factor.value);
    }
}
