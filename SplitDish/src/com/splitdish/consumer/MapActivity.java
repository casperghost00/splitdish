package com.splitdish.consumer;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;



public class MapActivity extends Activity {


	private GoogleMap mMap;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_map);
		/*
		MapFragment mMapFragment = MapFragment.newInstance();
		FragmentTransaction fragmentTransaction =
				getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.map_container, mMapFragment);
		fragmentTransaction.commit();
		
		getFragmentManager().executePendingTransactions();
		*/
		setUpMapIfNeeded();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}
	
	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	    	if(getFragmentManager().findFragmentById(R.id.map)==null) {
	    		Log.d("NullCheck", "Fragment is null");
	    	}
	        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.
	        	
	        	mMap.setMyLocationEnabled(true);
	        	Location loc = ((LocationManager)getSystemService(LOCATION_SERVICE))
	        			.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	        	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
	        			new LatLng(loc.getLatitude(),loc.getLongitude()), 15));
	        }
	    }
	}

}
