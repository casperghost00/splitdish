package com.splitdish.lib;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItemList extends ArrayList<MenuItem> implements Parcelable {
	
	int numMenuCategories = 0;
	MenuItemList[] mMenuCategoryList;
	
	public MenuItemList() {
		super();
	}
	
	public MenuItemList(JSONObject jsonMenuItems) throws JSONException {
		super();
		
		JSONArray jMenuItems = new JSONArray();
		JSONArray jMenuCategories = new JSONArray();
		
		jMenuCategories = jsonMenuItems.getJSONArray("category");

		JSONObject jItem = null;
		MenuItem item = null;
		numMenuCategories = jMenuCategories.length();
		for(int i=0;i<numMenuCategories;i++) {
			jMenuItems = jMenuCategories.getJSONObject(i).getJSONArray("items");

			for(int j=0;j<jMenuItems.length();j++) {
				jItem = jMenuItems.getJSONObject(j);
				item = new MenuItem(jItem);
				item.setCourse(i);
				add(item);
			}
		}
	}
	
	public MenuItemList getCourseList(int courseNum) {
		MenuItemList categoryList = new MenuItemList();
		
		MenuItem item = null;
		for(int i=0;i<size();i++) {
			item = get(i);
			if(item.getCourse()==courseNum) {
				categoryList.add(item);
			}
		}
		return categoryList;
	}
	

	
	// Returns how many unique courses a list possesses
	public int numCourses() {
		return numMenuCategories;
	}
	
	//Parcelable Interface Methods
	public MenuItemList(Parcel in) {
		int size = in.readInt();
		for(int i=0;i<size;i++) {
			this.add(new MenuItem(in));
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.size());
		for(int i=0;i<this.size();i++) {
			this.get(i).writeToParcel(dest,flags);
		}
	}
	
	public static final Parcelable.Creator<MenuItemList> CREATOR = new Parcelable.Creator<MenuItemList>() {
        public MenuItemList createFromParcel(Parcel in) {
            return new MenuItemList(in);
        }

        public MenuItemList[] newArray(int size) {
            return new MenuItemList[size];
        }
    };
}