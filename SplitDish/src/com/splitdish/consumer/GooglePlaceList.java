package com.splitdish.consumer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlaceList extends ArrayList<GooglePlace> implements Parcelable {
	
	//Takes JSON returned from Google Places API URL request
	//and constructs a list of GooglePlaces from it
	public GooglePlaceList(JSONObject jsonFromPlacesAPI) throws JSONException {
		
		JSONArray jArray = new JSONArray();
		jArray = jsonFromPlacesAPI.getJSONArray("results");
	
		JSONObject jObject;
		for(int i=0;i<jArray.length();i++) {
			jObject = jArray.getJSONObject(i);
			this.add(new GooglePlace(jObject));
		}
	}
	
	public GooglePlaceList(Parcel in) {
		int size = in.readInt();
		for(int i=0;i<size;i++) {
			this.add(new GooglePlace(in));
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
}
