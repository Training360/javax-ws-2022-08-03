package calculator;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculatorRequest {

    @XmlElement(name = "Param1")
    private int i;

    @XmlElement(name = "Param2")
    private int j;
}
