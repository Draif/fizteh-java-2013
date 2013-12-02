package ru.fizteh.fivt.students.piakovenko.filemap;

import ru.fizteh.fivt.students.piakovenko.filemap.storable.ColumnTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 01.12.13
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static List<Class<?>> arrayList(String argument) throws IOException {
        String temp = argument.substring(1, argument.length()- 1);
        String[] typesArray = temp.trim().split("\\s*;\\s*");
        List<Class<?>> resultList = new ArrayList<Class<?>>();
        for (int i = 0; i < typesArray.length; ++i) {
            resultList.add(ColumnTypes.fromNameToType(typesArray[i]));
        }
        return resultList;
    }

    public static String classesString(List<Class<?>> classList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classList.size(); ++i) {
            for (Map.Entry<String, Class<?>> entry : ColumnTypes.typesNames.entrySet()) {
                if (entry.getValue().equals(classList.get(i))) {
                    sb.append(entry.getKey());
                    break;
                }
            }
            if (i != classList.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
