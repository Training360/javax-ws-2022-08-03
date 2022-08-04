package xpath;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class XpathExamples {

    public List<String> getTitles(Reader reader) {
        try {
            var factory = XPathFactory.newInstance();
            var xpath = factory.newXPath();
            var expression = xpath.compile("//title");

            var nodes = (NodeList) expression.evaluate(new InputSource(reader), XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println(nodes.item(i).getTextContent());
            }

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Can not run xpath", e);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        new XpathExamples().getTitles(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml")));
    }
}
