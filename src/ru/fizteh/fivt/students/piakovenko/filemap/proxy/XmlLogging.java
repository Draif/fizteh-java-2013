package ru.fizteh.fivt.students.piakovenko.filemap.proxy;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 27.11.13
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
public class XmlLogging {
    private Writer writer = null;
    private Object object = null;
    private XMLStreamWriter xmlWriter = null;
    StringWriter stringWriter = new StringWriter();
    private IdentityHashMap<Object, Boolean> cycleLink = new IdentityHashMap<Object, Boolean>();


    public XmlLogging(Object tempObject, Writer tempWriter) throws IOException {
        this.writer = tempWriter;
        this.object = tempObject;
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try {
            xmlWriter = factory.createXMLStreamWriter(stringWriter);
        } catch (XMLStreamException e) {
            throw new IOException("error with print Mehod" + e.getMessage());
        }
    }


    private void printMainInformation(Method method) throws IOException, XMLStreamException {
            xmlWriter.writeStartElement("invoke");
            xmlWriter.writeAttribute("timestamp", Long.toString(System.currentTimeMillis()));
            xmlWriter.writeAttribute("class", object.getClass().getName());
            xmlWriter.writeAttribute("name", method.getName());
    }

    private void printArguments(Object[] arguments) throws XMLStreamException {
        if (arguments != null) {
            if (arguments.length == 0) {
                xmlWriter.writeEmptyElement("arguments");
            } else {
                for (final Object temp: arguments) {
                    xmlWriter.writeStartElement("argument");
                    if (temp == null) {
                        xmlWriter.writeEmptyElement("null");
                    } else if (temp instanceof Iterable) {
                        cycleLink.put(temp, true);
                        printList((Iterable) temp);
                    } else {
                        xmlWriter.writeCharacters(object.toString());
                    }
                    xmlWriter.writeEndElement();
                }
            }
        }
    }

    private void printList(Iterable list) throws XMLStreamException {
        xmlWriter.writeStartElement("list");
        for (final Object tempObject: list) {
            xmlWriter.writeStartElement("value");
            if (tempObject == null) {
                xmlWriter.writeEmptyElement("null");
            } else if (tempObject instanceof Iterable && cycleLink.containsKey(tempObject)) {
                xmlWriter.writeCharacters("cyclic");
            } else if (tempObject instanceof Iterable) {
                cycleLink.put(tempObject, true);
                printList((Iterable)tempObject);
            } else {
                xmlWriter.writeCharacters(object.toString());
            }
            xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();
    }

    private void printException(Throwable e) throws IOException, XMLStreamException {
            xmlWriter.writeStartElement("thrown");
            xmlWriter.writeCharacters(e.toString());
            xmlWriter.writeEndElement();
    }

    private Object printReturnValue(Method method, Object[] arguments) throws IOException, XMLStreamException {
        try {
            Object returnValue = method.invoke(arguments);
            if (returnValue instanceof Void) {
                return null;
            } else {
                xmlWriter.writeStartElement("return");
                if (returnValue ==null) {
                    xmlWriter.writeCharacters("null");
                } else {
                    xmlWriter.writeCharacters(returnValue.toString());
                }
                xmlWriter.writeEndElement();
            }
            return returnValue;
        } catch (XMLStreamException e) {
            throw e;
        } catch (Throwable e) {
            printException(e);
        }
        return null;
    }

    public Object printMethod(Method method, Object[] arguments) throws IOException {
        try {
            printMainInformation(method);
            printArguments(arguments);
            Object temp = printReturnValue(method, arguments);
            return temp;
        } catch (XMLStreamException e) {
            throw new IOException("Error in XML!");
        }
    }


    public void close() throws IOException{
        try {
            xmlWriter.writeEndElement();
            writer.write(xmlWriter.toString() + "\n");
            xmlWriter.flush();
        } catch (XMLStreamException e) {
            throw new IOException("error while closing: " + e.getMessage());
        }
    }
}
