package ru.fizteh.fivt.students.piakovenko.filemap.storable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 02.12.13
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public class DiskUtils {


    public static void fixedClasses(File f, List<Class<?>> classList) {
        if (!f.exists()) {
            RandomAccessFile randomAccessFile = null;
            try {
                randomAccessFile = new RandomAccessFile(f, "rw");
                randomAccessFile.write(ru.fizteh.fivt.students.piakovenko.filemap.Utils.classesString(classList)
                        .getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                //keep silent
            }
            finally {
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        new RuntimeException("Can't close file! " + f.getName());
                    }
                }
            }
        }
    }
}
