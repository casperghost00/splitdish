package com.splitdish.pos.floor;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.splitdish.pos.R;
import com.splitdish.pos.TableManager;
import com.splitdish.pos.table.Table;
import com.splitdish.pos.table.TableDialogFragment;

public class FloorSectionFragment extends Fragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_SECTION_TITLE = "com.splitdish.pos.section_title";
    public static final String ARG_JSON_FLOOR_LAYOUT = "com.splitdish.pos.json_floor_layout";
    public static final String ARG_TABLE_NAME = "com.splitdish.pos.table_name";
    
    private String mSectionTitle = null;
    private TableManager mTableManager = null;
	
	private FloorMap fMap = null;
	
	public FloorSectionFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		mSectionTitle = args.getString(ARG_SECTION_TITLE);
        
        mTableManager = TableManager.getTableManager();
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	RelativeLayout relLayout = new RelativeLayout(getActivity());
    	
    	relLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
    	
        RelativeLayout floorGrid = getAreaLayout(getActivity(), mSectionTitle, fMap);

       
    	relLayout.addView(floorGrid);
    	
        return relLayout;
    }
    
    // Creates a RelativeLayout based on the given FloorArea name and source file
 	private RelativeLayout getAreaLayout(Context context, String sectionTitle, FloorMap fMap) {
 		RelativeLayout sectionGrid = new RelativeLayout(context);
 		RelativeLayout.LayoutParams params = new RelativeLayout.
 				LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
 		
 		sectionGrid.setLayoutParams(params);
 		
 		ArrayList<Table> section = mTableManager.getTablesBySection(sectionTitle);
 		
 		if(section.size() == 0) {
 			return sectionGrid;
 		}
 		
 		int[][] tableCoords = new int[section.size()][2];
 		Table table = null;
 		//TODO Implement Zoom Levels
 		if(true) { //standard zoom
 			for(int i=0;i<section.size();i++) {
 				table = section.get(i);
 				tableCoords[i][0]=table.getCoords()[Table.X_COORD]*120 + 40;
 				tableCoords[i][1]=table.getCoords()[Table.Y_COORD]*120 + 40;
 			}
 		}
 		RelativeLayout tableContainer = null; //Container for table name on top of image
 		ImageView tableView = null; //Image of the table
 		TextView tableNameView = null; //Text representing table name
 		for(int i=0;i<section.size();i++) {
 			tableContainer = new RelativeLayout(context);
 			
 			table = section.get(i);

 			tableView = table.getView(context);
 			final String tableName = table.getName();
 			
 			
 			//Set the size of the table ImageView
 			params = new RelativeLayout.LayoutParams(100, 100);
 			
 			tableContainer.addView(tableView, params);
 			
 			//Set the proper table name
 			tableNameView = new TextView(context);
 			tableNameView.setText(tableName);
 			tableNameView.setTypeface(null, Typeface.BOLD);
 			tableNameView.setTextSize(18);
 			tableNameView.setTextColor(getResources().getColor(R.color.black));
 			
 			params = new RelativeLayout.LayoutParams(
 					LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
 			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
 			 			
 			tableContainer.addView(tableNameView, params);
 			
 			params = new RelativeLayout.LayoutParams(
 					LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);; 			
 			params.leftMargin = tableCoords[i][Table.X_COORD];
 			params.topMargin = tableCoords[i][Table.Y_COORD];
 			
 			sectionGrid.addView(tableContainer, params);

 			tableView.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					tableSelected(v,tableName, mSectionTitle);
				}
 			});
 		}		
 		
 		return sectionGrid;
 	}
 	
 	private void tableSelected(View v, String tableName, String sectionTitle) {
 		FragmentManager fm = getChildFragmentManager();
 		
 		Bundle args = new Bundle();
 		
 		TableDialogFragment itemFrag = new TableDialogFragment();
 		
 		//Tell the fragment what table it refers to
 		args.putString(FloorSectionFragment.ARG_TABLE_NAME, tableName);
 		args.putString(FloorSectionFragment.ARG_SECTION_TITLE, sectionTitle);
 		itemFrag.setArguments(args);
 		
 		itemFrag.show(fm, "fragment_ticket_list");
 	}
}
