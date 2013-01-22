package com.splitdish.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;

public class PlaceSearchActivity extends Activity {

	public final static String PLACE_INTENT = "com.splitdish.consumer.PLACE_INTENT";
	public final static String PLACE = "com.splitdish.consumer.PLACE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_search);
		
		new PlaceSearchTask().execute();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(placesReceiver, new IntentFilter(PLACE_INTENT));
	}
	
	private BroadcastReceiver placesReceiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
		    // Get extra data included in the Intent
		    GooglePlaceList gplaces = intent.getParcelableExtra(PLACE);
		    Log.d("receiver", "Got PlaceList: " + gplaces.get(0).name);
		  }
		};
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_place_search, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(placesReceiver);
		super.onPause();
	}
	
	private class PlaceSearchTask extends AsyncTask<String,String,GooglePlaceList> {
    	
    	@Override
    	public void onPreExecute() {
    		
    	}
    	
    	@Override
    	public GooglePlaceList doInBackground(String... params) {
    		GooglePlaceList gplaces = searchPlaces();
    		
    		return gplaces;
    	}
    	
    	@Override
    	public void onProgressUpdate(String... values) {
    		
    	}
    	
    	@Override
    	public void onPostExecute(GooglePlaceList result) {
    		for(GooglePlace g : result) {
	    		Log.d("JSON", g.name);
	    		Log.d("JSON", g.icon);
	    		Log.d("JSON", g.googleId);
	    		Log.d("JSON", String.valueOf(g.latitude));
	    		Log.d("JSON", String.valueOf(g.longitude));
    		}
    		

    	}
    	
    	private GooglePlaceList searchPlaces() {
    		String url = "";
    		String apiKey = "AIzaSyAv8VYAVMhsBU4WZMIIeM7fmSMb4x9ZZUM";
    		String json = "";
    		JSONObject jsonObject = null;
    		GooglePlaceList gPlaces= null;
    		try {
    			url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
    					"location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor="+
    					"false&key="+apiKey;
    			
    			DefaultHttpClient defaultClient = new DefaultHttpClient();
    			HttpGet getRequest = new HttpGet(url);
    			
    			HttpResponse httpResponse = defaultClient.execute(getRequest);
    			
    			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
    			String line;
    			while((line = reader.readLine())!=null) {
    		    	json += (line+"\n");
    		    }
    		    reader.close();
    			
    		}
    		//catch some error
    		catch(MalformedURLException ex) {  
    		}
    		catch(SocketTimeoutException ex) {
    		}
    		// and some more
    		catch(IOException ex) {
    		}
    		
    		 // Instantiate a JSON object from the request response
		    try {
				jsonObject = new JSONObject(json);
				gPlaces = new GooglePlaceList(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		    
		    return gPlaces;
    	}
    }
}
