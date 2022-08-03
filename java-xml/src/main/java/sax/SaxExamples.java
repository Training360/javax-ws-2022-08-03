package sax;

import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaxExamples {

    public void printTitles(Reader reader) {
        try {
            var factory = SAXParserFactory.newInstance();
            var parser = factory.newSAXParser();
            parser.parse(new InputSource(reader), new BookHandler());

        } catch (Exception e) {
            throw new IllegalStateException("Can not parse xml file", e);
        }
    }

    public static void main(String[] args) throws IOException {
        new SaxExamples().printTitles(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml")));
    }
}
