package ru.fizteh.fivt.students.chernigovsky.filemap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class State {
    private HashMap<String, String> hashMap;
    String tableName;

    public State(File directory, String newTableName) {
        hashMap = new HashMap<String, String>();
        tableName = newTableName;
    }
    public String put(String key, String value) {
        return hashMap.put(key, value);
    }
    public String get(String key) {
        return hashMap.get(key);
    }
    public String remove(String key) {
        return hashMap.remove(key);
    }

    public String getTableName() {
        return tableName;
    }

    public Set<Map.Entry<String, String>> getEntrySet() {
        return hashMap.entrySet();
    }

}
