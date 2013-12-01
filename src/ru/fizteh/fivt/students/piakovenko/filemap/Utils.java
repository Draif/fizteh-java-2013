package ru.fizteh.fivt.students.piakovenko.filemap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 01.12.13
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */
public class Utils {


    public static Class fromNameToType(String name) throws IOException {
        if (name == "int") {
            return int.class;
        } else if (name == "long") {
            return long.class;
        } else if (name == "byte") {
            return byte.class;
        } else if (name == "float") {
            return float.class;
        } else if (name == "double") {
            return double.class;
        } else if (name == "boolean") {
            return boolean.class;
        } else if (name == "String") {
            return String.class;
        } else {
            throw new IOException("Wrong type " + name);
        }
    }

    public static List<Class<?>> arrayList(String argument) throws IOException {
        String temp = argument.substring(1, argument.length()- 1);
        String[] typesArray = temp.trim().split("\\s*;\\s*");
        List<Class<?>> resultList = new ArrayList<Class<?>>();
        for (int i = 0; i < typesArray.length; ++i) {
            resultList.add(fromNameToType(typesArray[i]));
        }
        return resultList;
    }
}
