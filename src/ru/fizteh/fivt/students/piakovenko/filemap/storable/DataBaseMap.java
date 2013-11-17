package ru.fizteh.fivt.students.piakovenko.filemap.storable;

import ru.fizteh.fivt.storage.structured.Storeable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 12.10.13
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseMap {
    private Map<String, Storeable> map = new HashMap<String, Storeable>(15);
    private Map<String, Storeable> changedMap = new HashMap<String, Storeable>(15);
    private Map<String, Storeable> overwriteMap = new HashMap<String, Storeable>(15);

    public Storeable put (String key, Storeable value) {
        Storeable oldValue = null;
        if (!map.containsKey(key)) {
            map.put(key, value);
            System.out.println("new");
        } else {
            System.out.println("overwrite");
            oldValue = map.get(key);
            System.out.println(oldValue);
            map.remove(key);
            map.put(key, value);
        }
        return oldValue;
    }

    public Storeable get(String key) {
        if (!map.containsKey(key)) {
            System.out.println("not found");
        } else {
            System.out.println("found");
            System.out.println(map.get(key));
            return map.get(key);
        }
        return null;
    }

    public Storeable remove (String key) {
        if (!map.containsKey(key)) {
            System.out.println("not found");
        } else {
            Storeable returnValue = map.get(key);
            map.remove(key);
            System.out.println("removed");
            return returnValue;
        }
        return null;
    }

    public void primaryPut (String key, Storeable value) {
            map.put(key, value);
    }

    public Map<String, Storeable> getMap () {
        return map;
    }

    public Map<String, Storeable> getChangedMap() {
        return changedMap;
    }

    public Map<String, Storeable> getOverwriteMap() {
        return overwriteMap;
    }

}
