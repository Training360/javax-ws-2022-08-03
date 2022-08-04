package calculator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Component
@AllArgsConstructor
@WebService(targetNamespace = "https://training360.com/ns/calculator")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class CalculatorEndpoint {

    private CalculatorService calculatorService;

    @WebResult(name = "CalculatorResponse")
    public CalculatorResponse add(@WebParam(name = "CalculatorRequest") CalculatorRequest request) {
        var i = request.getI();
        var j = request.getJ();

        var result = calculatorService.add(i, j);
        return new CalculatorResponse(result);
    }
}
