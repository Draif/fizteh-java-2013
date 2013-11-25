package ru.fizteh.fivt.students.piakovenko.filemap.storable;

import ru.fizteh.fivt.storage.structured.TableProvider;
import ru.fizteh.fivt.storage.structured.TableProviderFactory;
import ru.fizteh.fivt.students.piakovenko.shell.Shell;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 26.10.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class DataBasesFactory implements TableProviderFactory {
    private Shell shell = null;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public TableProvider create(String dir) throws IllegalArgumentException, IOException {
        Checker.stringNotEmpty(dir);
        Checker.correctTableName(dir);
        File fileMapStorage = null;
        try {
            lock.writeLock().lock();
            fileMapStorage = new File(dir);

            if (fileMapStorage.isFile()) {
                throw new IllegalArgumentException("try create provider on file");
            }
            if (!fileMapStorage.exists()) {
                fileMapStorage.mkdir();
            }
            shell = new Shell();
        } finally {
            lock.writeLock().unlock();
        }
        return new DataBasesCommander(shell, fileMapStorage);
    }

    public void start(String[] args) {
        shell.start(args);
    }
}
