package service;

import jakarta.xml.ws.WebFault;

/**
 * Exception métier transformée en SOAP Fault typé.
 * Le WSDL contient alors un élément &lt;wsdl:fault&gt; pour les opérations qui la déclarent.
 */
@WebFault(name = "CalculFault", targetNamespace = Constantes.NAMESPACE)
public class CalculException extends Exception {

    public static final String ERR_DIV_ZERO = "ERR_DIV_ZERO";
    public static final String ERR_RACINE_NEGATIVE = "ERR_RACINE_NEGATIVE";
    public static final String ERR_MODULO_ZERO = "ERR_MODULO_ZERO";
    public static final String ERR_RESULTAT_INVALIDE = "ERR_RESULTAT_INVALIDE";
    public static final String ERR_LISTE_VIDE = "ERR_LISTE_VIDE";
    public static final String ERR_FACTORIELLE = "ERR_FACTORIELLE";

    private final CalculFault faultInfo;

    public CalculException(String message, CalculFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public CalculException(String message, CalculFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /** Détail transporté dans le SOAP Fault. */
    public CalculFault getFaultInfo() {
        return faultInfo;
    }

    /** Fabrique pratique : code d'erreur + message. */
    public static CalculException of(String code, String message) {
        return new CalculException(message, new CalculFault(code, message));
    }
}
