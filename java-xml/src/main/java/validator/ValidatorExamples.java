package validator;

import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ValidatorExamples {

    private void validate(InputStream newInputStream) {
        try {
            // Séma fájl (xsd) betöltése
            var factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(Path.of("src/main/resources/catalog.xsd").toFile());

            // Betöltöm az XML (dom, sax, stax)
            // Jelen esetben DOM
            var domFactory = DocumentBuilderFactory.newInstance();
            var domBuilder = domFactory.newDocumentBuilder();
            var document = domBuilder.parse(newInputStream);

            // Validator
            var validator = schema.newValidator();
            validator.validate(new DOMSource(document));
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not validate", e);
        }
    }

    public static void main(String[] args) throws IOException {
        new ValidatorExamples().validate(Files.newInputStream(Path.of("src/main/resources/catalog.xml")));
    }


}
