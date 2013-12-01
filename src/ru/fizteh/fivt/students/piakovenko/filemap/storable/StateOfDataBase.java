package ru.fizteh.fivt.students.piakovenko.filemap.storable;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 27.11.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class StateOfDataBase {
    private boolean valid;

    public StateOfDataBase() {
        valid = true;
    }

    public synchronized void check() throws IllegalStateException {
        if (!valid) {
            throw new IllegalStateException("this method was closed!");
        }
    }

    public boolean isValid() {
        return valid;
    }

    public synchronized void change(boolean valid) {
        this.valid = valid;
    }
}
