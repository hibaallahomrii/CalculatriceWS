package client;

import jakarta.xml.ws.Service;
import java.net.URL;
import javax.xml.namespace.QName;
import service.CalculException;
import service.Constantes;
import service.ICalculatrice;
import service.Operation;

/**
 * Client Java : prouve l'interopérabilité du service sans SoapUI.
 * Le serveur (serveur.ServeurJWS) doit être lancé avant.
 */
public class ClientCalculatrice {

    public static void main(String[] args) throws Exception {
        String url = (args.length > 0) ? args[0] : "http://localhost:8080/calculatrice";

        Service service = Service.create(new URL(url + "?wsdl"),
                new QName(Constantes.NAMESPACE, "CalculatriceWS"));
        ICalculatrice calc = service.getPort(
                new QName(Constantes.NAMESPACE, "CalculatricePort"), ICalculatrice.class);

        System.out.println(calc.getInfo());
        System.out.println("10 + 5   = " + calc.somme(10, 5));
        System.out.println("10 - 5   = " + calc.soustraction(10, 5));
        System.out.println("10 * 5   = " + calc.multiplication(10, 5));
        System.out.println("10 / 4   = " + calc.division(10, 4));
        System.out.println("2 ^ 10   = " + calc.puissance(2, 10));
        System.out.println("sqrt(81) = " + calc.racineCarree(81));
        System.out.println("17 % 5   = " + calc.modulo(17, 5));
        System.out.println("20% de 250 = " + calc.pourcentage(250, 20));
        System.out.println("moyenne  = " + calc.moyenne(new double[]{12, 15, 9, 18}));
        System.out.println("5!       = " + calc.factorielle(5));

        try {
            calc.division(1, 0);
        } catch (CalculException e) {
            System.out.println("Erreur attendue : [" + e.getFaultInfo().getCode() + "] " + e.getMessage());
        }

        System.out.println("\n--- Historique ---");
        for (Operation op : calc.getHistorique()) {
            System.out.println(op);
        }
    }
}
