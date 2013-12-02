package ru.fizteh.fivt.students.piakovenko.filemap.storable;

import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.students.piakovenko.filemap.storable.JSON.JSONSerializer;
import ru.fizteh.fivt.students.piakovenko.shell.Remove;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    public static void saveDataBase(DataBase dataBase, File storage, Map<String, Storeable> map) {
        try {
            if (storage.exists()) {
                Remove.removeRecursively(storage);
            }
            if (!storage.mkdirs()) {
                throw new IOException("Unable to create this directory - " + storage.getCanonicalPath());
            }
            for (String key : map.keySet()) {
                Integer numberOfDirectory = RulesAndOthers.ruleNumberDirectory(key);
                Integer numberOfFile = RulesAndOthers.ruleNumberFile(key);
                File directory = new File(storage, numberOfDirectory.toString() + ".dir");
                if (!directory.exists()) {
                    if (!directory.mkdirs()) {
                        throw new IOException("Unable to create this directory - " + directory.getCanonicalPath());
                    }
                }
                File writeFile = new File(directory, numberOfFile.toString() + ".dat");
                if (!writeFile.exists()) {
                    writeFile.createNewFile();
                }
                saveToFile(writeFile, key, JSONSerializer.serialize(dataBase, map.get(key)));
            }
        } catch (IOException e) {
            new RuntimeException("can't save file!");
        }
    }

    private static void saveToFile(File f, String key, String value) throws IOException {
        RandomAccessFile ra = null;
        try {
            ra = new RandomAccessFile(f, "rw");
            ra.seek(ra.length());
            byte [] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte [] valueBytes = value.getBytes(StandardCharsets.UTF_8);
            ra.writeInt(keyBytes.length);
            ra.writeInt(valueBytes.length);
            ra.write(keyBytes);
            ra.write(valueBytes);
        } finally {
            ra.close();
        }
    }

}
