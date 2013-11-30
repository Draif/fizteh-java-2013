package ru.fizteh.fivt.students.piakovenko.filemap.proxy;

import ru.fizteh.fivt.common.Exceptions;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 27.11.13
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
public class XmlLogging {
    private XMLStreamWriter xmlWriter = null;
    StringWriter stringWriter = new StringWriter();
    private IdentityHashMap<Object, Boolean> cycleLink = new IdentityHashMap<Object, Boolean>();

    public XmlLogging() throws IOException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try {
            xmlWriter = factory.createXMLStreamWriter(stringWriter);
        } catch (XMLStreamException e) {
            throw new IOException("error with print Mehod" + e.getMessage());
        }
    }
    public void printObject(Object object) throws Exception {
        if (object == null) {
            xmlWriter.writeEmptyElement("null");
        } else if (object instanceof Iterable) {
            if (cycleLink.containsKey(object)) {
                xmlWriter.writeCharacters("cyclic");
                return;
            } else {
                cycleLink.put(object, true);
            }
            if (!((Iterable) object).iterator().hasNext()) {
                xmlWriter.writeEmptyElement("list");
            } else {
                xmlWriter.writeStartElement("list");
                Iterator ptr = ((Iterable) object).iterator();
                while (ptr.hasNext()) {
                    xmlWriter.writeStartElement("value");
                    printObject(ptr.next());
                    xmlWriter.writeEndElement();
                }
                xmlWriter.writeEndElement();
            }
        } else {
            xmlWriter.writeCharacters(object.toString());
        }
    }

    public void  printArguments(Object[] arguments) {
        try {
            if (arguments.length > 0) {
                xmlWriter.writeStartElement("arguments");
                for (final Object temp: arguments) {
                    xmlWriter.writeStartElement("argument");
                    printObject(temp);
                    xmlWriter.writeEndElement();
                }
                xmlWriter.writeEndElement();
            } else {
                xmlWriter.writeEmptyElement("arguments");
            }
            xmlWriter.writeCharacters("");
        } catch (Exception e) {
            throw Exceptions.runtime(e,"failed to print log");
        }
    }

    public String writeMethod(Method method, Class clazz, Object returnValue, Throwable exception,
                              Object[] arguments) {
        try {
            xmlWriter.writeStartElement("invoke");
            xmlWriter.writeAttribute("timestamp", Long.toString(System.currentTimeMillis()));
            xmlWriter.writeAttribute("class", clazz.getName());
            xmlWriter.writeAttribute("name", method.getName());
            xmlWriter.writeCharacters("");
            if (arguments != null) {
                printArguments(arguments);
            }
            if (exception == null) {
                if (!method.getReturnType().toString().equals("void")) {
                    xmlWriter.writeStartElement("return");
                    printObject(returnValue);
                    xmlWriter.writeEndElement();
                }
            } else {
                xmlWriter.writeStartElement("thrown");
                printObject(exception);
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
            xmlWriter.flush();
            return stringWriter.toString();
        } catch (Exception e) {
            throw Exceptions.runtime(e, "Failed to print log");
        }

    }


}
