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

    // creates a writer for writing to the file at the given destination
    public Writer(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer, throws a FileNotFoundException if the file cannot be opened to write to
    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the inventory to the file as a json string
    public void write(Inventory inv) {
        JSONObject json = inv.toJson();
        printWriter.print(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        printWriter.close();
    }
}
