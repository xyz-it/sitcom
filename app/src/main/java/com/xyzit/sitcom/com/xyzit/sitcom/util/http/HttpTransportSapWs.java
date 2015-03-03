package com.xyzit.sitcom.com.xyzit.sitcom.util.http;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kimveasna on 01/03/2015.
 */
public class HttpTransportSapWs extends HttpsTransportSE {
    /**
     * Creates instance of HttpTransportSE with set url
     *
     */
    public HttpTransportSapWs(String host, int port, String file, int timeout) {
        super(host, port, file, timeout);
    }

    /**
     * set the desired soapAction header field
     *
     * @param soapAction
     *            the desired soapAction
     * @param envelope
     *            the envelope containing the information for the soap call.
     */
    public void call(String soapAction, SoapEnvelope envelope) throws IOException, XmlPullParserException {
        if (soapAction == null)
            soapAction = "\"\"";
        Log.i("SAP", "vor createRequestData");
        byte[] requestData = createRequestData(envelope);
        Log.i("SAP", "nach createRequestData");
        debug = true;
        requestDump = debug ? new String(requestData) : null;

        responseDump = null;

        ServiceConnection connection = getServiceConnection();
//        connection.setRequestProperty("User-Agent", "kSOAP/2.0");
        connection.setRequestProperty("User-Agent", "Jakarta Commons-HttpClient/3.1");
        connection.setRequestProperty("Host", "");
        connection.setRequestProperty("SOAPAction", soapAction);
        connection.setRequestProperty("Content-Type", "text/xml");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Content-Length", "" + requestData.length);

        String login = android.util.Base64.encodeToString(("kpen:kimpen01".getBytes()),0);
        connection.setRequestProperty("Authorization", "Basic " + login);

        connection.setRequestMethod("POST");
        connection.connect();
        OutputStream os = connection.openOutputStream();
        String requestDump = new String(requestData);
        Log.i("request", requestDump);
        os.write(requestData, 0, requestData.length);
        os.flush();
        os.close();
        requestData = null;
        InputStream is;
        Log.i("SAP", "vor connect");
        try {
            connection.connect();
            is = connection.openInputStream();
        } catch (IOException e) {
            Log.i("SAP", "IOException");

            is = connection.getErrorStream();
            if (is == null) {
                connection.disconnect();
                throw (e);
            }
        }
        Log.i("SAP", "nach connect");
        if (true) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            while (true) {
                int rd = is.read(buf, 0, 256);
                if (rd == -1)
                    break;
                bos.write(buf, 0, rd);
            }
            bos.flush();
            buf = bos.toByteArray();
            responseDump = new String(buf);
            is.close();
            is = new ByteArrayInputStream(buf);
        }
        Log.i("response", responseDump);

        Log.i("SAP", "vor parseResponse");

        //On renvoie la r�ponse brute
        envelope.bodyIn = this.responseDump;

        // Parser la r�ponse prend beaucoup trop de temps !!!
        //parseResponse(envelope, is);
        Log.i("SAP", "nach parseResponse");
    }

    //protected ServiceConnection getServiceConnection() throws IOException {
    //    return new ServiceConnectionSE(url);
    //}
}
