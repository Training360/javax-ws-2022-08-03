package calculator;

import lombok.AllArgsConstructor;

@WebServiceEndpoint
@AllArgsConstructor
public class SimpleCalculatorEndpoint implements CalculatorEndpoint {

    private CalculatorService calculatorService;

    @Override
    public CalculatorResponse add(CalculatorRequest request) {
        var i = request.getI();
        var j = request.getJ();

        var result = calculatorService.add(i, j);
        return new CalculatorResponse(result);
    }
}
