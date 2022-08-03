package dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

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

    public void write(List<Book> books, Writer writer) {
        try {
            // Létrehozok egy üres dokumentumot
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var document = builder.newDocument();

            // Létrehozok egy catalog taget
            var catalogElement = document.createElement("catalog");
            document.appendChild(catalogElement);

            // Végigiterálok a könyveken
            for (var book: books) {
                // Létrehozom a book taget
                var bookElement = document.createElement("book");
                catalogElement.appendChild(bookElement);
                // isbn10 számot
                bookElement.setAttribute("isbn10", book.getIsbn10());
                // title taget
                var titleElement = document.createElement("title");
                titleElement.setTextContent(book.getTitle());
                bookElement.appendChild(titleElement);
            }
            // DOM fát átalakítom XML-lé és beleírom a writerbe

            var transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            var source = new DOMSource(document);
            var result = new StreamResult(writer);

            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new IllegalStateException("Can not write xml", e);
        }
    }

    public static void main(String[] args) throws IOException {
        var titles = new DomExamples().getTitles();
        System.out.println(titles);

        var books = new DomExamples().getBooks(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml")));
        System.out.println(books);

        var booksToWrite = List.of(
          new Book("title1", "isbn1", Book.Available.NOT_AVAILABLE),
                new Book("title2", "isbn2", Book.Available.IS_AVAILABLE)
        );

        var writer = new StringWriter();
        new DomExamples().write(booksToWrite, writer);

        System.out.println(writer);
    }
}
