package ru.fizteh.fivt.students.piakovenko.filemap.proxy;

import ru.fizteh.fivt.proxy.LoggingProxyFactory;

import java.io.Writer;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 27.11.13
 * Time: 0:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class Logging implements LoggingProxyFactory {
    @Override
    public Object wrap(Writer writer, Object implementation, Class<?> interfaceClass) throws IllegalArgumentException {
        if (writer == null || implementation == null || interfaceClass == null) {
            throw new IllegalArgumentException("One of the arguments equals null");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException(interfaceClass.getName() + "is not interface");
        }
        if (!interfaceClass.isInstance(implementation)) {
            throw new IllegalArgumentException(implementation.getClass() + "is not instance of " + interfaceClass.getName());
        }
        return Proxy.newProxyInstance(implementation.getClass().getClassLoader(),new Class[]{interfaceClass}, new ProxyHandlerImpl(implementation, writer));
    }

}
