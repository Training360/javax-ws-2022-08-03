package calculator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@Component
@AllArgsConstructor
@WebService
public class CalculatorEndpoint {

    private CalculatorService calculatorService;

    public CalculatorResponse add(CalculatorRequest request) {
        var i = request.getI();
        var j = request.getJ();

        var result = calculatorService.add(i, j);
        return new CalculatorResponse(result);
    }
}
