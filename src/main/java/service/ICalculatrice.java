package service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import java.util.List;

/**
 * Contrat du service web SOAP CalculatriceWS (devient le portType du WSDL).
 */
@WebService(name = "ICalculatrice", targetNamespace = Constantes.NAMESPACE)
public interface ICalculatrice {

    // ---------- Opérations de base (énoncé du TP) ----------

    @WebMethod(operationName = "somme")
    @WebResult(name = "resultat")
    double somme(@WebParam(name = "a") double a,
                 @WebParam(name = "b") double b);

    @WebMethod(operationName = "soustraction")
    @WebResult(name = "resultat")
    double soustraction(@WebParam(name = "a") double a,
                        @WebParam(name = "b") double b);

    @WebMethod(operationName = "multiplication")
    @WebResult(name = "resultat")
    double multiplication(@WebParam(name = "a") double a,
                          @WebParam(name = "b") double b);

    @WebMethod(operationName = "division")
    @WebResult(name = "resultat")
    double division(@WebParam(name = "a") double a,
                    @WebParam(name = "b") double b) throws CalculException;

    // ---------- Opérations avancées (bonus) ----------

    @WebMethod(operationName = "puissance")
    @WebResult(name = "resultat")
    double puissance(@WebParam(name = "base") double base,
                     @WebParam(name = "exposant") double exposant) throws CalculException;

    @WebMethod(operationName = "racineCarree")
    @WebResult(name = "resultat")
    double racineCarree(@WebParam(name = "a") double a) throws CalculException;

    @WebMethod(operationName = "modulo")
    @WebResult(name = "resultat")
    double modulo(@WebParam(name = "a") double a,
                  @WebParam(name = "b") double b) throws CalculException;

    @WebMethod(operationName = "pourcentage")
    @WebResult(name = "resultat")
    double pourcentage(@WebParam(name = "valeur") double valeur,
                       @WebParam(name = "taux") double taux);

    /** Tableau en paramètre => élément XSD avec maxOccurs="unbounded". */
    @WebMethod(operationName = "moyenne")
    @WebResult(name = "resultat")
    double moyenne(@WebParam(name = "valeurs") double[] valeurs) throws CalculException;

    /** Paramètre int / retour long : autres types simples XSD. */
    @WebMethod(operationName = "factorielle")
    @WebResult(name = "resultat")
    long factorielle(@WebParam(name = "n") int n) throws CalculException;

    // ---------- Historique (types complexes) ----------

    @WebMethod(operationName = "getHistorique")
    @WebResult(name = "operation")
    List<Operation> getHistorique();

    @WebMethod(operationName = "viderHistorique")
    @WebResult(name = "nombreSupprimees")
    int viderHistorique();

    @WebMethod(operationName = "getInfo")
    @WebResult(name = "info")
    String getInfo();
}
