package employees;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private int partNumber;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var parts = value.split(" ");
        return parts.length == partNumber;
    }

    @Override
    public void initialize(ValidName constraintAnnotation) {
        partNumber = constraintAnnotation.partNumber();
    }
}
