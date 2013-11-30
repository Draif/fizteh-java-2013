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
    //private Object object = null;
    private XMLStreamWriter xmlWriter = null;
    StringWriter stringWriter = new StringWriter();
    private IdentityHashMap<Object, Boolean> cycleLink = new IdentityHashMap<Object, Boolean>();


    public XmlLogging(Writer tempWriter) throws IOException {
        this.writer = tempWriter;
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try {
            xmlWriter = factory.createXMLStreamWriter(stringWriter);
        } catch (XMLStreamException e) {
            throw new IOException("error with print Mehod" + e.getMessage());
        }
    }


    public void printMainInformation(Object object, Method method) throws IOException, XMLStreamException {
            xmlWriter.writeStartElement("invoke");
            xmlWriter.writeAttribute("timestamp", Long.toString(System.currentTimeMillis()));
            xmlWriter.writeAttribute("class", object.getClass().getName());
            xmlWriter.writeAttribute("name", method.getName());
    }

    public void printArguments(Object[] arguments) throws XMLStreamException {
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
                        xmlWriter.writeCharacters(temp.toString());
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
                xmlWriter.writeCharacters(tempObject.toString());
            }
            xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();
    }

    public void printException(Throwable e) throws IOException, XMLStreamException {
            xmlWriter.writeStartElement("thrown");
            xmlWriter.writeCharacters(e.toString());
            xmlWriter.writeEndElement();
    }

    public void printReturnValue(Object returnValue) throws IOException, XMLStreamException {
        try {
            xmlWriter.writeStartElement("return");
            if (returnValue == null) {
                xmlWriter.writeCharacters("null");
            } else {
                xmlWriter.writeCharacters(returnValue.toString());
            }
            xmlWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw e;
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
