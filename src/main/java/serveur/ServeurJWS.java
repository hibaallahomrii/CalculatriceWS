package serveur;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;
import service.Calculatrice;

/**
 * Activité 4 : publication du service web avec Endpoint.
 * Usage : lancer main() (port 8080 par défaut) ou passer un port en argument.
 */
public class ServeurJWS {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main(String[] args) {
        String port = (args.length > 0) ? args[0] : "8080";
        String url = "http://localhost:" + port + "/calculatrice";

        Endpoint endpoint = Endpoint.create(new Calculatrice());

        // Ajout du handler de journalisation des messages SOAP
        Binding binding = endpoint.getBinding();
        List<Handler> chaine = new ArrayList<>(binding.getHandlerChain());
        chaine.add(new LoggingHandler());
        binding.setHandlerChain(chaine);

        endpoint.publish(url);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            endpoint.stop();
            System.out.println("Service arrêté.");
        }));

        System.out.println("==============================================");
        System.out.println(" Service CalculatriceWS publié avec succès !");
        System.out.println(" WSDL : " + url + "?wsdl");
        System.out.println(" XSD  : " + url + "?xsd=1");
        System.out.println(" (Arrêter avec le carré rouge d'IntelliJ)");
        System.out.println("==============================================");
    }
}
