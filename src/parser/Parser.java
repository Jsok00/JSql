package parser;

import ast.Expr;
import ast.Factor;
import ast.Id;
import ast.Symbols;
import ast.stmt.AssignStmt;
import ast.stmt.Stmt;
import lexer.Lexer;
import lexer.Token;

import java.util.Vector;

public class Parser {
    Vector<Token> tokens;
    Integer index;
    Token lookAhead;
    public Vector<Stmt> Parse(String SourceCode) throws Exception {
        Vector<Token> tokens = new Lexer(SourceCode).getTokens();
        this.tokens=tokens;
        this.tokens.add(new Token("eof",null));
        this.index=0;
        this.lookAhead=this.tokens.get(this.index++);

        return this.parseStmts();
        //return null;

    }
    //Tokens后移
    public void read(){
        if(!this.lookAhead.type.equals("eof")){
            this.lookAhead = this.tokens.get(this.index++);
        }
    }

    //确认值
    public String match(String value) throws Exception {
        if(this.lookAhead.value.equals(value)){
            this.read();
            //System.out.println(value+"  走了match现在是  "+this.lookAhead.value);
            return value;
        }
        throw new Exception();
    }
    public String matchType(String type) throws Exception {
        if(this.lookAhead.type.equals(type)){
            this.read();
            return type;
        }
        throw new Exception();
    }

    public Vector<Stmt> parseStmts() throws Exception {
        Vector<Stmt> stmts = new Vector<>();
        while (!this.lookAhead.type.equals("eof")){
            stmts.add(this.parseStmt());
        }
        return stmts;
    }
    public Stmt parseStmt() throws Exception {
//        if (this.lookAhead.type.equals("id")||this.lookAhead.type.equals("number")){
//            return this.parseExpr();
//        }

        switch (this.lookAhead.value){
            case "auto":{
                return this.parseAssignStmt();
            }
            default:
                throw new Exception();
        }
    }

    public Stmt parseAssignStmt() throws Exception {
        this.match("auto");
        if (!this.lookAhead.type.equals("id")){
            throw new Exception("syntax error");
        }
        Id id = new Id(this.lookAhead.value);
        this.match(this.lookAhead.value);
        this.match("=");
        Expr right=this.parseExpr();
        new AssignStmt(id,right).gen(new Symbols());
        return new AssignStmt(id,right);
    }
    /*
     *Expr -> Term Expr_
     *
     *
     *
     * Term -> -Expr || (Expr) || Term * Factor || Term / Factor ||Factor
     * Factor -> number || string || id
     */


    public Expr parseExpr() throws Exception {
//        Expr term=this.parseTerm();
//        Expr rexpr=this.parseExpr_();
//        if (rexpr==null){
//            return term;
//        }else{
//            return new Expr(term,rexpr);//?
//        }
        ExprParser exprParser = new ExprParser();

        return exprParser.parseExpr(this);
    }
    /*
     *Expr_ -> +Expr || -Expr || e
     */
    public Expr parseExpr_() throws Exception {
        if (lookAhead.value != null)
        if(this.lookAhead.value.equals("+") || this.lookAhead.value.equals("-")){
            String value = this.match(this.lookAhead.value);
            return new Expr(new Factor(value),this.parseExpr());//+ 和+后面东西
        }
        return null;
    }
    /*
     *Term -> -Expr || (Expr)|| Term * Factor || Term / Factor || Factor || e
     *
     * Term -> Factor Term_ || -Expr Term_ || (Expr) Term_
     * Term_ -> * TErm || / Term  || e
     */

    public Expr parseTerm() throws Exception {
        Expr left = null;
        if(this.lookAhead.value.equals("-")){
            this.match("-");
            //left = new Expr("-",this.parseExpr());
        }else if(this.lookAhead.value.equals("(")){
            this.match("(");
            Expr expr = this.parseExpr();
            this.match(")");
            left=expr;
        }else{
            left = this.parseFactor();
        }
        Expr rterm = this.parseTerm_();
        if(rterm == null){
            return left;
        }
        return new Expr(rterm.op,left,rterm.right);

    }
    public Expr parseTerm_() throws Exception {
        if (lookAhead.value != null)
        if(this.lookAhead.value.equals("*") || this.lookAhead.value.equals("/")){

            String value = this.match(this.lookAhead.value);

            return new Expr(new Factor(value),this.parseTerm());
        }
        return null;
    }
    public Expr parseFactor() throws Exception {
        if(this.lookAhead.type.equals("number")){
            String value = this.match(this.lookAhead.value);
            return new Expr(new Id(value));
        }else if(this.lookAhead.type.equals("String")) {
            throw new Exception();
        }else {
            throw new Exception();
        }
    }

    public static void main(String[] args) throws Exception {
        Parser parser2 = new Parser();
        parser2.Parse("auto x =  ( 1 + 2 ) * 3 ;");
        //System.out.println(parser2.("auto x = ( 1 + 2 ) * 3;"));
    }
}