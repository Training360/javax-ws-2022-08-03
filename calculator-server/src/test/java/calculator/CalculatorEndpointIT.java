package calculator;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorEndpointIT {

    @LocalServerPort
    int port;

    @Test
    void testAdd() {
        // Given
        var proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(CalculatorEndpoint.class);
        proxyFactory.setAddress("http://localhost:" + port + "/services/calculator");
        var endpoint = proxyFactory.create(CalculatorEndpoint.class);
        var request = new CalculatorRequest(10, 20);

        // When
        var response = endpoint.add(request);

        // Then
        assertEquals(30, response.getResult());
    }
}
