package jsql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.List;

public class JSql {
    private List<JSqlDatabase> jSqlDatabaseList;

    public JSqlDatabase getDatabase(String databaseName) throws Exception {
        FileInputStream fis = new FileInputStream("Database\\"+databaseName+".ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        JSqlDatabase jSqlDatabase = (JSqlDatabase) ois.readObject();
        ois.close();
        fis.close();
        return jSqlDatabase;
    }
}
