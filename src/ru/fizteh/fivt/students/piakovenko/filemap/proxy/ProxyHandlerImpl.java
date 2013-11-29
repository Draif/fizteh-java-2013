package ru.fizteh.fivt.students.piakovenko.filemap.proxy;

import com.sun.deploy.net.proxy.ProxyHandler;

import java.io.Writer;
import java.lang.reflect.InvocationHandler;
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
    public Object invoke(Object proxy, Method method, Object[] argumnets) {
        return null;
    }
}
