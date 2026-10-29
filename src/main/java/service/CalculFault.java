package service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Bean de détail du SOAP Fault (apparaît dans le XSD comme type complexe "CalculFault").
 */
@XmlRootElement(name = "CalculFault", namespace = Constantes.NAMESPACE)
@XmlType(name = "CalculFault", namespace = Constantes.NAMESPACE, propOrder = {"code", "message"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculFault {

    private String code;
    private String message;

    /** Constructeur sans argument requis par JAXB. */
    public CalculFault() {
    }

    public CalculFault(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
