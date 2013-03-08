package com.splitdish.pos.menu;

import java.util.ArrayList;
import java.util.HashSet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;
import com.splitdish.pos.floor.FloorSectionFragment;
import com.splitdish.pos.menu.MenuLetterPageFragment.OnLetterSelectedListener;

public class MenuPagerActivity extends FragmentActivity 
			implements ActionBar.TabListener, OnLetterSelectedListener {
	
	private MenuSectionsPagerAdapter mMenuPagerAdapter;
    private ViewPager mViewPager;
    private MenuItemList mMenuItemList = null;
    private FragmentManager mFragmentManager = null;
    private ArrayList<String> mCategories = null;
    private String mAssociatedTableName = null;
    private String mAssociatedSectionTitle = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_main_pager);
		
		Intent intent = getIntent();
		
		mAssociatedTableName = intent.getStringExtra(FloorSectionFragment.ARG_TABLE_NAME);
		mAssociatedSectionTitle = intent.getStringExtra(FloorSectionFragment.ARG_SECTION_TITLE);
		
		mMenuItemList = GlobalMenu.getGlobalMenu();
		mFragmentManager = getSupportFragmentManager();
		
		getCategories();
		
		mMenuPagerAdapter = new MenuSectionsPagerAdapter(mFragmentManager, mCategories);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.menu_pager);
        mViewPager.setAdapter(mMenuPagerAdapter);

        // When swiping between different sections, select the corresponding tab.
        // We can also use ActionBar.Tab#select() to do this if we have a reference to the
        // Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mMenuPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mMenuPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public class MenuSectionsPagerAdapter extends FragmentStatePagerAdapter {
 
        ArrayList<String> menuCategories = null;
        
        public MenuSectionsPagerAdapter(FragmentManager fm, ArrayList<String> menuCategories) {
            super(fm);
            
            this.menuCategories = menuCategories;
            
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new MenuPageContainerFragment();
            Bundle args = new Bundle();
            args.putString(MenuPageContainerFragment.ARGS_CATEGORY_TITLE, menuCategories.get(i));
            args.putString(FloorSectionFragment.ARG_SECTION_TITLE, mAssociatedSectionTitle);
            args.putString(FloorSectionFragment.ARG_TABLE_NAME, mAssociatedTableName);
            fragment.setArguments(args);
            
            return fragment;
        }

        @Override
        public int getCount() {
            return menuCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return menuCategories.get(position);
        }
        
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {}

    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {}
	
	public void onLetterSelected(char selectedChar) {
		int position = mViewPager.getCurrentItem();
        mCategories.remove(position);
        mMenuPagerAdapter.notifyDataSetChanged();
	}

	private void getCategories() {
		mCategories = mMenuItemList.getCategoriesList();
        
        HashSet<String> noDupList = new HashSet<String>();
		ArrayList<String> categoriesList = new ArrayList<String>();

		for(int i=0;i<mMenuItemList.size();i++) {
			noDupList.add(mMenuItemList.get(i).getCategory());
		}
		categoriesList = new ArrayList<String>();
		categoriesList.addAll(noDupList);
	}
}
