package employees;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = NameValidator.class)
public @interface ValidName {

    String message() default "Not valid name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int partNumber() default 2;
}
