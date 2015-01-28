package com.xyzit.sitcom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.*;

import java.util.Locale;

public class LeftMenuAdapter extends ArrayAdapter<String> {
	private final Context context;
	private int[] itemView = new int[7];

	
	public LeftMenuAdapter(Context context, int layout, String[] values) {
		//super(context, layout, values);
        super(context, layout, values);

        for(int i=0;i<values.length;i++){
            itemView[i] = context.getResources().getIdentifier(values[i],
                    "layout", context.getPackageName());
        }

		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

/*
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.drawer_list_item, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.label);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(values[position]);
		// change the icon for Windows and iPhone
		String s = values[position];

		int imageId = 0;
		if (s.startsWith("Client")) {
			imageId = context.getResources().getIdentifier("ic_customer",
												   "drawable",  context.getPackageName());
		} else {
			imageId = context.getResources().getIdentifier("ic_general",
															   "drawable",  context.getPackageName());
		}
		textView.setCompoundDrawablesWithIntrinsicBounds(imageId, 0, 0, 0);
		
		return rowView;*/

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(itemView[position], parent, false);


        if(position==0){
            Spinner spinner = (Spinner) rowView.findViewById(R.id.user_store);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.store_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);

        }

        return rowView;
	}
	}
