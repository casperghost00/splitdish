package com.splitdish.pos.table;

import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TableManager {

	private static TableManager mTableManager = null;
	private ArrayList<Table> mTableList = null;
	
	private TableManager() {
		mTableList = new ArrayList<Table>();
	}
	
	public static TableManager getTableManager() {
		if(mTableManager == null) {
			mTableManager = new TableManager();
		}
		return mTableManager;
	}
	
	public void setFloorLayout(JSONObject jsonFloorLayout) throws JSONException {
		JSONArray jArray = new JSONArray();
		jArray = jsonFloorLayout.getJSONArray("tables");
	
		JSONObject jTable;
		for(int i=0;i<jArray.length();i++) {
			jTable = jArray.getJSONObject(i);
			mTableList.add(new Table(jTable));
		}
	}
	
	public void addTable(Table table) {
		mTableList.add(table);
	}
	
	public void removeTable(Table table) {
		mTableList.remove(table);
	}
	
	public Table getTable(String name, String section) {
		int index = mTableList.indexOf(new Table(name,section));
		return mTableList.get(index);
	}
	
	public Table getTable(Table table) {
		int index = mTableList.indexOf(table);
		return mTableList.get(index);
	}
	
	public ArrayList<Table> getTablesBySection(String section) {
		ArrayList<Table> sectionTables = new ArrayList<Table>();
		for(Table t : mTableList) {
			if(t.getSection().compareTo(section)==0) {
				sectionTables.add(t);
			}
		}
		return sectionTables;
	}
	
	public ArrayList<String> getSectionTitles() {
		
		HashSet<String> uniqueSections = new HashSet<String>();
		for(Table t : mTableList) {
			uniqueSections.add(t.getSection());
		}
		ArrayList<String> sections = new ArrayList<String>(uniqueSections);

		return sections;
	}
	
}
