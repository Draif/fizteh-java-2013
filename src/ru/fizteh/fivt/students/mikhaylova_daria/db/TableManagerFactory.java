package ru.fizteh.fivt.students.mikhaylova_daria.db;

import ru.fizteh.fivt.storage.structured.*;

import java.io.IOException;
import java.util.HashSet;

public class TableManagerFactory implements TableProviderFactory, AutoCloseable {

    private boolean isClosed = false;

    private HashSet<TableManager> providers = new HashSet<TableManager>();

    public TableManagerFactory() {}

    public TableManager create(String dir) throws IllegalArgumentException, IOException {
        if (isClosed) {
            throw new IllegalStateException("This factory is closed");
        }
        if (dir == null) {
            throw new IllegalArgumentException("wrong type (Argument is null)");
        }
        TableManager newProvider = new TableManager(dir);
        providers.add(newProvider);
        return newProvider;
    }

    public void close() {
        if (isClosed) {
            throw new IllegalArgumentException("This factory is closed");
        }
        isClosed = true;
        for (TableManager manager: providers) {
            try {
                manager.close();
            } catch (IllegalStateException e) {

            }
        }
    }
}
