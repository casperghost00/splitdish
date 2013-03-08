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
import com.splitdish.pos.menu.MenuMainPageFragment.OnLetterSelectedListener;

public class MenuPageContainerFragment extends Fragment implements OnLetterSelectedListener {

public static final String ARGS_CATEGORY_TITLE = "com.splitdish.pos.menu.ARGS_CATEGORY_TITLE";
	
	private String mCategory = null;
	private Fragment mFragment = null;
	OnLetterSelectedListener mListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		mCategory = args.getString(ARGS_CATEGORY_TITLE);
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
    	
    	Fragment mainPageFrag = new MenuMainPageFragment();
    	
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
		Fragment subMenuFrag = new MenuSubPageFragment();
		
		Bundle args = new Bundle();
		args.putString(MenuSubPageFragment.ARGS_CATEGORY_TITLE, mCategory);
		args.putChar(MenuSubPageFragment.ARGS_FIRST_LETTER, selectedChar);
		
		subMenuFrag.setArguments(args);
    	
		fTrans.replace(R.id.menu_page_placeholder, subMenuFrag, "subMenu");
		fTrans.addToBackStack(null);
		fTrans.commit();
		
	}
}
