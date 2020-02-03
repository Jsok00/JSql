package ast;
/*
 *表达式
 */
public class Expr {
    public Factor op;
    public Expr left;
    public Expr right;
    public Factor factor;
    public Symbols tmpId;
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

    public void gen(Symbols symbols){
        this.tmpId=symbols;
        if(this.left.op!=null){
            this.left.gen(symbols);
        }
        if(this.right.op!=null){
            this.right.gen(symbols);
        }

        String rValue = this.left.factor.rValue();
        this.factor=new Factor(this.tmpId.getNowSymbol());
        System.out.println(this.tmpId.getSymbol()+"="+this.left.factor.lValue()+this.op.value+this.right.factor.value);
    }
}
