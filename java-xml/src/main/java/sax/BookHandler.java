package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class BookHandler extends DefaultHandler {

    private Deque<String> tags = new ArrayDeque<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println("Tag: " + qName);
        tags.push(qName);
        System.out.println(tags);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        tags.pop();
        System.out.println(tags);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("title".equals(tags.peek())) {
            System.out.println("characters: " + new String(ch, start, length));
        }
    }
}
