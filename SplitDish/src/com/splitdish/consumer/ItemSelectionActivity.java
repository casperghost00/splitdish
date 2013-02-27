package com.splitdish.consumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.splitdish.lib.TicketAdapter;
import com.splitdish.lib.TicketItem;
import com.splitdish.lib.TicketItemList;

public class ItemSelectionActivity extends Activity {

	
	private static final String SELECTED_ITEMS = "com.splitdish.consumer.SELECTED_ITEMS";
	private TicketItemList items;
	private TicketItemList selectedItems;
	private TicketAdapter adapter;
	private ListView listView1;
	private int subtotal = 0;
	private boolean allSelected = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_selection);
		
		items = new TicketItemList();
		selectedItems = new TicketItemList();
		
		items.add(new TicketItem("Chicken Parmesan", "","Hold the Chicken",1499));
		items.add(new TicketItem("Chicken n Waffles","","Double Syrup",2999));
		items.add(new TicketItem("Waldorf Salad","","Extra Girly",1299));
		items.add(new TicketItem("Barbeque Chopped Chicken Salad","","",1399));
		items.add(new TicketItem("Calamari","","",999));
		items.add(new TicketItem("Long Island Iced Tea","","",799));
		items.add(new TicketItem("Dr. Pepper","","",299));
		items.add(new TicketItem("Apple Crumble","","",799));
		items.add(new TicketItem("Hot Fudge Sundae","","",499));
		items.add(new TicketItem("H3 Pinot Noir","","",1199));
		items.add(new TicketItem("H3 Merlot","","Extra Large Glass",1099));
		items.add(new TicketItem("Protein Shake", "", "Muscle Milk",499));
		items.add(new TicketItem("Hot Fudge Sundae","","",499));
		items.add(new TicketItem("H3 Pinot Noir","","",1199));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_item_selection, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		
		adapter = new TicketAdapter(this, R.layout.ticket_item_row, items);
		
		listView1 = (ListView)findViewById(R.id.listView1);
		
		listView1.setAdapter(adapter);
		
	    listView1.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			    
			    
	        	itemPressed(parent,view,position,id);
	        	
	        	refreshSubtotal();        	
            }
	    });
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		selectedItems.clear();
	}
	
	private void itemPressed(AdapterView<?> parent, View view, int position, long id) {
		TicketItem item = adapter.getItem(position);
		if(item.isSelected() == false) {
	    	view.setBackgroundResource(R.drawable.border_highlight);
	    	subtotal+=item.getPrice();
	    	item.setSelected(true);
	    }
	    else {
	    	view.setBackgroundResource(R.color.transparent);
	    	subtotal-=item.getPrice();
	    	item.setSelected(false);

	    }
	}
	
	public void checkOut(View v) {
		
		for(int i=0; i<items.size(); i++) {
			if(items.get(i).isSelected()) {
				selectedItems.add(items.get(i));
			}
		}
		
		Intent intent = new Intent(this, CheckOutActivity.class);
		intent.putExtra(SELECTED_ITEMS, (Parcelable)selectedItems);
    	startActivity(intent);
	}
	
	public void selectAll(View v) {
		allSelected = allSelected ? false : true;
		for(int i=0; i<items.size(); i++) {
			TicketItem item = items.get(i);
			if(allSelected) {
				if(item.isSelected() == false)
					subtotal+=item.getPrice();
				item.setSelected(true);
				
			}
			else {
				if(item.isSelected()) {
					subtotal-=item.getPrice();
				}
				item.setSelected(false);
				
			}
		}
		refreshSubtotal();
		for(int i=0; i<listView1.getChildCount(); i++) {
		    View item = (View)listView1.getChildAt(i);
		    if(allSelected) {
		    	item.setBackgroundResource(R.drawable.border_highlight);
		    }
		    else {
		    	item.setBackgroundResource(R.color.transparent);
		    }
		}
	}

	private void refreshSubtotal() {

    	TextView tv = (TextView)findViewById(R.id.subtotalText);
    	tv.setText("Subtotal: $"+String.format("%.2f", subtotal/100.0));
	}
}
