package employees;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.net.URI;

@ControllerAdvice
public class EmployeesExceptionHandler implements ProblemHandling {

    @ExceptionHandler
    public ResponseEntity<Problem> handleNotFoundException(EmployeeNotFoundException e, NativeWebRequest request) {
        var problem = Problem.builder()
                .withType(URI.create("employees/employee-not-found"))
                .withTitle("Employee not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(e.getMessage())
                .with("employee-id", e.getId())
                .build();
        return create(e, problem, request);
    }


}
