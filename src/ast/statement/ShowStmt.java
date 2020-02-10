package ast.statement;

import ast.Expr;

import java.util.ArrayList;

public class ShowStmt extends Statement {
    private Expr value;
    public ShowStmt(Expr value){
        this.value = value;
    }

    @Override
    public ArrayList<String[]> gen() throws Exception {
        String str ="show "+value.factor.value;
        ArrayList<String[]> gen = new ArrayList<>();
        gen.add(str.split(" "));
        return gen;
    }
}
