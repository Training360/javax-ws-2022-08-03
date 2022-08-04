package calculator;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Bean
    public Endpoint endpoint(Bus bus, CalculatorEndpoint calculatorEndpoint) {
        var endpoint = new EndpointImpl(bus, calculatorEndpoint);
        endpoint.publish("/calculator");
        return endpoint;
    }
}
