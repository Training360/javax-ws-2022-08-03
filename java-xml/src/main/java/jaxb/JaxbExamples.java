package jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JaxbExamples {

    public void write(Catalog catalog, Writer writer) {
        try {
            var context = JAXBContext.newInstance(Catalog.class);
            var marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(catalog, writer);
        } catch (Exception e) {
            throw new IllegalStateException("Can not write xml", e);
        }
    }

    public Catalog read(Reader reader) {
        try {
            var context = JAXBContext.newInstance(Catalog.class);
            var unmarshaller = context.createUnmarshaller();

            return (Catalog) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new IllegalStateException("Can not write xml", e);
        }
    }

    public static void main(String[] args) throws IOException {
        var catalog = new Catalog(
                List.of(new Book("title1", "isbn1"),
                new Book("title2", "isbn2"),
                new Book("title3", "isbn3"))
        );
        var writer = new StringWriter();
        new JaxbExamples().write(catalog, writer);
        System.out.println(writer);

        var readedCatalog = new JaxbExamples().read(Files.newBufferedReader(Path.of("src/main/resources/catalog.xml")));
        System.out.println(readedCatalog);
    }
}
