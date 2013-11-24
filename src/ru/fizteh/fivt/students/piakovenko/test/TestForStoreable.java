package ru.fizteh.fivt.students.piakovenko.test;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 24.11.13
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.storage.structured.TableProvider;
import ru.fizteh.fivt.storage.structured.TableProviderFactory;
import ru.fizteh.fivt.students.piakovenko.filemap.storable.DataBasesFactory;
import ru.fizteh.fivt.students.piakovenko.filemap.storable.JSON.JSONSerializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestForStoreable {
    private static final int KEYS_COUNT = 20;
    private static final String TEST_TABLE_NAME = "test";
    private List<Class<?>> listWithClassesTypes = null;

    TableProviderFactory factory = null;
    TableProvider provider = null;
    Table table;

    @Before
    public void initialize() throws Exception {
        factory = new DataBasesFactory();
        provider = factory.create("C:\\temp\\JUnit");
        listWithClassesTypes = new ArrayList<Class<?>>();
        listWithClassesTypes.add(String.class);
        table = provider.createTable(TEST_TABLE_NAME, listWithClassesTypes);
        initializeTable();
    }

    private void initializeTable() {
        for (int index = 0; index < KEYS_COUNT; ++index) {
            String key = String.format("key%d", index);
            Storeable value = null;
            try {
                value = JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index));
            } catch (ParseException e) {
                System.exit(1);
            }
            table.put(key, value);
        }
    }

    @After
    public void afterTest() throws Exception {
        provider.removeTable(TEST_TABLE_NAME);
    }

    @Test
    public void tableExistingData() {
        for (int index = 0; index < KEYS_COUNT; ++index) {
            Storeable expectedValue = null;
            try {
               expectedValue = JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index));
            } catch (ParseException e) {
                System.exit(1);
            }
            String key = String.format("key%d", index);
            Assert.assertEquals(expectedValue, table.get(key));
        }
    }

    @Test
    public void tableNonExistingData() {
        Random random = new Random();
        for (int index = 0; index < KEYS_COUNT; ++index) {
            String key = String.format("k%d", random.nextInt(100));
            Assert.assertNull(table.get(key));
        }
    }

    @Test
    public void tableNewData() {
        for (int index = 0; index < KEYS_COUNT; ++index) {
            String newKey = String.format("new_key%d", index);
            Storeable newValue = null;
            try {
                newValue = JSONSerializer.deserialize(table, String.format("[\"new_value%d\"]", index));
            } catch (ParseException e) {
                System.exit(1);
            }
            Assert.assertNull(table.put(newKey, newValue));
        }
    }

    @Test
    public void tableReplaceData() {
        for (int index = 0; index < KEYS_COUNT; ++index) {
            String key = String.format("key%d", index);
            Storeable oldValue = null;
            Storeable newValue = null;
            try {
                newValue = JSONSerializer.deserialize(table, String.format("[\"new_value%d\"]", index));
                oldValue = JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index));
            } catch (ParseException e) {
                System.exit(1);
            }
            Assert.assertEquals(oldValue, table.put(key, newValue));
        }
    }

    @Test
    public void tableCommit() {
        try {
            int committed = table.commit();
            Assert.assertEquals(KEYS_COUNT, committed);
            for (int index = 0; index < 2 * KEYS_COUNT; ++index) {
                String key = String.format("key%d", index);
                Storeable value = JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index));
                table.put(key, value);
            }
            Assert.assertEquals(KEYS_COUNT, table.commit());
            for (int index = 0; index < 2 * KEYS_COUNT; ++index) {
                String key = String.format("key%d", index);
                Assert.assertNotNull(table.get(key));
            }
        } catch (IOException e) {
            System.exit(1);
        } catch (ParseException e) {
            System.exit(1);
        }
    }

    @Test
    public void tableRollback() {
        Assert.assertEquals(KEYS_COUNT, table.rollback());
        for (int index = 0; index < 2 * KEYS_COUNT; ++index) {
            String key = String.format("key%d", index);
            Storeable value = null;
            try {
                value = JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index));
            } catch (ParseException e) {
                System.exit(1);
            }
            table.put(key, value);
        }
        Assert.assertEquals(2 * KEYS_COUNT, table.rollback());
        for (int index = 0; index < 2 * KEYS_COUNT; ++index) {
            String key = String.format("key%d", index);
            Assert.assertNull(table.get(key));
        }
    }

    @Test
    public void tableSize() {
        Assert.assertEquals(KEYS_COUNT, table.size());
    }

    @Test
    public void tableGetName() {
        Assert.assertEquals(TEST_TABLE_NAME, table.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tableExceptions() {
        table.get(null);
        try {
            table.put(null, JSONSerializer.deserialize(table, "[\"value\"]"));
        } catch (ParseException e) {
            System.exit(1);
        }
        table.put("key", null);
        table.remove(null);
    }

    @Test
    public void rollbackCommit() {
        try {
            for (int index = 0; index < KEYS_COUNT; ++index) {
                String key = String.format("key%d", index);
                table.put(key, JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index)));
            }
            table.commit();
            for (int index = 0; index < KEYS_COUNT; ++index) {
                String key = String.format("key%d", index);
                table.remove(key);
            }
            for (int index = 0; index < KEYS_COUNT; ++index) {
                String key = String.format("key%d", index);
                table.put(key, JSONSerializer.deserialize(table, String.format("[\"value%d\"]", index)));
            }
            Assert.assertEquals(0, table.rollback());

            table.remove("non-exists");
            table.remove("non-exists1");
            table.remove("key1");
            table.put("key1", JSONSerializer.deserialize(table, String.format("[\"value1\"]")));
            Assert.assertEquals(0, table.rollback());

            table.put("key1", JSONSerializer.deserialize(table, String.format("[\"value1\"]")));
            table.commit();
            table.remove("key1");
            table.put("key1", JSONSerializer.deserialize(table, String.format("[\"value1\"]")));
            Assert.assertEquals(0, table.rollback());
        } catch (IOException e) {
            System.exit(1);
        } catch (ParseException e) {
            System.exit(1);
        }
    }
}

