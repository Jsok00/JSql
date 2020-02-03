import bplustree.BPlusTree;
import jsql.JSql;
import table.Field;
import table.Table;

public class main {
    public static void main(String[] args) {
        Integer state =0;
        char c='>';
        switch (c){
            case '>':{
                state = 1;
                System.out.println(state);
                break;
            }
            case '<':{
                state = 2;
                System.out.println(state);
                break;
            }
            case '=':{
                state = 3;
                System.out.println(state);
                break;
            }
            case '!':{
                state = 4;
                System.out.println(state);
                break;
            }

        }
        System.out.println(state);

    }
}
