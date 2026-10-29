package serveur;

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * SOAPHandler qui affiche chaque requête et chaque réponse SOAP (XML indenté) dans la console.
 */
public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        afficher(context, "REPONSE SOAP (serveur -> client)", "REQUETE SOAP (client -> serveur)");
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        afficher(context, "SOAP FAULT (serveur -> client)", "SOAP FAULT (entrant)");
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    private void afficher(SOAPMessageContext context, String titreSortant, String titreEntrant) {
        boolean sortant = Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
        System.out.println();
        System.out.println("======== " + (sortant ? titreSortant : titreEntrant) + " ========");
        System.out.println(formater(context.getMessage()));
        System.out.println("==============================================");
    }

    private String formater(SOAPMessage message) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            message.writeTo(out);
            String xml = out.toString(StandardCharsets.UTF_8);

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StringWriter writer = new StringWriter();
            t.transform(new StreamSource(new StringReader(xml)), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            return "(message illisible : " + e.getMessage() + ")";
        }
    }
}
