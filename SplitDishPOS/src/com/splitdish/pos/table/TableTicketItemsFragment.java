package com.splitdish.pos.table;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.splitdish.lib.MenuItemAdapter;
import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;
import com.splitdish.pos.TableManager;
import com.splitdish.pos.floor.FloorSectionFragment;

public class TableTicketItemsFragment extends Fragment {

	private MenuItemList mTicketItems = null;
	private MenuItemAdapter mAdapter = null;
	private View mFragmentView = null;
	private String mTableName = null;
	private String mSectionTitle = null;
	private String mCourseNum = null;
	
	public TableTicketItemsFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
	    Bundle args;
	    args = getArguments();
	    
	    mTableName = args.getString(FloorSectionFragment.ARG_TABLE_NAME);
	    mSectionTitle = args.getString(FloorSectionFragment.ARG_SECTION_TITLE);
	    
	    mCourseNum = args.getString(TableTicketPagerFragment.ARG_COURSE_NUMBER);    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
		Bundle savedInstanceState) {
	  	    
	    // Inflate the layout for this fragment
	    mFragmentView = inflater.inflate(R.layout.table_ticket_items_fragment, container, false);

	    //Set the title of the ListView with the Course
	    TextView tView = (TextView)mFragmentView.findViewById(R.id.courseTitle);

	    tView.setText("Course "+Integer.toString(Integer.parseInt(mCourseNum)+1));
	    
	    return mFragmentView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	

	public void onResume() {
		super.onResume();
		
		TableManager tManager = TableManager.getTableManager();
	    Table table = tManager.getTable(mTableName,mSectionTitle);
	    mTicketItems = table.getTicket();
		
		MenuItemList courseList = null;
		courseList = mTicketItems.getCourseList(Integer.parseInt(mCourseNum));
		
	    mAdapter = new MenuItemAdapter(getActivity(), R.layout.ticket_item_row, courseList);
	    
	    ListView listView = (ListView)mFragmentView.findViewById(R.id.ticketListItem);
	    
		listView.setAdapter(mAdapter);
	}

}