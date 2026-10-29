#!/bin/sh
# Envoie toutes les requêtes d'exemple au service (serveur lancé sur le port 8080)
# Usage : sh docs/exemples/tester.sh
URL="${1:-http://localhost:8080/calculatrice}"
cd "$(dirname "$0")" || exit 1
for f in *.xml; do
  echo "=== $f ==="
  curl -s -H "Content-Type: text/xml; charset=utf-8" -d @"$f" "$URL"
  echo; echo
done
