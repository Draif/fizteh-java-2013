package ru.fizteh.fivt.students.piakovenko.test;

import junit.framework.Test;
import ru.fizteh.fivt.students.piakovenko.filemap.proxy.XmlLogging;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 27.11.13
 * Time: 1:00
 * To change this template use File | Settings | File Templates.
 */
public class TestForXml {

    @org.junit.Test
    public void TestOne() {
        File output = new File(new File("."), "output.txt");
        output = output.getAbsoluteFile();
        //System.out.println(output.getAbsolutePath());
        try {
            if (!output.exists()) {
                output.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new PrintWriter(output));
           // XmlLogging temp = new XmlLogging(String.class, writer);
            //temp.close();
        } catch (FileNotFoundException e) {
            System.exit(1);
        } /*catch (NoSuchMethodException e) {
            System.exit(1);
        } */catch (IOException e) {
            System.exit(2);
        }
    }
}
