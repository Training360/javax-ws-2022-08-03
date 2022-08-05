package employees;

//import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeDto {

    private Long id;

//    @JsonProperty("employee-name")
    private String name;
}
