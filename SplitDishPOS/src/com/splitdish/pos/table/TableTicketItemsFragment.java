package com.splitdish.pos.table;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.splitdish.lib.TicketAdapter;
import com.splitdish.lib.TicketItemList;
import com.splitdish.lib.Utilities;
import com.splitdish.pos.R;
import com.splitdish.pos.R.id;
import com.splitdish.pos.R.layout;
import com.splitdish.pos.R.raw;

public class TableTicketItemsFragment extends Fragment {

	private TicketItemList items;
	private TicketAdapter adapter;
	private View mFragmentView;
	private String tableName;
	private String courseNum;
	
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
	    
	    Bundle args;
	    args = getArguments();
	    
	    if(args != null) {
	    	courseNum = args.getString(TableTicketItemsPagerFragment.ARG_COURSE_NUMBER);
	    }
	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
		Bundle savedInstanceState) {
	  	    
	    // Inflate the layout for this fragment
	    mFragmentView = inflater.inflate(R.layout.fragment_table_ticket_items, container, false);

	    //Set the title of the ListView with the Course
	    TextView tView = (TextView)mFragmentView.findViewById(R.id.courseTitle);
	    
	    tView.setText("Course "+Integer.toString(Integer.parseInt(courseNum)+1));
	    
	    return mFragmentView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TicketItemList courseList = null;
		if(courseNum != null) {
			courseList = items.getCourseList(Integer.parseInt(courseNum));
		}
		else {
			courseList = items;
		}
		
	    adapter = new TicketAdapter(getActivity(), R.layout.ticket_item_row, courseList);
	    
	    ListView listView = (ListView)mFragmentView.findViewById(R.id.ticketListItem);
	    
		listView.setAdapter(adapter);
	}

}