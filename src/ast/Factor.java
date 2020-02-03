package ast;
/*
 *最小因子单位，树节点
 */
public class Factor {
    public String value;

    public Factor(String value){
        this.value = value;
    }


    public String lValue(){
        return this.value;
    }
    public String rValue(){
        return this.value;
    }

}
