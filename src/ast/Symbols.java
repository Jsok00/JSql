package ast;

public class Symbols {
    public Integer id;

    public Symbols(){
        this.id=1;
    }

    public String getSymbol(){
        return "t"+this.id++;
    }
    public String getNowSymbol(){
        return "t"+this.id;
    }
}
