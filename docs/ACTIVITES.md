# Correspondance énoncé du TP ↔ projet

| Activité | Demandé | Où dans le projet |
|---|---|---|
| **1.1** | Dépendances Maven | `pom.xml` (jaxws-rt 4.0.2 apporte `jakarta.xml.ws-api` et `jakarta.jws-api`) |
| **1.2** | Packages `service` et `serveur` | `src/main/java/service/`, `src/main/java/serveur/` (+ `client/` en bonus) |
| **2** | Classe `Calculatrice` implémentant `ICalculatrice` | `service/ICalculatrice.java`, `service/Calculatrice.java` |
| **3.1** | `@WebService` sur l'interface et la classe | `ICalculatrice` (name, targetNamespace) et `Calculatrice` (serviceName, portName, endpointInterface) |
| **3.2** | `@WebMethod` | Toutes les méthodes de `ICalculatrice` (`operationName`) |
| **3.3** | `@WebParam` | Tous les paramètres (`a`, `b`, `base`, `valeurs`, ...) |
| **4.1** | Classe `ServeurJWS` dans `serveur` | `serveur/ServeurJWS.java` |
| **4.2** | `Endpoint.publish` + URL | `http://localhost:8080/calculatrice` |
| **4.3** | Consulter WSDL et XSD | `?wsdl` et `?xsd=1` (voir `WSDL_XSD.md`) |
| **5** | Test SoapUI | Requêtes prêtes dans `docs/exemples/*.xml` |

## Consigne « une méthode d'abord, puis les autres »

Le projet contient directement toutes les méthodes. Pour respecter la progression demandée,
suivez la séquence de commits décrite dans `GIT.md` (premier commit : `somme` seule, testée dans
SoapUI ; deuxième commit : les autres méthodes).

## Ce qui dépasse l'énoncé (bonus)

1. **SOAP Fault typé** : `CalculException` + `CalculFault` (`@WebFault`) → `<wsdl:fault>` dans le WSDL
2. **Opérations avancées** : puissance, racine carrée, modulo, pourcentage, moyenne, factorielle
3. **Types variés dans le XSD** : `double`, `int`, `long`, tableau (`maxOccurs="unbounded"`), types complexes
4. **Historique** : type complexe `Operation` (`getHistorique`, `viderHistorique`)
5. **SOAPHandler** : `LoggingHandler` affiche chaque requête/réponse XML dans la console
6. **Client Java** : `client/ClientCalculatrice.java`
7. **Tests** : JUnit 5 (unitaires + intégration SOAP réelle)
8. **Documentation** : README, ce guide, plan du rapport, exemples de requêtes
