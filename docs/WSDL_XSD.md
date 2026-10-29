# Correspondance classe métier ↔ contrat WSDL/XSD (Activité 4.3)

Ouvrir dans le navigateur (serveur lancé) :

- WSDL : <http://localhost:8080/calculatrice?wsdl>
- XSD : <http://localhost:8080/calculatrice?xsd=1>

| Java | WSDL / XSD |
|---|---|
| `@WebService(targetNamespace=...)` | attribut `targetNamespace="http://service.fss.tn/"` |
| Interface `ICalculatrice` | `<wsdl:portType name="ICalculatrice">` |
| Méthode `somme(a, b)` | `<wsdl:operation name="somme">` + messages `somme` / `sommeResponse` |
| `@WebParam(name="a")` | `<xs:element name="a" type="xs:double"/>` dans `<xs:complexType name="somme">` |
| `@WebResult(name="resultat")` | `<xs:element name="resultat" type="xs:double"/>` dans `sommeResponse` |
| `double[] valeurs` | `<xs:element name="valeurs" type="xs:double" maxOccurs="unbounded"/>` |
| `int n` / retour `long` | `xs:int` / `xs:long` |
| Classe `Operation` | `<xs:complexType name="Operation">` (type, operande*, resultat, date) |
| `List<Operation>` | `<xs:element name="operation" type="tns:Operation" maxOccurs="unbounded"/>` |
| `CalculException` + `CalculFault` | `<wsdl:fault name="CalculFault">` + `<xs:complexType name="CalculFault">` |
| `serviceName="CalculatriceWS"` | `<wsdl:service name="CalculatriceWS">` |
| `portName="CalculatricePort"` | `<wsdl:port name="CalculatricePort" binding="...">` |
| URL de `Endpoint.publish` | `<soap:address location="http://localhost:8080/calculatrice"/>` |

Principe : pour chaque opération, le XSD définit **un élément de requête** (nom de l'opération)
et **un élément de réponse** (nom + `Response`). C'est le style *document/literal wrapped*.
