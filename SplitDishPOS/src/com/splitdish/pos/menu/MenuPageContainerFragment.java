package com.splitdish.pos.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;

import com.splitdish.pos.R;
import com.splitdish.pos.floor.FloorSectionFragment;
import com.splitdish.pos.menu.MenuItemPageFragment.OnMenuItemSelectedListener;
import com.splitdish.pos.menu.MenuLetterPageFragment.OnLetterSelectedListener;
import com.splitdish.pos.table.Table;
import com.splitdish.pos.table.TableManager;

public class MenuPageContainerFragment extends Fragment 
	implements OnLetterSelectedListener, OnMenuItemSelectedListener {

	public static final String ARGS_CATEGORY_TITLE = "com.splitdish.pos.menu.ARGS_CATEGORY_TITLE";
	
	private String mCategory = null;
	private Fragment mFragment = null;
	private TableManager mTableManager = null;
    private String mAssociatedTableName = null;
    private String mAssociatedSectionTitle = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTableManager = TableManager.getTableManager();
		
		Bundle args = getArguments();
		mCategory = args.getString(ARGS_CATEGORY_TITLE);
		mAssociatedTableName = args.getString(FloorSectionFragment.ARG_TABLE_NAME);
		mAssociatedSectionTitle = args.getString(FloorSectionFragment.ARG_SECTION_TITLE);
        mFragment = this;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View menuView = inflater.inflate(R.layout.menu_page_container_fragment, container, false);

    	menuView.setFocusableInTouchMode(true);
    	menuView.requestFocus();
    	menuView.setOnKeyListener( new OnKeyListener()
    	{
    	    public boolean onKey( View v, int keyCode, KeyEvent event) {
    	        if( keyCode == KeyEvent.KEYCODE_BACK )
    	        {
    	        	if(mFragment.getChildFragmentManager().popBackStackImmediate()) {
    	        		return true;
    	        	}
    	        }
    	        return false;
    	    }
    	});
    	FragmentManager manager = getChildFragmentManager();
    	
    	manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    	
    	Fragment mainPageFrag = new MenuLetterPageFragment();
    	
    	Bundle args = new Bundle();
    	args.putString(ARGS_CATEGORY_TITLE, mCategory);
    	
    	mainPageFrag.setArguments(args);
    	
		FragmentTransaction fTrans = manager.beginTransaction();
		fTrans.replace(R.id.menu_page_placeholder, mainPageFrag, "main");
		fTrans.commit();

    	return menuView;
    }

	public void onLetterSelected(char selectedChar) {
		FragmentTransaction fTrans = getChildFragmentManager().beginTransaction();
		Fragment subMenuFrag = new MenuItemPageFragment();
		
		Bundle args = new Bundle();
		args.putString(ARGS_CATEGORY_TITLE, mCategory);
		args.putChar(MenuLetterPageFragment.ARGS_FIRST_LETTER, selectedChar);
		
		subMenuFrag.setArguments(args);
    	
		fTrans.replace(R.id.menu_page_placeholder, subMenuFrag, "subMenu");
		fTrans.addToBackStack(null);
		fTrans.commit();
		
	}

	public void onMenuItemSelected(int itemId) {
		Table table = mTableManager.getTable(mAssociatedTableName, mAssociatedSectionTitle);
		table.addToTicket(itemId);
		getActivity().finish();
	}
}
