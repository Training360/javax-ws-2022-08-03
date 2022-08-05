package employees;

import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private long id;

    public EmployeeNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }
}
