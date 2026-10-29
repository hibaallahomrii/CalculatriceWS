package service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Une opération de l'historique. Type complexe visible dans le XSD généré
 * (liste d'opérandes => maxOccurs="unbounded").
 */
@XmlRootElement(name = "Operation", namespace = Constantes.NAMESPACE)
@XmlType(name = "Operation", namespace = Constantes.NAMESPACE,
        propOrder = {"type", "operandes", "resultat", "date"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Operation {

    private String type;

    @XmlElement(name = "operande")
    private List<Double> operandes = new ArrayList<>();

    private double resultat;
    private String date;

    /** Constructeur sans argument requis par JAXB. */
    public Operation() {
    }

    public Operation(String type, List<Double> operandes, double resultat, String date) {
        this.type = type;
        this.operandes = operandes;
        this.resultat = resultat;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getOperandes() {
        return operandes;
    }

    public void setOperandes(List<Double> operandes) {
        this.operandes = operandes;
    }

    public double getResultat() {
        return resultat;
    }

    public void setResultat(double resultat) {
        this.resultat = resultat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + " | " + type + operandes + " = " + resultat;
    }
}
