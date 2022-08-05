package employees;

import lombok.Data;

@Data
public class QueryCriteria {

    private String prefix;

    private String postfix;

    private String order;
}
