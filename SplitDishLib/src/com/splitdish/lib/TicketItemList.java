package com.splitdish.lib;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketItemList extends ArrayList<TicketItem> implements Parcelable {
	
	private static final long serialVersionUID = 7586756316979267439L;
	int numCourses = 0;
	TicketItemList[] courseList;
	
	public TicketItemList() {
		super();
	}
	
	public TicketItemList(JSONObject jsonTicketItems) throws JSONException {
		super();
		
		JSONArray jTicketItems = new JSONArray();
		JSONArray jCourses = new JSONArray();
		
		jCourses = jsonTicketItems.getJSONArray("courses");

		JSONObject jItem = null;
		TicketItem item = null;
		numCourses = jCourses.length();
		for(int i=0;i<numCourses;i++) {
			jTicketItems = jCourses.getJSONObject(i).getJSONArray("items");

			for(int j=0;j<jTicketItems.length();j++) {
				jItem = jTicketItems.getJSONObject(j);
				item = new TicketItem(jItem);
				item.setCourse(i);
				add(item);
			}
		}
	}
	
	public TicketItemList getCourseList(int courseNum) {
		TicketItemList courseList = new TicketItemList();
		
		TicketItem item = null;
		for(int i=0;i<size();i++) {
			item = get(i);
			if(item.getCourse()==courseNum) {
				courseList.add(item);
			}
		}
		return courseList;
	}
	

	
	// Returns how many unique courses a list possesses
	public int numCourses() {
		return numCourses;
	}
	
	//Parcelable Interface Methods
	public TicketItemList(Parcel in) {
		int size = in.readInt();
		for(int i=0;i<size;i++) {
			this.add(new TicketItem(in));
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
	
	public static final Parcelable.Creator<TicketItemList> CREATOR = new Parcelable.Creator<TicketItemList>() {
        public TicketItemList createFromParcel(Parcel in) {
            return new TicketItemList(in);
        }

        public TicketItemList[] newArray(int size) {
            return new TicketItemList[size];
        }
    };
}