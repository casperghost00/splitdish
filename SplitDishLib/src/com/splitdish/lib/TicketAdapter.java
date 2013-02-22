package com.splitdish.lib;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TicketAdapter extends ArrayAdapter<TicketItem> {

	Context context; 
    int layoutResourceId;    
    List<TicketItem> items = null;
	
	public TicketAdapter(Context context, int textViewResourceId,
			List<TicketItem> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.items = items;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TicketItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new TicketItemHolder();
            holder.textTitle = (TextView)row.findViewById(R.id.textTitle);
            holder.textNotes = (TextView)row.findViewById(R.id.textNotes);
            holder.textPrice = (TextView)row.findViewById(R.id.textPrice); 
            
            row.setTag(holder);
        }
        else
        {
            holder = (TicketItemHolder)row.getTag();
        }
        
        TicketItem ticket = items.get(position);
        holder.textTitle.setText(ticket.title);
        holder.textNotes.setText(ticket.notes);
        holder.textPrice.setText(String.format("%.2f", ticket.price/100.0));
        if(ticket.selected == true) {
        	row.setBackgroundResource(R.drawable.border_highlight);
        }
        else {
        	row.setBackgroundResource(R.color.transparent);
        }
        
        return row;
    }

	static class TicketItemHolder {
		TextView textTitle;
		TextView textNotes;
		TextView textPrice;
	}
	
}
