package com.xyzit.sitcom.com.xyzit.sitcom.util.http;

/**
 * Created by kimveasna on 03/03/2015.
 */
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class SimpleWebserviceCursor extends MatrixCursor implements IWebserviceResponseHandler {

    private Uri contentUri;

    public SimpleWebserviceCursor(Uri uri, String[] columnNames) {
        super(columnNames);
        this.contentUri = uri;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onWebserviceResponse(Object response) {
        // TODO Auto-generated method stub
        parseSoapResponse((String) response);
        mContentResolver.notifyChange(contentUri, null);
    }


    private void parseSoapResponse(String response) {
        //ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
        boolean resultTab_reached = false;
        try
        {
            //SoapObject result = soap(METHOD_NAME, SOAP_ACTION, NAMESPACE, URL);
            //Log.i(TAG,"Result:" + result);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader ( response ) );

            //ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    //System.out.println("Start document");
                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    //System.out.println("End document");
                } else if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("ResultTab")) {
                    resultTab_reached = true;

                } else if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("item") && resultTab_reached) {
                    HashMap<String,String> map = new HashMap<String,String>();
                    parseItem(xpp, map);
                    //dataList.add(map);
                    //System.out.println("Start tag "+xpp.getName());
                    addItem(map);
                } else if(eventType == XmlPullParser.END_TAG && xpp.getName().equals("ResultTab")) {
                    resultTab_reached = false;
                    //System.out.println("End tag "+xpp.getName());
                } else if(eventType == XmlPullParser.END_TAG) {
                } else if(eventType == XmlPullParser.TEXT) {
                    //System.out.println("Text "+xpp.getText());
                }
                eventType = xpp.next();
            }
        }
        catch(Exception e)
        {
        }
        Log.i("KIM", "end");

        //return dataList;
    }


    private void parseItem(XmlPullParser parser, HashMap<String, String> map) throws XmlPullParserException, IOException
    {
        String attrName = null;
        String attrValue = null;
        int eventType = parser.next();
        //int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_TAG || !parser.getName().equals("item"))
        {
            if(eventType == XmlPullParser.START_TAG)
                attrName = parser.getName();
            else if (eventType == XmlPullParser.TEXT)
            {
                attrValue = parser.getText();
                if(attrName != null && attrValue != null)
                    map.put(attrName, attrValue);

                attrName = null;
                attrValue = null;
            }
            eventType = parser.next();
        }
    }


    private void addItem(HashMap<String, String> map)
    {
        String[] colNames = getColumnNames();
        MatrixCursor.RowBuilder row = newRow();
        row.add(map.get("Customer"));
        for (int i=1; i < colNames.length; i++)
        {
            String value = map.get(colNames[i]);
            if (colNames[i] == CustomerProvider.NAME && value == null)
                value = map.get("Fieldvalue");
            if (value != null)
                row.add(value);
            else
                row.add("");
        }
    }
}
