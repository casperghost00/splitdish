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
	private int mNumMenuCategories = 0;
	private ArrayList<String> mCategoriesList = null;
	private int mNumCourses = 1;
	
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
		mNumMenuCategories = noDupList.size();
		
	}
	
	// Returns a MenuItemList of all items under a specified category.
	public MenuItemList getSubListByCategory(String category) {
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
	
	public MenuItem getItemById(int id) {
		for(MenuItem m : this) {
			if(m.getId() == id) {
				return m;
			}
		}
		return null;
	}
	
	// Returns how many unique courses a list possesses
	public int getCategoriesCount() {
		return mNumMenuCategories;
	}
	
	public ArrayList<String> getCategoriesList() {
		
		HashSet<String> noDupList = new HashSet<String>();
		
		for(int i=0;i<this.size();i++) {
			noDupList.add(get(i).getCategory());
		}
		mCategoriesList = new ArrayList<String>();
		mCategoriesList.addAll(noDupList);
		
		return mCategoriesList;
	}
	// Gets every item with first letter that matches the provided char
	// Currently Case-Insensitive
	public MenuItemList getSubListByFirstLetter(char firstLetter) {
		MenuItemList letterList = new MenuItemList();
		char fLetter = Character.toLowerCase(firstLetter);
		for(MenuItem m : this) {
			if(Character.toLowerCase(m.getName().charAt(0)) == fLetter) {
				letterList.add(m);
			}
		}
		return letterList;
	}
	
	public MenuItemList getCourseList(int courseNum) {
		MenuItemList courseList = new MenuItemList();
		
		MenuItem item = null;
		for(int i=0;i<size();i++) {
			item = get(i);
			if(item.getCourse()==courseNum) {
				courseList.add(item);
			}
		}
		return courseList;
	}
	
	public int getNumCourses() {
		return mNumCourses;
	}
	
	public void incrementCourses() {
		mNumCourses++;
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