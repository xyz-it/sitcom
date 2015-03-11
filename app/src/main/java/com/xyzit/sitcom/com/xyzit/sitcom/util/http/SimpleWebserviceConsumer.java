package com.xyzit.sitcom.com.xyzit.sitcom.util.http;

/**
 * Created by kimveasna on 03/03/2015.
 */

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

//import org.ksoap2.transport.HttpTransportBasicAuth;

/**
 * @author k.pen
 *
 */
public class SimpleWebserviceConsumer {
    /**
     * La classe qui va traiter la r�ponse du webservice
     */
    private IWebserviceResponseHandler wsHandler;

    /**
     * C'est le namespace utilis� pour les webservices SAP
     */
    private static final String SAP_NAMESPACE = "urn:sap-com:document:sap:soap:functions:mc-style";

    /**
     * L'URL du point d'acc�s
     */
    private String wsURL;

    /**
     * Le nom du point d'acc�s
     */
    private String wsEndPoint;

    /**
     * La m�thode � appeler
     */
    private String wsMethod;

    /**
     * La requ�te SOAP � envoyer
     */
    private SoapObject wsRequest;

    /**
     * Constructeur
     *
     * @param wsURL
     *            URL du point d'acc�s
     * @param wsEndPoint
     *            Nom du point d'acc�s
     * @param wsMethod
     *            M�thode � appeler
     */
    public SimpleWebserviceConsumer(String wsURL, String wsEndPoint,
                                    String wsMethod) {
        super();
        this.wsURL = wsURL;
        this.wsEndPoint = wsEndPoint;
        this.wsMethod = wsMethod;

        /** Construction de la requ�te */
        wsRequest = new SoapObject(SAP_NAMESPACE, wsMethod);
    }

    /**
     * Ajout de param�tres � la requ�te. Pour simplifier, la m�thode n'est qu'un wrapper
     *
     * @param name Nom du param�tre
     * @param value Valeur du param�tre
     */
    public void addProperty(String name, Object value)
    {
        wsRequest.addProperty(name, value);
    }

    public void addProperty(PropertyInfo value)
    {
        wsRequest.addProperty(value);
    }


    /**
     * Appel de l'objet qui va prendre en charge la r�ponse XML
     *
     * @param handler L'objet qui va traiter la r�ponse
     */
    public void addResponseHandler(IWebserviceResponseHandler handler) {
        this.wsHandler = handler;
    }

    /**
     * Appel du webservice
     */
    public void call() {
        /** Creation du SoapEnvelope */
        SimpleSoapSerializationEnveloppe envelope = new SimpleSoapSerializationEnveloppe(
                SoapEnvelope.VER11); // put all required data into a soap
        // envelope
        envelope.dotNet = false;
        envelope.implicitTypes = true;
        envelope.setOutputSoapObject(wsRequest);

        /**
         * Creating AndroidTransport for passing the request to the URL where
         * the service is located
         */
        //HttpTransportSE httpTransport = new HttpTransportSE(wsURL);
        HttpsTransportSE httpTransport = new HttpsTransportSE("", 0, "", 0);
        httpTransport.debug = true;
        try {
            httpTransport.call(wsEndPoint, envelope);
            wsHandler.onWebserviceResponse((String) envelope.getResponse());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // send request

    }
}
