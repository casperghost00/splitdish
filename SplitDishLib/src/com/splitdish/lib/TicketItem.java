package com.splitdish.lib;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketItem implements Parcelable{
	
	String title;
	String description;
	String notes;
	int price; //price in cents
	Boolean selected = false;
	int course = 0; //what course number does the item belong to
	
	public TicketItem(JSONObject jsonItem) throws JSONException {
		title = jsonItem.getString("title");
		description = jsonItem.getString("description");
		notes = jsonItem.getString("notes");
		price = jsonItem.getInt("price");		
	}
	
	public TicketItem(String title, String desc, String notes, int price) {
		this.title = title;
		this.description = desc;
		this.notes = notes;
		this.price = price;
	}
	
	public TicketItem (Parcel in) {
		title = in.readString();
		description = in.readString();
		notes = in.readString();
		price = in.readInt();
		selected = in.readByte() == 1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(notes);
		dest.writeInt(price);
		dest.writeByte((byte)(selected ? 1:0));
	}
	
	public String toString() {
		return title;
	}
	

	public static final Parcelable.Creator<TicketItem> CREATOR = new Parcelable.Creator<TicketItem>() {
        public TicketItem createFromParcel(Parcel in) {
            return new TicketItem(in);
        }

        public TicketItem[] newArray(int size) {
            return new TicketItem[size];
        }
    };
    
    public String getTitle() {
    	return title;
    }
	public String getDescription(){
		return description;
	}
	public String getNotes() {
		return notes;
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
