package com.splitdish.consumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlace implements Parcelable {

	double latitude;
	double longitude;
	String icon;
	String googleId;
	String name;
	String rating;
	String[] types;
	String vicinity;
	
	public GooglePlace(JSONObject json) throws JSONException {
		JSONObject geometryJSON;
		JSONObject locationJSON;
		JSONArray jArray = new JSONArray();
		
		geometryJSON = json.getJSONObject("geometry");
		locationJSON = geometryJSON.getJSONObject("location");

		latitude = Double.valueOf(locationJSON.getString("lat"));
		longitude = Double.valueOf(locationJSON.getString("lng"));
		
		icon = json.getString("icon");
		googleId = json.getString("id");
		name = json.getString("name");
		
		jArray = json.getJSONArray("types");
		types = new String[jArray.length()];
		
		for(int i=0;i<jArray.length();i++) {
			types[i] = (String)jArray.get(i);
		}
		
		vicinity = json.getString("vicinity");
	}
	
	public GooglePlace (Parcel in) {
		latitude = in.readDouble();
		longitude = in.readDouble();
		icon = in.readString();
		googleId = in.readString();
		name = in.readString();
		types = (String[]) in.readArray(null);
		vicinity = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(icon);
		dest.writeString(googleId);
		dest.writeString(name);
		dest.writeArray(types);
		dest.writeString(vicinity);
	}
}
