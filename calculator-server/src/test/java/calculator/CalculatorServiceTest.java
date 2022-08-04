package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    @Test
    void add() {
        assertEquals(5, new CalculatorService().add(2, 3));
    }
}