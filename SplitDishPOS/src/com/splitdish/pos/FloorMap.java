package com.splitdish.pos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;



public class FloorMap {

	private static final int X_COORD = 0;
	private static final int Y_COORD = 1;
	private enum ZoomLevel {close, standard, far}; 

	ArrayList<FloorArea> areas;
	String timestamp;
	
	public FloorMap(JSONObject jsonFloorLayout) throws JSONException {
		areas = new ArrayList<FloorArea>();
		
		timestamp = jsonFloorLayout.getString("timestamp");
		
		JSONArray jArray = new JSONArray();
		jArray = jsonFloorLayout.getJSONArray("floor_layout");
	
		JSONObject jArea;
		for(int i=0;i<jArray.length();i++) {
			jArea = jArray.getJSONObject(i);
			areas.add(new FloorArea(jArea));
		}
	}
	
	private class FloorArea {
		String title;
		String zoom;
		ArrayList<Table> tables;
		public FloorArea(JSONObject jsonFloorArea) throws JSONException {
			tables = new ArrayList<Table>();
			
			title = jsonFloorArea.getString("title");
			zoom = jsonFloorArea.getString("zoom");
			
			JSONArray jArray = new JSONArray();
			jArray = jsonFloorArea.getJSONArray("tables");
			
			JSONObject jTable;
			for(int i=0;i<jArray.length();i++) {
				jTable = jArray.getJSONObject(i);
				tables.add(new Table(jTable));
			}
		}		
		
		//Returns number of tables in the area
		public int size() {
			return tables.size();
		}
		
		public Table getTable(String tableName) {
			Table table = null;
			for(int i=0;i<tables.size();i++) {
				if(tables.get(i).name.compareTo(tableName)==0) {
					table = tables.get(i);
				}
			}
			return table;
		}
		
		public Table getTable(int position) {
			return tables.get(position);
		}

		public ArrayList<ImageView> getTableViews(Context context) {
			ArrayList<ImageView> tableViews = new ArrayList<ImageView>();
			ImageView tableView;
			GridLayout.LayoutParams param;

			//For each table set the parameters and add it to the 
			//tableViews ArrayList
			for(int i=0;i<tables.size();i++) {
				
				tableView = new ImageView(context);
				param = new GridLayout.LayoutParams();
				
		        param.setGravity(Gravity.CENTER);
		        tableView.setLayoutParams(param);
		        
		        //TODO Check what shape the table is to determine resource
		        tableView.setImageResource(R.drawable.square_table);
		        
		        tableViews.add(tableView);
			}
			return tableViews;
		}
	}
	
	private class Table {

		String name = "";
		String shape = "";
		int size = 0;
		int[] coords;
		
		public Table(JSONObject jsonTable) throws JSONException {
			coords = new int[2];
			
			name = jsonTable.getString("name");
			shape = jsonTable.getString("shape");
			size = jsonTable.getInt("size");
			
			JSONObject jCoords = jsonTable.getJSONObject("coordinates");
			
			coords[X_COORD] = jCoords.getInt("x-co");
			coords[Y_COORD] = jCoords.getInt("y-co");
		}
	}
	
	public String[] getAreaTitles() {
		String[] areaTitles = new String[areas.size()];
		
		for(int i=0;i<areas.size();i++) {
			areaTitles[i] = areas.get(i).title;
		}
		
		return areaTitles;
	}
	
	// Creates a RelativeLayout based on the given FloorArea name and source file
	public RelativeLayout getAreaLayout(Context context, String areaTitle) {
		RelativeLayout areaGrid = new RelativeLayout(context);
		RelativeLayout.LayoutParams params = new RelativeLayout.
				LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		areaGrid.setLayoutParams(params);
		
		FloorArea area = getArea(areaTitle);
		
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
	
	public FloorArea getArea(String areaTitle) {
		FloorArea area = null;

		for(int i=0;i<areas.size();i++) {
			if(areas.get(i).title.compareTo(areaTitle)==0) {
				area = areas.get(i);
			}
		}
		return area;
	}
}
