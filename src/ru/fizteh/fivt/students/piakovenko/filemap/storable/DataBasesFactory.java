package ru.fizteh.fivt.students.piakovenko.filemap.storable;

import ru.fizteh.fivt.storage.structured.TableProvider;
import ru.fizteh.fivt.storage.structured.TableProviderFactory;
import ru.fizteh.fivt.students.piakovenko.shell.Shell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 26.10.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class DataBasesFactory implements TableProviderFactory, AutoCloseable {
    private Shell shell = null;
    private StateOfDataBase stateOfDataBase = new StateOfDataBase();
    private Map<String, DataBasesCommander> commanders = new HashMap<String, DataBasesCommander>();

    public synchronized TableProvider create(String dir) throws IllegalArgumentException, IOException, IllegalStateException {
        stateOfDataBase.check();
        Checker.stringNotEmpty(dir);
        File fileMapStorage;
        fileMapStorage = new File(dir);
        if (fileMapStorage.isFile()) {
            throw new IllegalArgumentException("try create provider on file");
        }
        if (!fileMapStorage.exists()) {
            if (!fileMapStorage.mkdir()) {
                throw new IOException("Can't create the directory " + fileMapStorage.getCanonicalPath());
            }
        }
        shell = new Shell();
        commanders.put(dir, new DataBasesCommander(shell, fileMapStorage, dir));
        return commanders.get(dir);
    }

    public void start(String[] args) {
        shell.start(args);
    }

    @Override
    synchronized public void close() {
        for (final DataBasesCommander temp: commanders.values()) {
            temp.close();
        }
        stateOfDataBase.change(false);
    }
}
