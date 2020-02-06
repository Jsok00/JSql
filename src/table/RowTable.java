package table;

import bplustree.BPlusTree;

import java.util.ArrayList;
import java.util.HashMap;

public class RowTable extends Table{



    public RowTable(String name) {
        super(name);
        this.fields=new HashMap<>();

    }

}
