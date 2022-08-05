package employees;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeCommand {

    @Schema(description = "the name of the created employee", example = "Jane Doe")
    @NotBlank(message = "the name can not be empty")
    @ValidName(partNumber = 3)
    private String name;
}
