package service;

import jakarta.jws.WebService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation métier du service CalculatriceWS.
 * Une seule instance est publiée : l'historique est donc partagé par tous les appels.
 */
@WebService(
        serviceName = "CalculatriceWS",
        portName = "CalculatricePort",
        targetNamespace = Constantes.NAMESPACE,
        endpointInterface = "service.ICalculatrice"
)
public class Calculatrice implements ICalculatrice {

    private static final int TAILLE_MAX_HISTORIQUE = 100;
    private static final DateTimeFormatter FORMAT_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<Operation> historique = new ArrayList<>();

    // ---------- Opérations de base ----------

    @Override
    public double somme(double a, double b) {
        return enregistrer("somme", a + b, a, b);
    }

    @Override
    public double soustraction(double a, double b) {
        return enregistrer("soustraction", a - b, a, b);
    }

    @Override
    public double multiplication(double a, double b) {
        return enregistrer("multiplication", a * b, a, b);
    }

    @Override
    public double division(double a, double b) throws CalculException {
        if (b == 0) {
            throw CalculException.of(CalculException.ERR_DIV_ZERO, "Division par zéro impossible");
        }
        return enregistrer("division", a / b, a, b);
    }

    // ---------- Opérations avancées ----------

    @Override
    public double puissance(double base, double exposant) throws CalculException {
        double r = Math.pow(base, exposant);
        if (Double.isNaN(r) || Double.isInfinite(r)) {
            throw CalculException.of(CalculException.ERR_RESULTAT_INVALIDE,
                    "Résultat non représentable pour " + base + "^" + exposant);
        }
        return enregistrer("puissance", r, base, exposant);
    }

    @Override
    public double racineCarree(double a) throws CalculException {
        if (a < 0) {
            throw CalculException.of(CalculException.ERR_RACINE_NEGATIVE,
                    "Racine carrée d'un nombre négatif impossible");
        }
        return enregistrer("racineCarree", Math.sqrt(a), a);
    }

    @Override
    public double modulo(double a, double b) throws CalculException {
        if (b == 0) {
            throw CalculException.of(CalculException.ERR_MODULO_ZERO, "Modulo par zéro impossible");
        }
        return enregistrer("modulo", a % b, a, b);
    }

    @Override
    public double pourcentage(double valeur, double taux) {
        return enregistrer("pourcentage", valeur * taux / 100.0, valeur, taux);
    }

    @Override
    public double moyenne(double[] valeurs) throws CalculException {
        if (valeurs == null || valeurs.length == 0) {
            throw CalculException.of(CalculException.ERR_LISTE_VIDE,
                    "La liste de valeurs est vide");
        }
        double somme = 0;
        for (double v : valeurs) {
            somme += v;
        }
        return enregistrer("moyenne", somme / valeurs.length, valeurs);
    }

    @Override
    public long factorielle(int n) throws CalculException {
        if (n < 0 || n > 20) {
            throw CalculException.of(CalculException.ERR_FACTORIELLE,
                    "n doit être compris entre 0 et 20 (limite du type long)");
        }
        long r = 1;
        for (int i = 2; i <= n; i++) {
            r *= i;
        }
        enregistrer("factorielle", r, n);
        return r;
    }

    // ---------- Historique ----------

    @Override
    public List<Operation> getHistorique() {
        synchronized (historique) {
            return new ArrayList<>(historique);
        }
    }

    @Override
    public int viderHistorique() {
        synchronized (historique) {
            int n = historique.size();
            historique.clear();
            return n;
        }
    }

    @Override
    public String getInfo() {
        return "CalculatriceWS v1.0 - Jakarta XML Web Services - FSS LSI3-GLSI 2025-2026";
    }

    // ---------- Utilitaire interne (non exposé : absent de l'interface) ----------

    private double enregistrer(String type, double resultat, double... operandes) {
        List<Double> ops = new ArrayList<>();
        for (double o : operandes) {
            ops.add(o);
        }
        Operation op = new Operation(type, ops, resultat, LocalDateTime.now().format(FORMAT_DATE));
        System.out.println("[" + type + "] " + ops + " = " + resultat);
        synchronized (historique) {
            historique.add(op);
            if (historique.size() > TAILLE_MAX_HISTORIQUE) {
                historique.remove(0);
            }
        }
        return resultat;
    }
}
