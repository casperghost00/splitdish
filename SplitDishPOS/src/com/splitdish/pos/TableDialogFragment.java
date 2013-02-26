package com.splitdish.pos;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TableDialogFragment extends DialogFragment {
	
	private View mFragmentView;
	private String mTableName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = new Bundle();
		args = getArguments();
		
		mTableName = args.getString(FloorSectionFragment.ARG_TABLE_NAME);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		mFragmentView = inflater.inflate(R.layout.fragment_table_dialog, container, false);
		
		Button doNestingButton = (Button) mFragmentView.findViewById(R.id.ticket_items_button);
		
		
		Fragment noteFrag = new TableNotesFragment();
		
		doNestingButton.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
            	Fragment listFrag = new TableTicketItemsFragment();
        		FragmentTransaction fTrans = getChildFragmentManager().beginTransaction();
        		fTrans.addToBackStack("list");
        		fTrans.add(getId(), listFrag, "list");
        		fTrans.commit();
            }
        });
		/*
		fTrans = getChildFragmentManager().beginTransaction();
		fTrans.addToBackStack("note");
		fTrans.add(getId(), noteFrag, "note");
		fTrans.commit();*/
		
		return mFragmentView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	    getDialog().setTitle("Table " +mTableName);
	}
}
