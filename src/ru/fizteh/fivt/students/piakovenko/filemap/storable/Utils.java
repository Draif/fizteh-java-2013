package ru.fizteh.fivt.students.piakovenko.filemap.storable;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 08.11.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    public static Class<?> classByString(String nameOfClass) {
        if (nameOfClass.equals("int")) {
            return Integer.class;
        } else if (nameOfClass.equals("long")) {
            return Long.class;
        } else if (nameOfClass.equals("byte")) {
            return Byte.class;
        } else if (nameOfClass.equals("float")) {
            return Float.class;
        } else if (nameOfClass.equals("double")) {
            return Double.class;
        } else if (nameOfClass.equals("boolean")) {
            return Boolean.class;
        } else if (nameOfClass.equals("String")) {
            return String.class;
        } else {
            return null;
        }
    }

    public static String stringByClass (Class<?> tempClass) {
        if (Integer.class.equals(tempClass)) {
            return "int";
        } else if (Long.class.equals(tempClass)) {
            return "long";
        } else if (Byte.class.equals(tempClass)) {
            return "byte";
        } else if (Float.class.equals(tempClass)) {
            return "float";
        } else if (Double.class.equals(tempClass)) {
            return "double";
        } else if (Boolean.class.equals(tempClass)) {
            return "boolean";
        } else if (Boolean.class.equals(tempClass)) {
            return "String";
        } else {
            return null;
        }
    }
}
