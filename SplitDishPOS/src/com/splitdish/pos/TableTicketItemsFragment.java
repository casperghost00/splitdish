package com.splitdish.pos;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.splitdish.lib.TicketAdapter;
import com.splitdish.lib.TicketItemList;
import com.splitdish.lib.Utilities;

public class TableTicketItemsFragment extends Fragment {

	private TicketItemList items;
	private TicketAdapter adapter;
	private View mFragmentView;
	private String tableName;
	
	public TableTicketItemsFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String jsonRaw = null;
	    try {
			jsonRaw = Utilities.getTextFromRawResource(getActivity(), R.raw.sample_ticket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    try {
	    	JSONObject jTicket = new JSONObject(jsonRaw);
	    	items = new TicketItemList(jTicket);
	    } catch (JSONException e) {
	    	e.printStackTrace();
	    }
	    
	    //tableName = getArguments().getString(FloorSectionFragment.ARG_TABLE_NAME);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
		Bundle savedInstanceState) {
	  	    
	    // Inflate the layout for this fragment
	    mFragmentView = inflater.inflate(R.layout.fragment_table_ticket_items, container, false);

	    return mFragmentView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	    adapter = new TicketAdapter(getActivity(), R.layout.ticket_item_row, items);
	    
	    ListView listView = (ListView)mFragmentView.findViewById(R.id.ticketListItem);
	    
		listView.setAdapter(adapter);
	}

}