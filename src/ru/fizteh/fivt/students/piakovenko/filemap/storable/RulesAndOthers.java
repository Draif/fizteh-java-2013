package ru.fizteh.fivt.students.piakovenko.filemap.storable;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 02.12.13
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class RulesAndOthers {
    public static int ruleNumberDirectory(String key) {
        int b = Math.abs(key.getBytes()[0]);
        return b % 16;
    }

    public static int ruleNumberFile(String key) {
        int b = Math.abs(key.getBytes()[0]);
        return b / 16 % 16;
    }
}
