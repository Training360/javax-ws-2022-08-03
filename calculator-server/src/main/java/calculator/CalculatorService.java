package calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public int add(int i, int j) {
        return i + j;
    }
}
