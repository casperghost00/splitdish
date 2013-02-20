package com.splitdish.pos;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.splitdish.pos.FloorMap.FloorArea;
import com.splitdish.pos.FloorMap.Table;

public class FloorSectionFragment extends Fragment {
	
	private static final int X_COORD = 0;
	private static final int Y_COORD = 1;
	private enum ZoomLevel {close, standard, far}; 

	
	public FloorSectionFragment() {
    }

    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_AREA_TITLE = "com.splitdish.pos.area_title";
    public static final String ARG_JSON_FLOOR_LAYOUT = "com.splitdish.pos.json_floor_layout";

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
 		
 		int[][] coords = new int[area.size()][2];
 		Table table = null;
 		//TODO Implement Zoom Levels
 		if(area.zoom.compareTo(ZoomLevel.close.toString()) == 0) {
 			for(int i=0;i<area.size();i++) {
 				table = area.getTable(i);
 				coords[i][0]=table.coords[X_COORD]*120 + 40;
 				coords[i][1]=table.coords[Y_COORD]*120 + 40;
 			}
 		}
 		
 		ArrayList<ImageView> tables = area.getTableViews(context);
 		
 		ImageView tableView = null;
 		
 		for(int i=0;i<tables.size();i++) {
 			tableView = tables.get(i);
 			params = new RelativeLayout.LayoutParams(100, 100);
 			params.leftMargin = coords[i][X_COORD];
 			params.topMargin = coords[i][Y_COORD];
 			
 			areaGrid.addView(tableView, params);
 		}		
 		
 		return areaGrid;
 	}
}
