package calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class CalculatorEndpointTest {

    @Mock
    CalculatorService service;

    @InjectMocks
    CalculatorEndpoint endpoint;

    @Test
    void add() {
        when(service.add(anyInt(), anyInt())).thenReturn(30);
        var response = endpoint.add(new CalculatorRequest(10, 20));

        verify(service).add(10, 20);
        assertEquals(30, response.getResult());
    }
}