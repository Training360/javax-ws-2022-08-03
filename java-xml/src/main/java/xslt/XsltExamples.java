package xslt;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class XsltExamples {

    public void transform(Reader reader, Writer writer) {
        try {
            var factory = TransformerFactory.newInstance();
            var transformer = factory.newTransformer(
                    new StreamSource(Files.newBufferedReader(Path.of("src/main/resources/catalog-hu.xslt")))
            );

            transformer.transform(new StreamSource(reader), new StreamResult(writer));
        }catch (IOException | TransformerException e) {
            throw new IllegalStateException("Can not transform", e);
        }
    }

    public static void main(String[] args) throws IOException {
        var xml = Files.newBufferedReader(Path.of("src/main/resources/catalog.xml"));
        var writer = new StringWriter();
        new XsltExamples().transform(xml, writer);
        System.out.println(writer);
    }
}
