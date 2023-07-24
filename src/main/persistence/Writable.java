package persistence;

import org.json.JSONObject;

//Interface structure taken from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
