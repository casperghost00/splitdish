package com.splitdish.lib;

import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItemList extends ArrayList<MenuItem> implements Parcelable {

	private static final long serialVersionUID = -4182332833418859270L;
	private int numMenuCategories = 0;
	private ArrayList<String> categoriesList = null;
	
	public MenuItemList() {
		super();
	}
	
	public MenuItemList(JSONObject jsonMenuItems) throws JSONException {
		super();
		
		JSONArray jMenuItems = new JSONArray();
		jMenuItems = jsonMenuItems.getJSONArray("menu_items");

		JSONObject jItem = null;
		MenuItem item = null;
		HashSet<String> noDupList = new HashSet<String>();
		
		for(int i=0;i<jMenuItems.length();i++) {
			jItem = jMenuItems.getJSONObject(i);
			item = new MenuItem(jItem);
			noDupList.add(item.getCategory());
			add(item);
		}
		numMenuCategories = noDupList.size();
		
	}
	
	// Returns a MenuItemList of all items under a specified category.
	public MenuItemList getListByCategory(String category) {
		MenuItemList categoryList = new MenuItemList();
		
		MenuItem item = null;
		for(int i=0;i<size();i++) {
			item = get(i);
			if(item.getCategory().compareTo(category) == 0) {
				categoryList.add(item);
			}
		}
		return categoryList;
	}
	
	// Returns how many unique courses a list possesses
	public int getCategoriesCount() {
		return numMenuCategories;
	}
	
	public ArrayList<String> getCategoriesList() {
		
		HashSet<String> noDupList = new HashSet<String>();
		
		for(int i=0;i<this.size();i++) {
			noDupList.add(get(i).getCategory());
		}
		categoriesList = new ArrayList<String>();
		categoriesList.addAll(noDupList);
		
		return categoriesList;
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