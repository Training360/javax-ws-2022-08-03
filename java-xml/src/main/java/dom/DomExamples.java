package dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DomExamples {

    public List<String> getTitles() {
        var titleTexts = new ArrayList<String>();
        try {
            // DOM fa beolvasása az XML alapján
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(DomExamples.class.getResourceAsStream("/catalog.xml"));

            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            // getResourceAsStream működik, ha az alkalmazásod JAR/WAR fájlán belül van az XML
//            var document = builder.parse(DomExamples.class.getResourceAsStream("/catalog.xml"));
//            var document = builder.parse(Path.of("src/main/resources/catalog.xml").toFile());
//            var document = builder.parse(Files.newInputStream(Path.of("src/main/resources/catalog.xml")));
            var document = builder.parse(new InputSource(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml"))));

            // Book Elementek lekérése a DOM fából
            var books = document.getElementsByTagName("book");

            // Iterálás az Elementeken
            for (int i = 0; i < books.getLength(); i++) {
                var book = (Element) books.item(i);
                var title = book.getElementsByTagName("title").item(0);
                var text = title.getTextContent();
                titleTexts.add(text);
            }

            // Mindegyikből lekérjük a Title Element szöveges tartalmát
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException("Can not read file.", e);
        }
        return titleTexts;
    }

    public List<Book> getBooks(Reader reader) {
        // Book objektumok listáját adja vissza
        var bookObjects = new ArrayList<Book>();
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var document = builder.parse(new InputSource(reader));

            // Book Elementek lekérése a DOM fából
            var books = document.getElementsByTagName("book");

            // Iterálás az Elementeken
            for (int i = 0; i < books.getLength(); i++) {
                var book = (Element) books.item(i);
                var title = book.getElementsByTagName("title").item(0);
                var text = title.getTextContent();
                var isbn10 = book.getAttribute("isbn10");
                var available = book.getElementsByTagName("available").getLength() > 0 ? Book.Available.IS_AVAILABLE : Book.Available.NOT_AVAILABLE;
                var bookObject = new Book(text, isbn10, available);
                bookObjects.add(bookObject);
            }

            // Mindegyikből lekérjük a Title Element szöveges tartalmát
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException("Can not read file.", e);
        }
        return bookObjects;
    }

    public static void main(String[] args) throws IOException {
        var titles = new DomExamples().getTitles();
        System.out.println(titles);

        var books = new DomExamples().getBooks(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml")));
        System.out.println(books);
    }
}
