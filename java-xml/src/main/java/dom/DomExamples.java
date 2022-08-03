package dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomExamples {

    public List<String> getTitles() {
        List<String> titleTexts = new ArrayList<>();
        try {
            // DOM fa beolvasása az XML alapján
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(DomExamples.class.getResourceAsStream("/catalog.xml"));

            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var document = builder.parse(DomExamples.class.getResourceAsStream("/catalog.xml"));

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

    public static void main(String[] args) {
        var titles = new DomExamples().getTitles();
        System.out.println(titles);
    }
}
