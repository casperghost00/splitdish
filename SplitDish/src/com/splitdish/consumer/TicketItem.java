package com.splitdish.consumer;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketItem implements Parcelable{
	
	String title;
	String description;
	String notes;
	int price; //price in cents
	Boolean selected;
	
	public TicketItem(String title, String desc, String notes, int price) {
		this.title = title;
		this.description = desc;
		this.notes = notes;
		this.price = price;
		selected = false;
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
	
}
