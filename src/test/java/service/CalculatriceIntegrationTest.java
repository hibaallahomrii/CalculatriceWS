package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.Service;
import java.net.URL;
import javax.xml.namespace.QName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test d'intégration : publie vraiment le service (port 8099) puis l'appelle en SOAP.
 */
class CalculatriceIntegrationTest {

    private static final String URL = "http://localhost:8099/calculatrice-test";
    private static Endpoint endpoint;
    private static ICalculatrice port;

    @BeforeAll
    static void demarrer() throws Exception {
        endpoint = Endpoint.publish(URL, new Calculatrice());
        Service s = Service.create(new URL(URL + "?wsdl"),
                new QName(Constantes.NAMESPACE, "CalculatriceWS"));
        port = s.getPort(new QName(Constantes.NAMESPACE, "CalculatricePort"), ICalculatrice.class);
    }

    @AfterAll
    static void arreter() {
        if (endpoint != null) {
            endpoint.stop();
        }
    }

    @Test
    void sommeViaSoap() {
        assertEquals(15.0, port.somme(10, 5), 1e-9);
    }

    @Test
    void moyenneViaSoap() throws CalculException {
        assertEquals(13.5, port.moyenne(new double[]{12, 15, 9, 18}), 1e-9);
    }

    @Test
    void divisionParZeroRenvoieUnSoapFaultTypee() {
        CalculException e = assertThrows(CalculException.class, () -> port.division(1, 0));
        assertEquals(CalculException.ERR_DIV_ZERO, e.getFaultInfo().getCode());
    }

    @Test
    void historiqueViaSoap() {
        port.viderHistorique();
        port.somme(1, 1);
        assertEquals(1, port.getHistorique().size());
        assertEquals("somme", port.getHistorique().get(0).getType());
    }
}
