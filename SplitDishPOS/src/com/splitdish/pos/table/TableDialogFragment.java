package com.splitdish.pos.table;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.splitdish.pos.R;
import com.splitdish.pos.floor.FloorSectionFragment;
import com.splitdish.pos.menu.MenuPagerActivity;
import com.splitdish.pos.table.TableTicketPagerFragment.OnCourseSelectedListener;

public class TableDialogFragment extends DialogFragment 
		implements OnCourseSelectedListener {
	
	public static final String ARG_CURRENT_COURSE = "com.splitdish.pos.table.ARG_CURRENT_COURSE";
	
	private View mFragmentView;
	private String mTableName;
	private String mSectionTitle;
	private int mCurrentCourse = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = new Bundle();
		args = getArguments();
		
		mTableName = args.getString(FloorSectionFragment.ARG_TABLE_NAME);
		mSectionTitle = args.getString(FloorSectionFragment.ARG_SECTION_TITLE);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		mFragmentView = inflater.inflate(R.layout.table_dialog_fragment, container, false);		
		
		Fragment noteFrag = new TableNotesFragment();
    	Fragment listFrag = new TableTicketPagerFragment();
    	
    	Bundle args = new Bundle();
    	args.putString(FloorSectionFragment.ARG_TABLE_NAME, mTableName);
    	args.putString(FloorSectionFragment.ARG_SECTION_TITLE, mSectionTitle);
    	listFrag.setArguments(args);
    	
		FragmentTransaction fTrans = getChildFragmentManager().beginTransaction();
		fTrans.add(R.id.list_fragment_placeholder, listFrag, "list");
		fTrans.commit();

		fTrans = getChildFragmentManager().beginTransaction();
		fTrans.add(R.id.note_fragment_placeholder, noteFrag, "note");
		fTrans.commit();
		
		mFragmentView.findViewById(R.id.add_item_button).setOnClickListener(menuOnClickListener);
		mFragmentView.findViewById(R.id.add_course_button).setOnClickListener(courseOnClickListener);
		
		return mFragmentView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	    getDialog().setTitle("Table " +mTableName);
	}
	
	private OnClickListener menuOnClickListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(getActivity(), MenuPagerActivity.class);

        	intent.putExtra(FloorSectionFragment.ARG_TABLE_NAME, mTableName);
        	intent.putExtra(FloorSectionFragment.ARG_SECTION_TITLE, mSectionTitle);
        	intent.putExtra(ARG_CURRENT_COURSE, mCurrentCourse);
        	
        	startActivity(intent);
        }
    }; 
    private OnClickListener courseOnClickListener = new OnClickListener() {
        public void onClick(View v) {
        	((TableTicketPagerFragment)getChildFragmentManager()
        			.findFragmentByTag("list")).onCourseAddedClick();
        }
    }; 
    
    public void onCourseSelected(int courseNum) {
    	mCurrentCourse = courseNum;
    	Log.d("CurrentCourse", Integer.toString(mCurrentCourse));
    }
}
