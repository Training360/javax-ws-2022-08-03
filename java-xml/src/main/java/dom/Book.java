package dom;

import lombok.Value;

@Value
public class Book {

    public enum Available {IS_AVAILABLE, NOT_AVAILABLE}

    private String title;

    private String isbn10;

    // Clean Code: boolean helyett enum
    private Available available;

}
