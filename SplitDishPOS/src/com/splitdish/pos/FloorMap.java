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
import android.widget.Space;
import android.widget.TextView;


public class FloorMap {

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
		
		public Table getTable(String tableName) {
			Table table = null;
			for(int i=0;i<tables.size();i++) {
				if(tables.get(i).name.compareTo(tableName)==0) {
					table = tables.get(i);
				}
			}
			return table;
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
				
		        param.rightMargin = 5;
		        param.topMargin = 5;
		        param.setGravity(Gravity.CENTER);
		        param.columnSpec = GridLayout.spec(tables.get(i).coords[0]);
		        param.rowSpec = GridLayout.spec(tables.get(i).coords[1]);
		        param.height = 100;
		        param.width = 100;
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
			
			coords[0] = jCoords.getInt("x-co");
			coords[1] = jCoords.getInt("y-co");
		}
	}
	
	public String[] getAreaTitles() {
		String[] areaTitles = new String[areas.size()];
		
		for(int i=0;i<areas.size();i++) {
			areaTitles[i] = areas.get(i).title;
		}
		
		return areaTitles;
	}
	
	// Creates a GridLayout based on the given FloorArea name
	public GridLayout getAreaLayout(Context context, String areaTitle) {
		GridLayout areaGrid = new GridLayout(context);
		GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
		gridParams.height = LayoutParams.MATCH_PARENT;
		gridParams.width = LayoutParams.MATCH_PARENT;
		
		areaGrid.setLayoutParams(gridParams);
		
		FloorArea area = getArea(areaTitle);
		
		if(area == null) {
			return null;
		}
		
		if(area.zoom.compareTo(ZoomLevel.close.toString()) == 0) {
			areaGrid.setRowCount(10);
			areaGrid.setColumnCount(10);
		}
		
		ArrayList<ImageView> tables = area.getTableViews(context);
		
		for(int i=0;i<tables.size();i++) {
			areaGrid.addView(tables.get(i));
			TextView text = new TextView(context);
			text.setText(Integer.toString(i));
			text.setTextColor(context.getResources().getColor(R.color.Red));
			areaGrid.addView(text);
		}
	
		TextView testText;
		
		for(int i=0;i<area.tables.size();i++) {
			testText = new TextView(context);

			testText.setText("Name: "+area.tables.get(i).name+
					"\nCoordinates: "+area.tables.get(i).coords[0]+","+
					area.tables.get(i).coords[1]);
			testText.setTextColor(context.getResources().getColor(R.color.White));

			areaGrid.addView(testText);
			
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
