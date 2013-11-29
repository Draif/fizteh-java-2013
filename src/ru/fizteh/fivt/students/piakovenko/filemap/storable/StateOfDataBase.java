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

    public boolean check() {
        return valid;
    }

    public void change(boolean valid) {
        this.valid = valid;
    }
}
