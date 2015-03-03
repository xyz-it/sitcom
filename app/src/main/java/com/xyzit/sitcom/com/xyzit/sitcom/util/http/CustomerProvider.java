package com.xyzit.sitcom.com.xyzit.sitcom.util.http;

/**
 * Created by kimveasna on 03/03/2015.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.FileNotFoundException;
import java.util.Vector;

public class CustomerProvider extends ContentProvider {

    public static final String AUTHORITY = "com.kimveasna.customerprovider";
    public static final String PATH = "customer";
    private static final int CUSTOMER = 1;
    private static final int CUSTOMER_ID = 2;

    public static final String _ID = "_id";
    public static final String CUSTOMER_IDENTIFIER = "Customer";
    public static final String NAME = "Name";
    public static final String CITY = "City";
    public static final String POSTCODE = "PostlCode";
    public static final String STREET = "Street";
    public static final String TELEPHONE = "Telephone";
    public static final String COUNTRY = "Country";
    private static final String[] allFields =
            {CustomerProvider._ID,
                    CustomerProvider.CUSTOMER_IDENTIFIER,
                    CustomerProvider.NAME,
                    CustomerProvider.STREET,
                    CustomerProvider.CITY,
                    CustomerProvider.POSTCODE,
                    CustomerProvider.COUNTRY,
                    CustomerProvider.TELEPHONE
            };



    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + PATH);


    private String wsURL = "http://192.168.253.10:8000/sap/bc/srt/rfc/sap/zbapi_customer_vendor/200/zbapi_customer_vendor/zbapi_customer_vendor";
    private String wsEndPoint = "zbapi_customer_vendor";
    private String wsMethod = "CustomerFind";
    private SimpleWebserviceConsumer consumer;

    private static final UriMatcher sURIMatcher;

    static
    {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, PATH, CUSTOMER);
        sURIMatcher.addURI(AUTHORITY, PATH + "/#", CUSTOMER_ID);
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        switch (sURIMatcher.match(uri)) {
            case CUSTOMER_ID:
                return "vnd.android.cursor.item/vnd.kimveasna.customer";

            case CUSTOMER:
                return "vnd.android.cursor.dir/vnd.kimveasna.customer";

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        switch (sURIMatcher.match(uri)) {
            case CUSTOMER:
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        String[] columns = projection;
        if (columns == null)
            columns = allFields;
        SimpleWebserviceCursor c = new SimpleWebserviceCursor(uri,columns);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        callWebservice(selection, c);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }


    private void callWebservice(String selection, IWebserviceResponseHandler handler)
    {
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

    /* (non-Javadoc)
     * @see android.content.ContentProvider#openFile(android.net.Uri, java.lang.String)
     */
    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }


}
