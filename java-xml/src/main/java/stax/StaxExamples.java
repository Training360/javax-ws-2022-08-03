package stax;

import dom.Book;
import dom.DomExamples;
import sax.SaxExamples;

import javax.xml.stream.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StaxExamples {

    public void printTitles(Reader contentReader) {
        try {
            var factory = XMLInputFactory.newFactory();
            var reader = factory.createXMLStreamReader(contentReader);

            while (reader.hasNext()) {
//                if (reader.hasName()) {
//                    System.out.println(reader.getName() + " " + reader.getEventType());
//                }

                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT &&
                        reader.getName().getLocalPart().equals("title")) {
                    System.out.println(reader.getElementText());
                }
//                if (reader.getEventType() == XMLStreamConstants.END_ELEMENT &&
//                        reader.getName().getLocalPart().equals("title")) {
//                    System.out.println(reader.getElementText());
//                }

                reader.next();

            }

        } catch (XMLStreamException xse) {
            throw new IllegalStateException("Can not read xml", xse);
        }
    }

    public void write(List<Book> books, Writer contentWriter) {
        var factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = null;
        try {

            writer = factory.createXMLStreamWriter(contentWriter);

            writer.writeStartDocument();
            writer.writeStartElement("catalog");

            for (var book: books) {
                writer.writeStartElement("book");
                writer.writeStartElement("title");
                writer.writeCharacters(book.getTitle());
                writer.writeEndElement(); // title
                writer.writeEndElement(); // book
            }

            writer.writeEndElement(); // catalog
            writer.writeEndDocument();
        }
        catch (XMLStreamException e) {
            throw new IllegalStateException("Can not read xml", e);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (XMLStreamException xse) {
                    throw new IllegalStateException("Can not read xml", xse);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new StaxExamples().printTitles(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml")));

        var booksToWrite = List.of(
                new Book("title1", "isbn1", Book.Available.NOT_AVAILABLE),
                new Book("title2", "isbn2", Book.Available.IS_AVAILABLE)
        );

        var writer = new StringWriter();
        new StaxExamples().write(booksToWrite, writer);

        System.out.println(writer);
    }
}
