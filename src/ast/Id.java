package ast;

public class Id extends Factor {
    public Id(String value) throws Exception {
        super(value);
        if(value==null){
            throw new Exception("value is not defind");
        }
    }
}
