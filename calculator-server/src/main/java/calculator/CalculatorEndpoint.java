package calculator;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
@WebService(targetNamespace = "https://training360.com/ns/calculator")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CalculatorEndpoint {
    @WebResult(name = "CalculatorResponse")
    CalculatorResponse add(@WebParam(name = "CalculatorRequest") CalculatorRequest request);
}
