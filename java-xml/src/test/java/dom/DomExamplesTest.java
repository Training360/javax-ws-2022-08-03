package dom;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static dom.Book.Available.IS_AVAILABLE;
import static dom.Book.Available.NOT_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class DomExamplesTest {

    @Test
    void getBooks() throws IOException {
        // Given: DomExamples példányom és egy fájlom
        var domExamples = new DomExamples();
        var file = Files.newBufferedReader(Path.of("src/test/resources/test-catalog.xml"));

        // When: meghívom a metódust
        var books = domExamples.getBooks(file);

        // Then: books lista jó értékeket tartalmaz-e
        // 1. pontos egyezőség
        var expected = List.of(
                new Book("Java and XML", "059610149X", IS_AVAILABLE),
                new Book("Pro XML Development with Java Technology", "1590597060", NOT_AVAILABLE)
                );
        assertEquals(expected, books);

        // 2. szúrópróba
        assertEquals(2, books.size());
        assertEquals("Pro XML Development with Java Technology", books.get(1).getTitle());

        // 3. stream
        assertEquals(List.of("Java and XML", "Pro XML Development with Java Technology"),
                books.stream().map(Book::getTitle).toList()
                );

        // 4. AssertJ
        assertThat(books)
                .isEqualTo(List.of(
                        new Book("Java and XML", "059610149X", IS_AVAILABLE),
                        new Book("Pro XML Development with Java Technology", "1590597060", NOT_AVAILABLE)
                ));

        assertThat(books)
                .hasSize(2)
                .contains(
                        new Book("Java and XML", "059610149X", IS_AVAILABLE)
                );

        assertThat(books)
                .extracting(Book::getTitle, Book::getIsbn10)
                .contains(tuple("Java and XML", "059610149X"));
    }

    @Test
    void write() throws IOException {
        // Given
        var domExamples = new DomExamples();
        var books = List.of(
                new Book("title1", "isbn1", Book.Available.NOT_AVAILABLE),
                new Book("title2", "isbn2", Book.Available.IS_AVAILABLE)
        );
        // When
        var writer = new StringWriter();
        domExamples.write(books, writer);

        // Then
        // var expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><catalog><book isbn10=\"isbn1\"><title>title1</title></book><book isbn10=\"isbn2\"><title>title2</title></book></catalog>";
        var expected = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <catalog>
                  <book isbn10="isbn1">
                    <title>title1</title>
                  </book>
                  <book isbn10="isbn2">
                    <title>title2</title>
                  </book>
                </catalog>
                """.replace("\n", "\r\n");
        assertEquals(expected, writer.toString());

        XmlAssert
                .assertThat(writer.toString())
                .valueByXPath("/catalog/book[2]/title")
                .isEqualTo("title2");

//        var expected2 = Files.readString(Path.of("src/test/resources/expected-catalog.xml")).replace("\n", "\r\n");;

//        System.out.println(writer.toString());

        XmlAssert.assertThat(writer.toString())
                .and(Input.fromFile("src/test/resources/expected-catalog.xml"))
                .ignoreElementContentWhitespace()
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes))
//                .ignoreChildNodesOrder()
                .areSimilar();
    }
}