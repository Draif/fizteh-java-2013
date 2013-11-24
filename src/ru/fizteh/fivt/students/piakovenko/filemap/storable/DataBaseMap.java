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

    public Storeable put(String key, Storeable value) {
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

    public Storeable remove(String key) {
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

    public void primaryPut(String key, Storeable value) {
            map.put(key, value);
    }

    public Map<String, Storeable> getMap() {
        return map;
    }

    public int commit(Map<String, Storeable> newMap) {
        int count = 0;
        for (final String key: newMap.keySet()) {
            Storeable tempValue = newMap.get(key);
            if (wasChanged(tempValue, map.get(key))) {
                if (tempValue == null) {
                    map.remove(key);
                } else {
                    map.put(key, tempValue);
                }
                ++count;
            }
        }
        return count;
    }

    public int changesCount(Map<String, Storeable> newMap) {
        int count = 0;
        for (final String key: newMap.keySet()) {
            Storeable tempValue = newMap.get(key);
            if (wasChanged(tempValue, map.get(key))) {
                ++count;
            }
        }
        return count;
    }

    public int currentSize(Map<String, Storeable> _newMap) {
        int count = map.size();
        for (final String key: _newMap.keySet()) {
            Storeable newValue = _newMap.get(key);
            Storeable oldValue = map.get(key);
            if (newValue == null && oldValue != null) {
                --count;
            } else if (newValue != null && oldValue == null) {
                ++count;
            }
        }
        return count;
    }

    private boolean wasChanged(Storeable value1, Storeable value2) {
        boolean flag = false;
        if (value1 == null && value2 == null){
            return false;
        } else if (value1 == null) {
            return true;
        } else if (value2 == null) {
            return true;
        }
        flag = !value1.equals(value2);
        return flag;
    }
}
