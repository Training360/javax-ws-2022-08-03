package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeesControllerIT {

    @Autowired
    WebTestClient webClient;

    @Test
    void testCreate() {
        long id = webClient
                .post()
                .uri("/api/employees")
                .bodyValue(new CreateEmployeeCommand("John Doe"))
                .exchange()
                .expectHeader().exists("Location")
                .expectStatus()
                .isCreated()
                .expectBody(EmployeeDto.class).value(e -> assertEquals("John Doe", e.getName()))
                .returnResult()
                .getResponseBody()
                .getId();
//                .expectBody().jsonPath("name").isEqualTo("John Doe")
                //.expectBody(String.class).consumeWith(s -> System.out.println(s))
        ;

        webClient
                .get()
                .uri("/api/employees/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class).value(e ->  assertEquals("John Doe", e.getName()));

        webClient
                .get()
                .uri("/api/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class).value(employees ->
                        assertThat(employees)
                                .extracting(EmployeeDto::getName)
                                .contains("John Doe")
                        );
    }
}
