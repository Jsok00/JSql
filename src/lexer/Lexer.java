package lexer;


import javax.xml.transform.Source;
import java.util.Vector;

public class Lexer {
    String[] KeyWords = new String[5];
    Vector<Token> tokens= new Vector<>();

    public Lexer(String SourceCode) throws Exception {
        KeyWords[0]="if";
        KeyWords[1]="else";
        KeyWords[2]="insert";
        KeyWords[3]="select";
        KeyWords[4]="delete";

        Integer index=0;
        Integer length=SourceCode.length();
        while (SourceCode.charAt(index)!=';'){
            char c = SourceCode.charAt(index);
            switch (c){
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                {
                    Token token = literal(SourceCode,index);
                    index += token.value.length();
                    tokens.add(token);
                    break;
                }
                case '>':
                case '<':
                case '=':
                case '!':{
                    Token token = relop(SourceCode,index);
                    index += token.value.length();
                    tokens.add(token);
                    break;
                }
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                case ')': {
                    Token token = arithmetic(SourceCode,index);
                    index += token.value.length();
                    tokens.add(token);
                    break;
                }
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    Token token = relNumber(SourceCode,index);
                    index += token.value.length();
                    tokens.add(token);
                    break;
                }

                case ' ':
                case '\n':{
                    index++;
                    break;
                }


            }
        }
//        tokens.add(new Token("eof",null));


    }

    public char getNextChar(String SourceCode,Integer index){
        return SourceCode.charAt(index);
    }

    public boolean isInclude(String[] keyWords,String word){
        for (String keyWord: keyWords){
            if (keyWord.equals(word)){
                return true;
            }
        }
        return false;
    }

    public Token literal(String SourceCode,Integer index) throws Exception {
        Integer state=0;
        String str="";
        while (true){
            switch (state){
                case 0:{
                    char c=getNextChar(SourceCode,index++);
                    if((c>='A'&&c<='Z')||(c>='a'&&c<='z')){
                        state=1;
                        str+=c;
                    }else {
                        throw new Exception();
                    }
                    break;
                }
                case 1:{
                    char c=getNextChar(SourceCode,index++);
                    if((c>='A'&&c<='Z')||(c>='a'&&c<='z')||(c>='0'&&c<='9')){
                        state=1;
                        str+=c;
                    }else{
                        if(isInclude(KeyWords,str)){
                            return new Token("Keyword",str);
                        }else{
                            return new Token("id",str);
                        }
                    }
                }
                break;
            }
        }
    }

    public Token relop(String SourceCode,Integer index) throws Exception {
        Integer state=0;
        while(true){
            switch (state){
                case 0:{
                    char c=getNextChar(SourceCode,index++);
                    switch (c){
                        case '>':{
                            state = 1;
                            break;
                        }
                        case '<':{
                            state = 2;
                            break;
                        }
                        case '=':{
                            state = 3;
                            break;
                        }
                        case '!':{
                            state = 4;
                            break;
                        }
                    }
                    break;
                }
                case 1:{
                    char c=getNextChar(SourceCode,index++);
                    if(c=='='){
                        return new Token("op",">=");
                    }else{
                        System.out.println(state);
                        return new Token("op",">");
                    }
                }
                case 2:{
                    char c=getNextChar(SourceCode,index++);
                    if(c=='='){
                        return new Token("op","<=");
                    }else{
                        return new Token("op","<");
                    }
                }
                case 3:{
                    char c=getNextChar(SourceCode,index++);
                    if(c=='='){
                        return new Token("op","==");
                    }else{
                        return new Token("op","=");
                    }
                }
                case 4:{
                    char c=getNextChar(SourceCode,index++);
                    if(c=='='){
                        return new Token("op","!=");
                    }else{
                        throw new Exception();
                    }
                }
            }
        }
    }
    public Token arithmetic(String SourceCode,Integer index){
        Integer state=0;
        while (true){
            switch (state){
                case 0:
                    char c = getNextChar(SourceCode,index++);
                    switch (c){
                        case '+':
                            return new Token("op",c+"");
                        case '-':
                            return new Token("op",c+"");
                        case '*':
                            return new Token("op",c+"");
                        case '/':
                            return new Token("op",c+"");
                        case '(':
                            return new Token("op",c+"");
                        case ')':
                            return new Token("op",c+"");
                    }
            }
        }

    }

    public Token relNumber(String SourceCode,Integer index) throws Exception {
        Integer state=0;
        String str="";
        while(true){
            switch (state){
                case 0:{
                    char c = getNextChar(SourceCode,index++);
                    if(c>='0'&&c<='9'){
                        str += c;
                        state=1;
                    }else {
                        throw new Exception();
                    }
                    break;
                }
                case 1:{
                    char c = getNextChar(SourceCode,index++);
                    if(c>='0'&&c<='9'){
                        str += c;
                    }else {
                        return new Token("number",str);
                    }
                }
            }
        }
    }

    public Vector<Token> getTokens() {
        return tokens;
    }

    public static void main(String[] args) throws Exception {
        Lexer l = new Lexer("auto x =  (1 + 2)  * 3;");
        String[] KeyWords = new String[5];
        KeyWords[0]="if";
        KeyWords[1]="else";
        KeyWords[2]="insert";
        KeyWords[3]="select";

        System.out.println(l.tokens.toString());
    }
}
