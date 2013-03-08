package com.splitdish.pos.table;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.splitdish.pos.R;
import com.splitdish.pos.floor.FloorSectionFragment;
import com.splitdish.pos.menu.MenuPagerActivity;

public class TableDialogFragment extends DialogFragment {
	
	private View mFragmentView;
	private String mTableName;
	private String mSectionTitle;
	
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
    	Fragment listFrag = new TableTicketItemsPagerFragment();
    	
		FragmentTransaction fTrans = getChildFragmentManager().beginTransaction();
		fTrans.add(R.id.list_fragment_placeholder, listFrag, "list");
		fTrans.commit();

		fTrans = getChildFragmentManager().beginTransaction();
		fTrans.add(R.id.note_fragment_placeholder, noteFrag, "note");
		fTrans.commit();
		
		mFragmentView.findViewById(R.id.add_item_button).setOnClickListener(menuOnClickListener);
		
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
        	
        	startActivity(intent);
        }
    }; 
}
