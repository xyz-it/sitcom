package com.xyzit.sitcom.com.xyzit.sitcom.provider;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.preference.PreferenceManager;
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


public class SalesOrderProvider extends ContentProvider implements IWebserviceResponseHandler
{
	//URLs de base pour interrogation
	public static final String AUTHORITY = "com.lapeyre.sales";
    public static final String PATH = "salesorder";
	
	//Identifiant pour les types d"URL
    private static final int SALESORDER = 1;
    private static final int SALESORDER_ID = 2;

	
	//A definir, tous les champs retournes par le service
    public static final String _ID = "Vbeln";
    public static final String SALESORDER_IDENTIFIER = "Vbeln";
    public static final String CUSTOMER = "Kunnr";
    public static final String EXPECTED_PLANT = "ExpectedPlant";
    public static final String STORE = "Werks";
    public static final String REMINDERSTATUS = "ReminderStatus";
    public static final String HEADERSTATUS = "HeaderStatus";
    public static final String IHREZ = "Ihrez";
    public static final String REQDELIVDATE = "ReqDelivDate";
    public static final String APPLYDELIVDATE = "ApplyDelivDate";
    public static final String BUSINESSCONTRIBUTOR = "BusinessContributor";
    public static final String INCO2 = "Inco2";
    public static final String WORKSITESTART = "WorksiteStart";
    public static final String WORKSITEDURATION = "WorksiteDuration";
    public static final String SURVEYDATE = "SurveyDate";
    public static final String INSTALLDATE = "InstallDate";
    public static final String HEADERTEXT1 = "HeaderText1";
    public static final String HEADERTEXT2 = "HeaderText2";
    public static final String COUPON = "Coupon";
    public static final String WORKSHOPCONTROL = "WorkshopControl";
    public static final String IHREZE = "IhrezE";
    public static final String BSTKD = "Bstkd";
    public static final String WORKSITEEND = "WorksiteEnd";
    public static final String KDKG2 = "Kdkg2";
    public static final String DATERAZ = "Dateraz";
    public static final String ZTERM = "Zterm";
    public static final String REDUCEDTAX = "ReducedTax";
    public static final String BSARK = "Bsark";
    public static final String FITTER = "Fitter";
    public static final String BOOK = "Book";
    public static final String BLOCK = "Block";
    public static final String MEASURER = "Measurer";
    public static final String SURVEYTIME = "SurveyTime";
    public static final String WORKSITEINDIC = "WorksiteIndic";
    public static final String VBELV = "Vbelv";
    public static final String AUART = "Auart";
    public static final String KUNNR = "Kunnr";
    public static final String KDGRP = "Kdgrp";
    public static final String VKORG = "Vkorg";
    public static final String BUKRS = "Bukrs";
    public static final String VTWEG = "Vtweg";
    public static final String SPART = "Spart";
    public static final String VKBUR = "Vkbur";
    public static final String ERDAT = "Erdat";
    public static final String AEDAT = "Aedat";
    public static final String DELIVERYDATE = "DeliveryDate";
    public static final String NETWR = "Netwr";
    public static final String GROSSAMOUNT = "Grossamount";
    public static final String WAERK = "Waerk";
    public static final String TOTALDISCOUNT = "TotalDiscount";
    public static final String CREDITDELETED = "CreditDeleted";
	
	//Facilitateur pour acceder a tous les champs
    private static final String[] allFields =
	{SalesOrderProvider._ID,
		SalesOrderProvider.SALESORDER_IDENTIFIER,
		SalesOrderProvider.CUSTOMER,
            SalesOrderProvider.EXPECTED_PLANT,
            SalesOrderProvider.STORE,
            SalesOrderProvider.REMINDERSTATUS,
            SalesOrderProvider.HEADERSTATUS,
            SalesOrderProvider.IHREZ,
            SalesOrderProvider.REQDELIVDATE,
            SalesOrderProvider.APPLYDELIVDATE,
            SalesOrderProvider.BUSINESSCONTRIBUTOR,
            SalesOrderProvider.INCO2,
            SalesOrderProvider.WORKSITESTART,
            SalesOrderProvider.WORKSITEDURATION,
            SalesOrderProvider.SURVEYDATE,
            SalesOrderProvider.INSTALLDATE,
            SalesOrderProvider.HEADERTEXT1,
            SalesOrderProvider.HEADERTEXT2,
            SalesOrderProvider.COUPON,
            SalesOrderProvider.WORKSHOPCONTROL,
            SalesOrderProvider.IHREZE,
            SalesOrderProvider.BSTKD,
            SalesOrderProvider.WORKSITEEND,
            SalesOrderProvider.KDKG2,
            SalesOrderProvider.DATERAZ,
            SalesOrderProvider.ZTERM,
            SalesOrderProvider.REDUCEDTAX,
            SalesOrderProvider.BSARK,
            SalesOrderProvider.FITTER,
            SalesOrderProvider.BOOK,
            SalesOrderProvider.BLOCK,
            SalesOrderProvider.MEASURER,
            SalesOrderProvider.SURVEYTIME,
            SalesOrderProvider.WORKSITEINDIC,
            SalesOrderProvider.VBELV,
            SalesOrderProvider.AUART,
            SalesOrderProvider.KUNNR,
            SalesOrderProvider.KDGRP,
            SalesOrderProvider.VKORG,
            SalesOrderProvider.BUKRS,
            SalesOrderProvider.VTWEG,
            SalesOrderProvider.SPART,
            SalesOrderProvider.VKBUR,
            SalesOrderProvider.ERDAT,
            SalesOrderProvider.AEDAT,
            SalesOrderProvider.DELIVERYDATE,
            SalesOrderProvider.NETWR,
            SalesOrderProvider.GROSSAMOUNT,
            SalesOrderProvider.WAERK,
            SalesOrderProvider.TOTALDISCOUNT,
            SalesOrderProvider.CREDITDELETED
	};

    private String[] columnNames;

	
	//URI pour matcher les requetes
    public static final Uri CONTENT_URI =
	Uri.parse("content://" + AUTHORITY + "/" + PATH);
	
	private String wsHostname;
	private int wsPort = 9993;
	private String wsPath = "/sap/bc/srt/rfc/sap/zbapi_customer_vendor/200/zbapi_customer_vendor/zbapi_customer_vendor";

    private Uri contentUri;

	
	//Pointer vers le cursor en cours (unique ??)
    //Considérons le comme unique pour le moment, il le faut pour récupérer la réponse du webservice (événementiel)
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
		wsMethod = sharedPref.getString("get_details", "");
		
		
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
