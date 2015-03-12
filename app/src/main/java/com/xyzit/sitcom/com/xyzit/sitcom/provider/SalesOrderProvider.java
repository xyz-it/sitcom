package com.xyzit.sitcom.com.xyzit.sitcom.provider;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.xyzit.sitcom.com.xyzit.sitcom.util.http.CustomerProvider;
import com.xyzit.sitcom.com.xyzit.sitcom.util.http.IWebserviceResponseHandler;
import com.xyzit.sitcom.com.xyzit.sitcom.util.http.SimpleWebserviceConsumer;
import com.xyzit.sitcom.com.xyzit.sitcom.util.http.SimpleWebserviceCursor;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;
import android.content.*;
import android.preference.*;

public class SalesOrderProvider extends ContentProvider implements IWebserviceResponseHandler
{

	public static final String AUTHORITY = "com.lapeyre.sales.salesorderprovider";
    public static final String PATH = "salesorder";
    private static final int SALESORDER = 1;
    private static final int SALESORDER_ID = 2;

    public static final String _ID = "Vbeln";
    public static final String SALESORDER_IDENTIFIER = "Vbeln";
    public static final String CUSTOMER = "Kunnr";
    
    private static final String[] allFields =
	{SalesOrderProvider._ID,
		SalesOrderProvider.SALESORDER_IDENTIFIER,
		SalesOrderProvider.CUSTOMER
		
	};

    private String[] columnNames;

    public static final Uri CONTENT_URI =
	Uri.parse("content://" + AUTHORITY + "/" + PATH);
	
	private String wsHostname;
	private int wsPort = 9993;
	private String wsPath = "/sap/bc/srt/rfc/sap/zbapi_customer_vendor/200/zbapi_customer_vendor/zbapi_customer_vendor";

    private Uri contentUri;

    SimpleWebserviceCursor currentCursor;


	@Override
	public String toString()
	{
		// TODO: Implement this method
		return super.toString();
	}
	private String wsURL = "https://" + 
							wsHostname + 
							":" + wsPort + 
							wsPath;
							
    private String wsEndPoint = "zbapi_customer_vendor";
    private String wsMethod = "CustomerFind";
    private SimpleWebserviceConsumer consumer;
	
	private static UriMatcher sURIMatcher;
	
	
	@Override
	public boolean onCreate()
	{
		// TODO: Implement this method
		sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, PATH, SALESORDER);
        sURIMatcher.addURI(AUTHORITY, PATH + "/#", SALESORDER_ID);
		
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder)
	{
		// TODO Auto-generated method stub
		switch (sURIMatcher.match(uri)) {
			case SALESORDER:
				break;


			case SALESORDER_ID:
				break;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}


		String[] columns = projection;
		if (columns == null)
			columns = allFields;

        contentUri = uri;
        currentCursor = new SimpleWebserviceCursor(uri,columns);
        currentCursor.setNotificationUri(getContext().getContentResolver(), uri);
		callWebservice(selection, currentCursor);

		return currentCursor;

	}

	@Override
	public String getType(Uri uri)
	{
		// TODO: Implement this method
		switch (sURIMatcher.match(uri)) {
            case SALESORDER_ID:
                return "vnd.android.cursor.item/vnd.lapeyre.sales.salesorder";

            case SALESORDER:
                return "vnd.android.cursor.dir/vnd.lapeyre.sales.salesorder";

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
	}

	@Override
	public Uri insert(Uri p1, ContentValues p2)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public int delete(Uri p1, String p2, String[] p3)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int update(Uri p1, ContentValues p2, String p3, String[] p4)
	{
		// TODO: Implement this method
		return 0;
	}


    private void callWebservice(String selection, IWebserviceResponseHandler handler)
    {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
		wsURL = sharedPref.getString("url", "");
		wsEndPoint = sharedPref.getString("endpoint", "");
		wsPort = sharedPref.getInt("port", 8080);
		
        consumer = new SimpleWebserviceConsumer(wsURL, wsEndPoint, wsMethod);
        consumer.addResponseHandler(handler);

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
        item.addProperty("Fieldvalue", "*" + selection + "*");

        selopt.elementType.type = item.getClass();
        seloptTab.add(item);
        consumer.addProperty(selopt);

        consumer.call();
    }

    @Override
    public void onWebserviceResponse(Object response) {
        parseSoapResponse((String) response);
        //mContentResolver.notifyChange(contentUri, null);

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

            xpp.setInput( new StringReader( response ) );

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
        String[] colNames = columnNames;
        MatrixCursor.RowBuilder row = currentCursor.newRow();
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
