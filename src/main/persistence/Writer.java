package persistence;

import model.Inventory;
import org.json.JSONObject;
import java.io.*;

// class implementation is from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
// represents a writer that stores inventory data in a JSON file
public class Writer {
    public static final int TAB = 4;
    private PrintWriter printWriter;
    private String destination;

    public Writer(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(new File(destination));
    }

    public void write(Inventory inv) {
        JSONObject json = inv.toJson();
        printWriter.print(json.toString(TAB));
    }

    public void close() {
        printWriter.close();
    }
}
