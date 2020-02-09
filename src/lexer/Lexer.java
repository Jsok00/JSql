package lexer;


import java.util.LinkedList;
import java.util.Vector;

public class Lexer {
    String[] KeyWords = new String[11];
    LinkedList<Token> tokens= new LinkedList<>();

    public Lexer(String SourceCode) throws Exception {
        KeyWords[0] = "if";
        KeyWords[1] = "else";
        KeyWords[2] = "insert";
        KeyWords[3] = "select";
        KeyWords[4] = "delete";
        KeyWords[5] = "select";
        KeyWords[6] = "from";
        KeyWords[7] = "where";
        KeyWords[8] = "insert";
        KeyWords[9] = "into";
        KeyWords[10] = "values";

        int index=0;
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
                case ',':{
                    Token token = relSplit(SourceCode,index);
                    index++;
                    tokens.add(token);
                    break;
                }
                case '.':{
                    Token token = relDot(SourceCode,index);
                    index++;
                    tokens.add(token);
                    break;
                }
                case ';':{
                    Token token = relOver(SourceCode,index);
                    index++;
                    tokens.add(token);
                    break;
                }
                case ' ':
                case '\n':{
                    index++;
                    break;
                }
                default:{
                    if (String.valueOf(c).getBytes("UTF-8").length > 1){
                        Token token = literal(SourceCode,index);
                        index += token.value.length();
                        tokens.add(token);
                        break;
                    }else {
                        throw new Exception();
                    }
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
    //生成关键字或变量
    public Token literal(String SourceCode,Integer index) throws Exception {
        int state=0;
        String str="";
        while (true){
            switch (state){
                case 0:{
                    char c=getNextChar(SourceCode,index++);
                    if((c>='A'&&c<='Z')||(c>='a'&&c<='z')||(String.valueOf(c).getBytes("UTF-8").length > 1)){
                        state=1;
                        str+=c;
                    }else {
                        throw new Exception();
                    }
                    break;
                }
                case 1:{
                    char c=getNextChar(SourceCode,index++);
                    if((c>='A'&&c<='Z')||(c>='a'&&c<='z')||(c>='0'&&c<='9')||(String.valueOf(c).getBytes("UTF-8").length > 1)){
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
        int state=0;
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
        int state=0;
        while (true){
            switch (state){
                case 0:
                    char c = getNextChar(SourceCode,index++);
                    switch (c){
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                        case '(':
                        case ')':
                            return new Token("op",c+"");
                    }
            }
        }

    }

    public Token relNumber(String SourceCode,Integer index) throws Exception {
        int state=0;
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

    public Token relSplit(String SourceCode,int index) throws Exception {
        char c = getNextChar(SourceCode,index++);
        String str = "";
        if(c == ','){
            str += c;
            return new Token("split",str);
        }else{
            throw new Exception();
        }
    }

    public Token relDot(String SourceCode,int index) throws Exception {
        char c = getNextChar(SourceCode,index++);
        String str = "";
        if(c == '.'){
            str += c;
            return new Token("dot",str);
        }else{
            throw new Exception();
        }
    }

    public Token relOver(String SourceCode,int index) throws Exception {
        char c = getNextChar(SourceCode,index++);
        String str = "";
        if(c == ';'){
            str += c;
            return new Token("over",str);
        }else{
            throw new Exception();
        }
    }


    public LinkedList<Token> getTokens() {
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
