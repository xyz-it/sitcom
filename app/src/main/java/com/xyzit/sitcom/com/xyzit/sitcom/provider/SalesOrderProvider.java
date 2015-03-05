package com.xyzit.sitcom.com.xyzit.sitcom.provider;
import android.content.*;
import android.database.*;
import android.net.*;
import com.xyzit.sitcom.com.xyzit.sitcom.util.http.*;

public class SalesOrderProvider extends ContentProvider
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


    public static final Uri CONTENT_URI =
	Uri.parse("content://" + AUTHORITY + "/" + PATH);
	
	private String wsHostname;
	private int wsPort = 9993;
	private String wsPath = "/sap/bc/srt/rfc/sap/zbapi_customer_vendor/200/zbapi_customer_vendor/zbapi_customer_vendor";

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
	public Cursor query(Uri p1, String[] p2, String p3, String[] p4, String p5)
	{
		// TODO: Implement this method
		return null;
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
	
	
}
