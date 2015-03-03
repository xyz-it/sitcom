package com.xyzit.sitcom.com.xyzit.sitcom.util.http;

/**
 * Created by kimveasna on 03/03/2015.
 */

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class CustomerSearch extends ListActivity implements IWebserviceResponseHandler, AdapterView.OnItemClickListener {

    private String wsURL = "http://192.168.253.10:8000/sap/bc/srt/rfc/sap/zbapi_customer_vendor/200/zbapi_customer_vendor/zbapi_customer_vendor";
    private String wsEndPoint = "zbapi_customer_vendor";
    private String wsMethod = "CustomerFind";

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.sample_renderer, new String[] {"Model", "ThumbnailUrl"}, new int[] {R.id.myLabel, R.id.myIcon});
        //new ArrayAdapter(null, 0, 0, null)

        this.getListView().setOnItemClickListener(this);

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
            doMySearch2(query);
        }

    }

    private void doMySearch(String query) {
        // TODO Auto-generated method stub
        SimpleWebserviceConsumer consumer = new SimpleWebserviceConsumer(wsURL, wsEndPoint, wsMethod);
        consumer.addResponseHandler(this);


        //consumer.addProperty("SeloptTab", "<item><CompCode>0002</CompCode><Tabname>KNA1</Tabname><Fieldname>NAME1</Fieldname><Fieldvalue>*" + query + "*</Fieldvalue></item>");
        consumer.addProperty("PlHold", "X");
        consumer.addProperty("ResultTab", null);

        PropertyInfo selopt = new PropertyInfo();
        selopt.name = "SeloptTab";
        selopt.type = PropertyInfo.VECTOR_CLASS;
        selopt.elementType = new PropertyInfo();

        Vector<SoapObject> seloptTab = new Vector<SoapObject>();
        selopt.setValue(seloptTab);

        SoapObject item = new SoapObject("","");
        item.addProperty("CompCode", "0002");
        item.addProperty("Tabname", "KNA1");
        item.addProperty("Fieldname", "NAME1");
        item.addProperty("Fieldvalue", "*" + query + "*");

        selopt.elementType.type = item.getClass();
        seloptTab.add(item);
        consumer.addProperty(selopt);

        consumer.call();
    }


    private void doMySearch2(String query)
    {
        Cursor c = this.managedQuery(CustomerProvider.CONTENT_URI, null, query, null, null);
        //setListAdapter(new SimpleCursorAdapter(this, R.layout.customer_item, c, new String[] {CustomerProvider.NAME, CustomerProvider.CUSTOMER_IDENTIFIER}, new int[] {R.id.customer_text, R.id.customerId}));
    }

    @Override
    public void onWebserviceResponse(Object response) {
        // TODO Auto-generated method stub
        //adapter = new SoapObjectVectorAdapter<>(this, R.layout.customer_item, response. );

        ArrayList dataList = parseSoapResponse((String) response);
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.customer_item, R.id.customer_text, dataList));
    }

    private ArrayList parseSoapResponse(String response) {
        ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
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
                    dataList.add(map);
                    //System.out.println("Start tag "+xpp.getName());
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

        return dataList;
    }

    public class SoapObjectVectorAdapter extends ArrayAdapter<SoapObject>
    {

        public SoapObjectVectorAdapter(Context context, int resource,
                                       int textViewResourceId, List<SoapObject> objects) {
            super(context, resource, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        public SoapObjectVectorAdapter(Context context, int resource,
                                       int textViewResourceId, SoapObject[] objects) {
            super(context, resource, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        public SoapObjectVectorAdapter(Context context, int resource,
                                       int textViewResourceId) {
            super(context, resource, textViewResourceId);
            // TODO Auto-generated constructor stub
        }

        public SoapObjectVectorAdapter(Context context, int textViewResourceId,
                                       SoapObject[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        public SoapObjectVectorAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            // TODO Auto-generated constructor stub
        }

        public SoapObjectVectorAdapter(Context context, int textViewResourceId,
                                       List<SoapObject> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        CursorWrapper target = (CursorWrapper) parent.getItemAtPosition(position);
        String customerID = target.getString(0);
        Intent customerIntent = new Intent(Intent.ACTION_VIEW);
        customerIntent.setData(Uri.withAppendedPath(CustomerProvider.CONTENT_URI, customerID));
        this.startActivityIfNeeded(customerIntent, 0);
        this.finish();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onRestart()
     */
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    /* (non-Javadoc)
     * @see android.app.ListActivity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(state);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onNewIntent(android.content.Intent)
     */
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
            doMySearch2(query);
        }
    }

}
