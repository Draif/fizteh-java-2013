package ru.fizteh.fivt.students.piakovenko.filemap.storable;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 19.10.13
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */

public class Start {
    public static void  main(String[] args) {
        String databaseDirectory = System.getProperty("fizteh.db.dir");
        if (databaseDirectory == null) {
            //System.err.println("You haven't set database directory");
            System.exit(1);
        }
        try {
            DataBasesFactory factory = new  DataBasesFactory();
            factory.create(databaseDirectory);
            factory.start(args);
        } catch (IOException e) {
            //System.err.println("some error occurred during loading: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            //System.err.println("error while loading: " + e.getMessage());
            System.exit(1);
        }
    }
}
