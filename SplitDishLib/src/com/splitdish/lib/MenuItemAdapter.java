package com.splitdish.lib;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

	Context context; 
    int layoutResourceId;    
    List<MenuItem> items = null;
	
	public MenuItemAdapter(Context context, int textViewResourceId,
			List<MenuItem> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.items = items;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MenuItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new MenuItemHolder();
            holder.textTitle = (TextView)row.findViewById(R.id.textTitle);
            holder.textNotes = (TextView)row.findViewById(R.id.textNotes);
            holder.textPrice = (TextView)row.findViewById(R.id.textPrice); 
            
            row.setTag(holder);
        }
        else
        {
            holder = (MenuItemHolder)row.getTag();
        }
        
        MenuItem item = items.get(position);
        holder.textTitle.setText(item.getName());
        holder.textNotes.setText(item.getNote());
        holder.textPrice.setText(String.format("%.2f", item.price/100.0));
        if(item.isSelected() == true) {
        	row.setBackgroundResource(R.drawable.border_highlight);
        }
        else {
        	row.setBackgroundResource(R.color.transparent);
        }
        
        return row;
    }

	static class MenuItemHolder {
		TextView textTitle;
		TextView textNotes;
		TextView textPrice;
	}
	
}
