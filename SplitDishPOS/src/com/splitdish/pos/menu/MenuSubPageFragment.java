package com.splitdish.pos.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.splitdish.lib.MenuItem;
import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;

public class MenuSubPageFragment extends Fragment {

	public static final String ARGS_CATEGORY_TITLE = "com.splitdish.pos.menu.ARGS_CATEGORY_TITLE";
	public static final String ARGS_FIRST_LETTER = "com.splitdish.pos.menu.ARGS_FIRST_LETTER";

	private MenuItemList mMenuItemList = null;
	private String mCategory = null;
	private char mFirstLetter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		Bundle args = getArguments();
		mCategory = args.getString(ARGS_CATEGORY_TITLE);
		mFirstLetter = args.getChar(ARGS_FIRST_LETTER);
		Log.d("LETTERANDCAT", mCategory+" "+ mFirstLetter);
		mMenuItemList = GlobalMenu.getGlobalMenu().getSubListByCategory(mCategory).getSubListByFirstLetter(mFirstLetter);

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View menuView = inflater.inflate(R.layout.menu_sub_page_fragment, container, false);
		
		addItemButtons(menuView);
		
		return menuView;
	}
	
	private void addItemButtons(View menuView) {
		for(MenuItem m : mMenuItemList) {
			// Create a FrameLayout to contain buttons so they align properly
			// in the GridLayout.
			FrameLayout frameLayout = new FrameLayout(getActivity());
			Button itemButton = new Button(getActivity());
			
			itemButton.setText(m.getName());
			itemButton.setHeight(200);
			itemButton.setWidth(200);
			frameLayout.addView(itemButton);
			((GridLayout)menuView.findViewById(R.id.sub_menu_grid_layout)).addView(frameLayout);
			
		}
	}
}
