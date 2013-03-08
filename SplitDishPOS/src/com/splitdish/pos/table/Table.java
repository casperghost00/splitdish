package com.splitdish.pos.table;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.ImageView;

import com.splitdish.lib.MenuItem;
import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;
import com.splitdish.pos.menu.GlobalMenu;

public class Table {

	public static final int X_COORD = 0;
	public static final int Y_COORD = 1;
	
	private MenuItemList mTableOrderList = null;
	private String mTableName = null;
	private String mFloorSection = null;
	private String mTableShape = null;
	private int mTableSize = 0;
	private int[] mTableCoords;
	//TODO Add notes to each table
	
	public Table(String name, String section) {
		mTableName = name;
		mFloorSection = section;
		mTableOrderList = new MenuItemList();
	}
	
	public Table(JSONObject jsonTable) throws JSONException {
		mTableCoords = new int[2];
		
		mTableName = jsonTable.getString("name");
		mTableShape = jsonTable.getString("shape");
		mTableSize = jsonTable.getInt("size");
		mFloorSection = jsonTable.getString("section");
		
		JSONObject jCoords = jsonTable.getJSONObject("coordinates");
		
		mTableCoords[X_COORD] = jCoords.getInt("x-co");
		mTableCoords[Y_COORD] = jCoords.getInt("y-co");
		
		mTableOrderList = new MenuItemList();
	}
	
	public void addToTicket(int itemId) {
		mTableOrderList.add(GlobalMenu.getGlobalMenu().getItemById(itemId));
	}
	
	public void addToTicket(int itemId, int courseNum) {
		MenuItem item = GlobalMenu.getGlobalMenu().getItemById(itemId);
		item.setCourse(courseNum);
		mTableOrderList.add(item);
	}
	
	public void addToTicket(MenuItem item) {
		mTableOrderList.add(item);
	}
	
	public MenuItemList getTicket() {
		return (MenuItemList)mTableOrderList.clone();
	}
	
	public void removeFromTicket(MenuItem item) {
		mTableOrderList.remove(item);
	}
	
	public void setItemCourse(MenuItem item, int courseNum) {
		MenuItem editItem = mTableOrderList.get(mTableOrderList.indexOf(item));
		editItem.setCourse(courseNum);
	}
	
	public String getName() {
		return mTableName;
	}
	
	public String getSection() {
		return mFloorSection;
	}
	
	public int getSize() {
		return mTableSize;
	}
	
	public String getShape() {
		return mTableShape;
	}
	
	public int[] getCoords() {
		return mTableCoords;
	}
	
	public int getNumCourses() {
		return mTableOrderList.getNumCourses();
	}
	
	public void incremenetCourses() {
		mTableOrderList.incrementCourses();
	}
	
	public ImageView getView(Context context) {
		ImageView tableView = new ImageView(context);

		//TODO Check what size the table is to determine resource
        //TODO Check what shape the table is to determine resource
		if(true) { //square, standard size
			tableView.setImageResource(R.drawable.square_table);
		}		
        return tableView;
	}
	
	public boolean equals(Object table) {
		if(	(this.getName().compareTo(((Table)table).getName())==0) &&
			(this.getSection().compareTo(((Table)table).getSection())==0) ) {
			return true;
		}
		return false;
	}
}
