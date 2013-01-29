package com.splitdish.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemSelectionActivity extends Activity {

	private static final ArrayList<String> FRUITS = new ArrayList<String>(Arrays.asList("Apple", "Avocado", "Banana",
			"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
			"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple"));
	private List<TicketItem> items;
	private TicketAdapter adapter;
	private ListView listView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_selection);
		
		items = new ArrayList<TicketItem>();
		
		items.add(new TicketItem("Chicken Parmesan", "","Hold the Chicken",14.99));
		items.add(new TicketItem("Chicken n Waffles","","Double Syrup",29.99));
		items.add(new TicketItem("Waldorf Salad","","Extra Girly",12.99));
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
		
		View header = (View)getLayoutInflater().inflate(R.layout.ticket_header_row, null);
		listView1.addHeaderView(header);
		listView1.setAdapter(adapter);
 
		listView1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position > 0) {
				    // When clicked, show a toast with the TextView text
				    Toast.makeText(getApplicationContext(),
				    		parent.getItemAtPosition(position).toString()+" "+position,
				    		Toast.LENGTH_SHORT).show();
				    
				    //FRUITS.remove(position);
				    
				    //adapter.notifyDataSetChanged();
				}
			    
			}
		});
	}

}
