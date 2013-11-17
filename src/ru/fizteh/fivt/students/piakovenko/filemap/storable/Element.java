package ru.fizteh.fivt.students.piakovenko.filemap.storable;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 09.11.13
 * Time: 7:48
 * To change this template use File | Settings | File Templates.
 */

import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;

import java.util.ArrayList;
import java.util.List;

public class Element implements Storeable {
    private List<Class<?>> storageClasses = null;
    private List<Object> storage = null;

    private boolean columnChecker(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= storage.size()) {
            return false;
        }
        return true;
    }

    private boolean classChecker(int columnIndex, Object value) {
        if (storageClasses.get(columnIndex).equals(value.getClass())) {
            return true;
        }
        return false;
    }

    public Element () {
        storageClasses = new ArrayList<Class<?>>();
        storage = new ArrayList<Object>();
    }


    public Element(List<Class<?>> classes) {
        storageClasses = new ArrayList<Class<?>>(classes.size());
        storage = new ArrayList<Object>(classes.size());
        for (int i = 0; i < classes.size(); ++i) {
            storageClasses.add(classes.get(i));
        }
    }

    public void setColumnAt(int columnIndex, Object value) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("setColumnAt - wrong index!");
        }
        if (storageClasses.size() <= columnIndex) {
            storageClasses.set(columnIndex, value.getClass());
        }
        if (!classChecker(columnIndex, value)) {
            throw new ColumnFormatException("setColumnAt - wrong type of class!");
        }
        storage.set(columnIndex, value);
    }

    public Object getColumnAt(int columnIndex) throws IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getColumnAt - wrong index!");
        }
        return storage.get(columnIndex);
    }

    public Integer getIntAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException{
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getIntAt - wrong index!");
        }
        if (!classChecker(columnIndex, Integer.class)) {
            throw new ColumnFormatException("getIntAt - wrong type of class!");
        }
        return (Integer)storage.get(columnIndex);
    }

    public Long getLongAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getLongAt - wrong index!");
        }
        if (!classChecker(columnIndex, Long.class)) {
            throw new ColumnFormatException("getLongAt - wrong type of class!");
        }
        return (Long)storage.get(columnIndex);
    }

    public Byte getByteAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getByteAt - wrong index!");
        }
        if (!classChecker(columnIndex, Byte.class)) {
            throw new ColumnFormatException("getByteAt - wrong type of class!");
        }
        return (Byte)storage.get(columnIndex);
    }

    public Float getFloatAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getFloatAt - wrong index!");
        }
        if (!classChecker(columnIndex, Float.class)) {
            throw new ColumnFormatException("getFloatAt - wrong type of class!");
        }
        return (Float)storage.get(columnIndex);
    }

    public Double getDoubleAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getDoubleAt - wrong index!");
        }
        if (!classChecker(columnIndex, Double.class)) {
            throw new ColumnFormatException("getDoubleAt - wrong type of class!");
        }
        return (Double)storage.get(columnIndex);
    }

    public Boolean getBooleanAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getBooleanAt - wrong index!");
        }
        if (!classChecker(columnIndex, Boolean.class)) {
            throw new ColumnFormatException("getBooleanAt - wrong type of class!");
        }
        return (Boolean)storage.get(columnIndex);
    }

    public String getStringAt(int columnIndex) throws ColumnFormatException, IndexOutOfBoundsException {
        if (!columnChecker(columnIndex)) {
            throw  new IndexOutOfBoundsException("getStringAt - wrong index!");
        }
        if (!classChecker(columnIndex, Double.class)) {
            throw new ColumnFormatException("getStringAt - wrong type of class!");
        }
        return (String)storage.get(columnIndex);
    }


}
