package com.splitdish.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemSelectionActivity extends ListActivity {

	private static final ArrayList<String> FRUITS = new ArrayList<String>(Arrays.asList("Apple", "Avocado", "Banana",
			"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
			"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple"));
	private List<TicketItem> items;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_item_selection);
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
		
		adapter = new ArrayAdapter<String>(this, R.layout.activity_place_search,FRUITS);
		setListAdapter(adapter);
		
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    Toast.makeText(getApplicationContext(),
				((TextView) view).getText()+" "+position, Toast.LENGTH_SHORT).show();
			    
			    //FRUITS.remove(position);
			    
			    //adapter.notifyDataSetChanged();
			    
			}
		});
	}

}
