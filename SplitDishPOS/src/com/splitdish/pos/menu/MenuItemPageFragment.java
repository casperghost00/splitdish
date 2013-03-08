package com.splitdish.pos.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.splitdish.lib.MenuItem;
import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;

public class MenuItemPageFragment extends Fragment {

	private MenuItemList mMenuItemList = null;
	private String mCategory = null;
	private char mFirstLetter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		Bundle args = getArguments();
		mCategory = args.getString(MenuPageContainerFragment.ARGS_CATEGORY_TITLE);
		mFirstLetter = args.getChar(MenuLetterPageFragment.ARGS_FIRST_LETTER);
		
		mMenuItemList = GlobalMenu.getGlobalMenu()
				.getSubListByCategory(mCategory)
					.getSubListByFirstLetter(mFirstLetter);

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
			
			final int itemId = m.getId();
			
			itemButton.setText(m.getName());
			itemButton.setHeight(200);
			itemButton.setWidth(200);
			itemButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					((OnMenuItemSelectedListener)getParentFragment()).onMenuItemSelected(itemId);					
				}
				
			});
			frameLayout.addView(itemButton);
			((GridLayout)menuView.findViewById(R.id.sub_menu_grid_layout)).addView(frameLayout);
			
		}
	}
	
	// Container Activity must implement this interface
    public interface OnMenuItemSelectedListener {
        public void onMenuItemSelected(int itemId);
    }
}
