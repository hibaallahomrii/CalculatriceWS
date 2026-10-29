# Séquence Git conseillée (historique propre et progressif)

```bash
git init
git branch -M main
git remote add origin https://github.com/VOTRE_USER/CalculatriceWS.git
```

Commits (adaptez selon ce que vous avez réellement testé) :

```bash
git add pom.xml .gitignore
git commit -m "Activité 1 : projet Maven et dépendances Jakarta XML Web Services"

git add src/main/java/service src/main/java/serveur
git commit -m "Activités 2-4 : service annoté, serveur ServeurJWS (testé avec la méthode somme)"

git add docs
git commit -m "Activité 5 : exemples de requêtes SoapUI et correspondance WSDL/XSD"

git add src/main/java/client src/test
git commit -m "Bonus : client Java et tests JUnit"

git add README.md
git commit -m "Documentation : README"

git push -u origin main
```

Si GitHub refuse le mot de passe : utilisez un *Personal Access Token* (Settings → Developer settings),
ou dans IntelliJ : *Git → GitHub → Share Project on GitHub*.
