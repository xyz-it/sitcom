package com.xyzit.sitcom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.*;

public class LeftMenuAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public LeftMenuAdapter(Context context, String[] values) {
		super(context, R.layout.drawer_list_item, values);
		this.context = context;
		this.values = values;
	}
	
	public LeftMenuAdapter(Context context, int layout, String[] values) {
		super(context, layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.drawer_list_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(values[position]);
		// change the icon for Windows and iPhone
		String s = values[position];
		/*if (s.startsWith("iPhone")) {
			imageView.setImageResource(R.drawable.no);
		} else {
			imageView.setImageResource(R.drawable.ok);
		}*/

		int imageId = 0;
		if (s.startsWith("Client")) {
			imageId = context.getResources().getIdentifier("ic_customer",
												   "drawable",  context.getPackageName());
		} else {
			imageId = context.getResources().getIdentifier("ic_general",
															   "drawable",  context.getPackageName());
		}
		textView.setCompoundDrawablesWithIntrinsicBounds(imageId, 0, 0, 0);
		
		return rowView;
	}
	}
