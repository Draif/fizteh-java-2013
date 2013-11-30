package ru.fizteh.fivt.students.piakovenko.filemap.proxy;

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
    public Object invoke(Object proxy, Method method, Object[] argumenets) throws Throwable {
        Object result = null;
        if (!method.getDeclaringClass().equals(Object.class)) {
            XmlLogging logWriter = new XmlLogging(writer);
            logWriter.printMainInformation(object, method);
            logWriter.writeArguments(argumenets);
            try {
                result = method.invoke(argumenets);
                if (method.getReturnType() != void.class) {
                    logWriter.printReturnValue(result);
                }
            } catch (InvocationTargetException e) {
                logWriter.printException(e);
                throw e.getTargetException();
            }
            logWriter.close();
        } else {
            try {
                result = method.invoke(argumenets);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
        return result;
    }
}
