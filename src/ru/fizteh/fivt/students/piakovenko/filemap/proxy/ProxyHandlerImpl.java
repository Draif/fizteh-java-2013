package ru.fizteh.fivt.students.piakovenko.filemap.proxy;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 27.11.13
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
public class ProxyHandlerImpl implements InvocationHandler {
    private Writer writer = null;
    private Object object = null;


    public ProxyHandlerImpl(Object object, Writer writer) {
        this.object = object;
        this.writer = writer;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        if (method.getDeclaringClass().equals(Object.class)) {
            return method.invoke(object, arguments);
        }

        Object result = null;
        String log = null;
        XmlLogging logWriter = new XmlLogging();
        try {
            result = method.invoke(object, arguments);
            log = logWriter.writeMethod(method, object.getClass(), result, null, arguments);
            return result;
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                e = ((InvocationTargetException) e).getTargetException();
            }
            logWriter.writeMethod(method, object.getClass(), result, e, arguments);
            throw e;
        } finally {
            if (log != null) {
                try {
                    writer.append(log);
                    writer.append(System.lineSeparator());
                } catch (IOException e) {
                    //can't do anything
                }
            }
        }
    }
}
