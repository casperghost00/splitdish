package com.splitdish.pos;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitdish.lib.TicketItemList;
import com.splitdish.lib.Utilities;

public class TableTicketItemsPagerFragment extends Fragment implements ActionBar.TabListener {

	public static final String ARG_COURSE_NUMBER = "com.splitdish.pos.COURSE_NUMBER";

    TicketItemsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Activity mParentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
    		Bundle savedInstanceState) {
    	View fragmentView = inflater.inflate(R.layout.table_ticket_items_pager, container, false);

        mSectionsPagerAdapter = new TicketItemsPagerAdapter(getChildFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) fragmentView.findViewById(R.id.ticket_item_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding tab.
        // We can also use ActionBar.Tab#select() to do this if we have a reference to the
        // Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }
        });
    	
    	return fragmentView;
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class TicketItemsPagerAdapter extends FragmentPagerAdapter {

		String[] areaTitles;
	    String jsonTicketItems;
	    Activity mParentActivity;
	    TicketItemList mItemList;
        
        public TicketItemsPagerAdapter(FragmentManager fm) {
            super(fm);
            
            try {
            	jsonTicketItems = Utilities.getTextFromRawResource(getActivity(), R.raw.sample_ticket);
            }
            catch(IOException e) {
            	e.getStackTrace();
            }
            
            try {
            	mItemList = new TicketItemList(new JSONObject(jsonTicketItems));
            }
            catch(JSONException e) {
            	e.getStackTrace();
            }
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new TableTicketItemsFragment();
            Bundle args = new Bundle();
            args.putString(TableTicketItemsPagerFragment.ARG_COURSE_NUMBER, Integer.toString(i));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mItemList.numCourses();
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	return "Course "+Integer.toString(position+1);
        }
    }

	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
