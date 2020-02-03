import ast.Symbols;
import bplustree.BPlusTree;
import jsql.JSql;
import table.Field;
import table.Table;

import java.util.HashMap;

public class main {
    public static void main(String[] args) {
        HashMap<String,Integer> priorityTable = new HashMap<>();
        priorityTable.put("+",60);
        priorityTable.put("-",60);
        priorityTable.put("*",70);
        priorityTable.put("/",70);
        priorityTable.put(">=",80);
        priorityTable.put("<=",80);
        priorityTable.put(">",80);
        String op = "+";
        System.out.println(priorityTable.get(op));

    }
}
