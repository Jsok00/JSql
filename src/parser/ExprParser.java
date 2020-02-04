package parser;

import ast.Expr;
import ast.Factor;
import lexer.Token;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class ExprParser {
    public HashMap<String,Integer> priorityTable;
    public Parser parser;

    public ExprParser(){
        this.priorityTable = new HashMap<>();
        priorityTable.put("+",60);
        priorityTable.put("-",60);
        priorityTable.put("*",70);
        priorityTable.put("/",70);
        priorityTable.put(">=",80);
        priorityTable.put("<=",80);
        priorityTable.put(">",80);
        priorityTable.put("<",80);
        priorityTable.put("&&",90);
        priorityTable.put("||",90);
        priorityTable.put("==",100);
        priorityTable.put("!=",100);
        priorityTable.put("(",1000);
        priorityTable.put(")",1000);

    }
    /*
     *帮助stack一直pop到满意为止
     */
    public void popUntill(Stack<Token> stack1, Vector<Expr> stack2 ){
        Token token;
        while (!stack1.isEmpty()){
            token = stack1.pop();
            if(prediction(token)){
                stack1.push(token);
                break;
            }
            if(token.type.equals("op")) {
                stack2.add(new Expr(token));
            }else {
                stack2.add(new Expr(new Factor(token.value)));
            }
        }
    }

    public boolean prediction(Token token){
        return token.value.equals("(");
    }

    public Expr parseExpr(Parser parser) throws Exception {
        this.parser = parser;
        //if(parser.lookAhead.value.equals(")")){
        //    return null;
        //}
        /*
         * PreOrder 前序
         * inOrder 中序
         * PostOrder 后续
         *
         */
        Vector<Expr> postOrderOutput = inOrederToPostOrder();
        return constructAST(postOrderOutput);
    }

    public void print(Vector<Expr> postOrderOutput){
        for (Expr c : postOrderOutput) {
            if (c.factor != null) {
                System.out.println(c.factor.value);
            } else {
                System.out.println(c.op.value);
            }
        }
    }

    public Expr constructAST(Vector<Expr> postOrderOutput){
         Expr c;
        Stack<Expr> exprStack = new Stack<>();
        //print(postOrderOutput);
        for (Expr value : postOrderOutput) {
            c = value;
            if (c.op != null) {
                Expr r = exprStack.pop();
                Expr l = exprStack.pop();
                Expr expr = new Expr(new Factor(c.op.value), l, r);
                exprStack.push(expr);
            } else {
                exprStack.push(new Expr(c.factor));

            }

        }
        return exprStack.get(0);
    }

    public Vector<Expr> inOrederToPostOrder() throws Exception {
        Stack<Token> opStack = new Stack<>();
        Vector<Expr> outPut = new Vector<>();
        while ((!this.parser.lookAhead.type.equals("eof"))&&(!this.parser.lookAhead.value.equals("}"))){
            if (this.parser.lookAhead.value.equals("(")){
                opStack.push(this.parser.lookAhead);
                this.parser.match("(");
            }else if(this.parser.lookAhead.value.equals(")")) {
                popUntill(opStack, outPut);
                Token op = opStack.pop();
                //没有左括号则停止
                if (!op.value.equals("(")) { // 疑惑
                    break;
                }
                this.parser.match(")");
                if (!this.parser.lookAhead.type.equals("op")) {
                    break;
                }

            }else if (this.parser.lookAhead.type.equals("op")) {
                Token op = this.parser.lookAhead;
                if (priorityTable.get(op.value) == null) {
                    throw new Exception();
                }
                this.parser.match(op.value);
                Token lastOp;
                if (!opStack.isEmpty()) {
                    lastOp = opStack.lastElement();
                }else{
                    lastOp = null;
                }
                if (lastOp != null) {
                    if (priorityTable.get(op.value) <= priorityTable.get(lastOp.value)) {
                        popUntill(opStack, outPut); // 可能有问题
                    }
                }
                opStack.push(op);
            }else {
                Factor factor = this.parser.parseFactor().factor;
                outPut.add(new Expr(factor));
                if((!this.parser.lookAhead.type.equals("op")) || this.parser.lookAhead.value.equals("=")){
                    break;
                }
            }
        }

        if (!opStack.isEmpty()){
            while (!opStack.isEmpty()){
                outPut.add(new Expr(opStack.pop()));
            }
        }

        return outPut;
    }
}
