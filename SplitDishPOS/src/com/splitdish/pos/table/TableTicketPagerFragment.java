package com.splitdish.pos.table;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;
import com.splitdish.pos.TableManager;
import com.splitdish.pos.floor.FloorSectionFragment;

public class TableTicketPagerFragment extends Fragment implements ActionBar.TabListener {

	public static final String ARG_COURSE_NUMBER = "com.splitdish.pos.COURSE_NUMBER";

    MenuItemsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
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
    	View fragmentView = inflater.inflate(R.layout.table_ticket_items_pager, container, false);

        mSectionsPagerAdapter = new MenuItemsPagerAdapter(getChildFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) fragmentView.findViewById(R.id.ticket_item_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding tab.
        // We can also use ActionBar.Tab#select() to do this if we have a reference to the
        // Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	((OnCourseSelectedListener)getParentFragment()).onCourseSelected(position);
            }
        });
    	
    	return fragmentView;
    }

    @Override
    public void onResume() {
    	super.onResume();
    	int currentCourse = mViewPager.getCurrentItem();
    	((OnCourseSelectedListener)getParentFragment()).onCourseSelected(currentCourse);
    }

    public class MenuItemsPagerAdapter extends FragmentStatePagerAdapter {

    	private TableManager mManager = null;
        private MenuItemList mItemList;
        
        public MenuItemsPagerAdapter(FragmentManager fm) {
            super(fm);
            
            mManager = TableManager.getTableManager();
            mItemList = mManager.getTable(mTableName,mSectionTitle).getTicket();
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new TableTicketItemsFragment();

            //Make sure the most recent MenuItemList is used for reference
            mItemList = mManager.getTable(mTableName,mSectionTitle).getTicket();
            
            Bundle args = new Bundle();
        	args.putString(FloorSectionFragment.ARG_TABLE_NAME, mTableName);
        	args.putString(FloorSectionFragment.ARG_SECTION_TITLE, mSectionTitle);
            args.putString(TableTicketPagerFragment.ARG_COURSE_NUMBER, Integer.toString(i));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mItemList.getNumCourses();
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	return "Course "+Integer.toString(position+1);
        }
        
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
    
    public void onCourseAddedClick() {
    	TableManager.getTableManager().getTable(mTableName,mSectionTitle).incremenetCourses();
    	mSectionsPagerAdapter.notifyDataSetChanged();
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
	
	public interface OnCourseSelectedListener {
		public void onCourseSelected(int courseNum);
	}
}
