package com.splitdish.pos.floor;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.splitdish.lib.Utilities;
import com.splitdish.pos.R;
import com.splitdish.pos.R.id;
import com.splitdish.pos.R.layout;
import com.splitdish.pos.R.menu;
import com.splitdish.pos.R.raw;

public class FloorActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    FloorSectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    static String jsonFloorLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floor_main_pager);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.

        try {
        	jsonFloorLayout = Utilities.getTextFromRawResource(this, R.raw.floor_layout);
        }
        catch(IOException e) {
        	e.getStackTrace();
        }
        
        mSectionsPagerAdapter = new FloorSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    }
    

    //@Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    //@Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    //@Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class FloorSectionsPagerAdapter extends FragmentPagerAdapter {

        String[] areaTitles;
        
        public FloorSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            
            JSONObject jFloorLayout;
            FloorMap fMap = null;
            
            try {
            	jFloorLayout = new JSONObject(jsonFloorLayout);
                fMap = new FloorMap(jFloorLayout);
            }
            catch(JSONException e)
            {
            	e.printStackTrace();
            }
            
            areaTitles = fMap.getAreaTitles();
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new FloorSectionFragment();
            Bundle args = new Bundle();
            args.putString(FloorSectionFragment.ARG_AREA_TITLE, areaTitles[i]);
            args.putString(FloorSectionFragment.ARG_JSON_FLOOR_LAYOUT, jsonFloorLayout);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return areaTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return areaTitles[position];
        }
    }
}
