# Activité 5 : Test avec SoapUI (pas à pas)

## 0. Installer SoapUI (si absent)

1. Télécharger **SoapUI Open Source** (gratuit) : <https://www.soapui.org/downloads/soapui/>
2. Choisir l'installeur de votre système (Windows 64-bit, macOS, Linux), l'exécuter, accepter les options par défaut.
3. Lancer SoapUI.

## 1. Préparer le service

Lancer `serveur.ServeurJWS` dans IntelliJ et laisser tourner. Vérifier dans le navigateur que
`http://localhost:8080/calculatrice?wsdl` affiche du XML.

## 2. Configuration de SoapUI : nouveau projet

*File → New SOAP Project*, puis :

- **Project Name** : `CalculatriceWS-Test`
- **Initial WSDL** : `http://localhost:8080/calculatrice?wsdl`
- Laisser cochée l'option *Create sample requests for all operations*
- Cliquer **OK**

## 3. Importation du WSDL

SoapUI télécharge le WSDL et construit l'arbre à gauche :
`CalculatriceWS-Test → CalculatricePortBinding → somme, soustraction, ...`

## 4. Test de la première opération (somme)

1. Dérouler `somme` puis double-cliquer sur **Request 1**.
2. Remplacer les `?` par les deux valeurs choisies :

```xml
<ser:somme>
   <a>10</a>
   <b>5</b>
</ser:somme>
```

## 5. Exécution et vérification

Cliquer sur le triangle vert ▶. À droite, la réponse attendue :

```xml
<ns2:sommeResponse xmlns:ns2="http://service.fss.tn/">
   <resultat>15.0</resultat>
</ns2:sommeResponse>
```

Faire une **capture d'écran** (requête à gauche + réponse à droite).

## 6. Les autres opérations (second temps)

Répéter pour `soustraction`, `multiplication`, `division`, puis les opérations bonus.
Les corps de requête prêts à copier sont dans `docs/exemples/*.xml`.

Test d'erreur : `division` avec `a=10` et `b=0` → SoapUI affiche un **SOAP Fault** :
`faultstring` = « Division par zéro impossible », `code` = `ERR_DIV_ZERO`.

## Problèmes fréquents

| Problème | Solution |
|---|---|
| `Connection refused` / WSDL non chargé | Le serveur n'est pas lancé : exécuter `ServeurJWS` |
| `Address already in use` au lancement | Port 8080 occupé : lancer avec un autre port et utiliser ce port dans l'URL |
| Les `?` restent dans la requête | Les remplacer par des valeurs, sinon la requête est invalide |
| Modification du code sans effet | Arrêter puis relancer le serveur, puis ré-importer le WSDL dans SoapUI |
| SoapUI impossible à installer | Alternatives : `sh docs/exemples/tester.sh` (curl), `client.ClientCalculatrice`, ou Postman (POST, en-tête `Content-Type: text/xml`, corps = un fichier de `docs/exemples/`) |
