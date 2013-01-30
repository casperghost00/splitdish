package com.splitdish.consumer;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketItemList extends ArrayList<TicketItem> implements Parcelable {
	
	public TicketItemList() {
		super();
	}
	
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