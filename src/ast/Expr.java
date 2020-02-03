package ast;
/*
 *表达式
 */
public class Expr {
    public Factor op;
    public Expr left;
    public Expr right;
    public Factor factor;
    String value;
    public Expr(Factor op, Expr left, Expr right){
        this.op=op;
        this.left=left;
        this.right=right;
    }
    public Expr(Factor factor){
        this.factor=factor;
    }
    public Expr(Factor op, Expr right){
        this.op=op;
        this.right=right;
    }
    public Expr(Expr left,Expr right){
        this.left=left;
        this.op=right.op;
        this.right=right.right;
    }
}
