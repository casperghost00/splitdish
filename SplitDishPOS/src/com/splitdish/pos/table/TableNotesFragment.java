package com.splitdish.pos.table;

import com.splitdish.pos.R;
import com.splitdish.pos.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TableNotesFragment extends Fragment{

	private View mFragmentView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		mFragmentView = inflater.inflate(R.layout.table_notes_fragment, container, false);
		
		return mFragmentView;
	}
}
