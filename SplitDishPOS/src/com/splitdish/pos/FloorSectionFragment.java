package com.splitdish.pos;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.splitdish.pos.FloorMap.FloorArea;
import com.splitdish.pos.FloorMap.Table;

public class FloorSectionFragment extends Fragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_AREA_TITLE = "com.splitdish.pos.area_title";
    public static final String ARG_JSON_FLOOR_LAYOUT = "com.splitdish.pos.json_floor_layout";
    
	private static final int X_COORD = 0;
	private static final int Y_COORD = 1;
	private enum ZoomLevel {close, standard, far}; 
	
	public FloorSectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	RelativeLayout relLayout = new RelativeLayout(getActivity());
    	
    	relLayout.setBackgroundColor(getResources().getColor(R.color.Black));
        
    	Bundle args = getArguments();
    	String areaTitle = args.getString(FloorSectionFragment.ARG_AREA_TITLE);
    	String jsonFloorLayout = args.getString(FloorSectionFragment.ARG_JSON_FLOOR_LAYOUT);
    	
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
    	
        RelativeLayout floorGrid = getAreaLayout(getActivity(), areaTitle, fMap);

       
    	relLayout.addView(floorGrid);
    	
        return relLayout;
    }
    
    // Creates a RelativeLayout based on the given FloorArea name and source file
 	private RelativeLayout getAreaLayout(Context context, String areaTitle, FloorMap fMap) {
 		RelativeLayout areaGrid = new RelativeLayout(context);
 		RelativeLayout.LayoutParams params = new RelativeLayout.
 				LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
 		
 		areaGrid.setLayoutParams(params);
 		
 		FloorArea area = fMap.getArea(areaTitle);
 		
 		if(area == null) {
 			return null;
 		}
 		
 		int[][] tableCoords = new int[area.size()][2];
 		String[] tableNames = new String[area.size()];
 		Table table = null;
 		//TODO Implement Zoom Levels
 		if(area.zoom.compareTo(ZoomLevel.close.toString()) == 0) {
 			for(int i=0;i<area.size();i++) {
 				table = area.getTable(i);
 				tableCoords[i][0]=table.coords[X_COORD]*120 + 40;
 				tableCoords[i][1]=table.coords[Y_COORD]*120 + 40;
 				tableNames[i] = table.name;
 			}
 		}
 		
 		ArrayList<ImageView> tables = area.getTableViews(context);
 		
 		RelativeLayout tableContainer = null; //Container for table name on top of image
 		ImageView tableView = null; //Image of the table
 		TextView tableNameView = null; //Text representing table name
 		
 		for(int i=0;i<tables.size();i++) {
 			tableContainer = new RelativeLayout(context);
 			
 			//Get the table ImageView from the ArrayList
 			tableView = tables.get(i);
 			
 			//Set the size of the table ImageView
 			params = new RelativeLayout.LayoutParams(100, 100);
 			
 			tableContainer.addView(tableView, params);
 			
 			//Set the proper table name
 			tableNameView = new TextView(context);
 			tableNameView.setText(tableNames[i]);
 			tableNameView.setTypeface(null, Typeface.BOLD);
 			tableNameView.setTextSize(18);
 			
 			params = new RelativeLayout.LayoutParams(
 					LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
 			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
 			 			
 			tableContainer.addView(tableNameView, params);
 			
 			params = new RelativeLayout.LayoutParams(
 					LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);; 			
 			params.leftMargin = tableCoords[i][X_COORD];
 			params.topMargin = tableCoords[i][Y_COORD];
 			
 			areaGrid.addView(tableContainer, params);
 		}		
 		
 		return areaGrid;
 	}
}
