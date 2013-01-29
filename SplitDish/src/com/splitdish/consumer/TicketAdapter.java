package com.splitdish.consumer;

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
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtNotes = (TextView)row.findViewById(R.id.txtNotes);
            
            row.setTag(holder);
        }
        else
        {
            holder = (TicketItemHolder)row.getTag();
        }
        
        TicketItem ticket = items.get(position);
        holder.txtTitle.setText(ticket.title);
        holder.txtNotes.setText(ticket.notes);
        
        return row;
    }

	static class TicketItemHolder {
		TextView txtTitle;
		TextView txtNotes;
	}
	
}
