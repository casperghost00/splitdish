package com.splitdish.lib;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem implements Parcelable{
	
	String name;
	String description;
	String category;
	int price; //price in cents
	Boolean selected = false;
	int course = 0; //what course number does the item belong to
	
	public MenuItem(JSONObject jsonItem) throws JSONException {
		name = jsonItem.getString("name");
		description = jsonItem.getString("description");
		category = jsonItem.getString("category");
		price = jsonItem.getInt("price");		
	}
	
	public MenuItem(String name, String desc, String category, int price) {
		this.name = name;
		this.description = desc;
		this.category = category;
		this.price = price;
	}
	
	public MenuItem(Parcel in) {
		name = in.readString();
		description = in.readString();
		category = in.readString();
		price = in.readInt();
		selected = in.readByte() == 1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(category);
		dest.writeInt(price);
		dest.writeByte((byte)(selected ? 1:0));
	}
	
	public String toString() {
		return name;
	}
	

	public static final Parcelable.Creator<TicketItem> CREATOR = new Parcelable.Creator<TicketItem>() {
        public TicketItem createFromParcel(Parcel in) {
            return new TicketItem(in);
        }

        public TicketItem[] newArray(int size) {
            return new TicketItem[size];
        }
    };
    
    public String getName() {
    	return name;
    }
	public String getDescription() {
		return description;
	}
	public String getCategory() {
		return category;
	}
	public int getPrice() {
		return price; //price in cents
	}
	
	public Boolean isSelected() {
		return selected;
	}
	public void setSelected(Boolean isSelected) {
		selected = isSelected;
	}
	
	public int getCourse() {
		return course;
	}
	
	public void setCourse(int courseNum) {
		course = courseNum;
	}
	
}
