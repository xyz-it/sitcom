package com.xyzit.sitcom.com.xyzit.sitcom.provider;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.xyzit.sitcom.model.VModelSalesOrder;

import java.util.ArrayList;
import java.util.List;

public class SalesOrderLoader extends AsyncTaskLoader<List<VModelSalesOrder>>
{

	private List<VModelSalesOrder> mData;
	
	public SalesOrderLoader(Context ctx){
		super(ctx);
	}
	
	@Override
	public List<VModelSalesOrder> loadInBackground()
	{
		// TODO: Implement this method
		List<VModelSalesOrder> data = new ArrayList<VModelSalesOrder>();
		
		//Call webservice
		//SimpleWebserviceCursor c = new SimpleWebserviceCursor(uri,columns);
        //c.setNotificationUri(getContext().getContentResolver(), uri);
        //callWebservice(selection, c);
		
		return data;
	}

	@Override
	public void deliverResult(List<VModelSalesOrder> data)
	{
		// TODO: Implement this method
		super.deliverResult(data);
	}

	@Override
	protected void onStopLoading()
	{
		// TODO: Implement this method
		super.onStopLoading();
	}

	@Override
	protected void onReset()
	{
		// TODO: Implement this method
		super.onReset();
	}

	@Override
	public void onCanceled(List<VModelSalesOrder> data)
	{
		// TODO: Implement this method
		super.onCanceled(data);
	}
	
	private void releaseRessource(List<VModelSalesOrder> data){
		
	}

	@Override
	protected void onStartLoading()
	{
		// TODO: Implement this method
		super.onStartLoading();
	}

}
