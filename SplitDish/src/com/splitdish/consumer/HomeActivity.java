package com.splitdish.consumer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;


public class HomeActivity extends Activity {
	
	String auth_token = "";
	String username = "";
	ProgressDialog logoutDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth_token = getIntent().getStringExtra(LoginScreenActivity.AUTH_TOKEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home, menu); //inflate our menu
        
     // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        searchView.setQueryHint("Search Restaurants");
        
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_logout:
            	new LogoutTask().execute();
            	return true;
            case R.id.menu_settings:
            	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void tableSearch(View v) {
    	Context context = getApplicationContext();
		CharSequence text = "Find Your Table.";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
    }
    private class LogoutTask extends AsyncTask<String,String,Integer> {
    	    	
    	@Override
    	public void onPreExecute() {
    		startProgress();
    	}
    	
    	@Override
    	public Integer doInBackground(String... params) {
    		int result = destroyToken();
    		return result;
    	}
    	
    	@Override
    	public void onProgressUpdate(String... values) {}
    	
    	@Override
    	public void onPostExecute(Integer result) {
    		completeLogout(result);
    	}
    	
    	
    }
    
    private void startProgress() {
		logoutDialog = new ProgressDialog(this);
		logoutDialog.setMessage("Logging out...");
    	logoutDialog.show();
	}
    
    private void completeLogout(int result) {
    	Intent intent = new Intent(this, LoginScreenActivity.class);
    	Toast toast = Toast.makeText(getApplicationContext(), Integer.valueOf(result).toString(), Toast.LENGTH_SHORT);
		toast.show();
    	startActivity(intent);
    	finish();
    }
    
    private int destroyToken() {
    	URL url;
		HttpURLConnection conn;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		prefs.edit().remove("authentication_token").commit();
		
		try {
			url = new URL("http://www.splitdish.com:3000/tokens/"+ auth_token +".json");
			
			conn= (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setConnectTimeout(3500);
			
			//build the string to store the response text from the server
			String response= "";
			
			//start listening to the stream
			Scanner inStream = new Scanner(conn.getInputStream());
			
			//process the stream and store it in StringBuilder
			while(inStream.hasNextLine())
				response+=(inStream.nextLine());
			
			
			if(response=="")
				return -2;
			return 0;
		}
		//catch some error
		catch(MalformedURLException ex) {  
			return -1;
		}
		catch(SocketTimeoutException ex) {
			return 1;
		}
		// and some more
		catch(IOException ex) {
			return 2;
		}
    }
    public void startSplitting(View v) {
    	Intent intent = new Intent(this, LeadOrJoinActivity.class);
    	startActivity(intent);
    }
    
    public void scanReceipt(View v) {
    	Intent intent = new Intent(this, GetTicketFromScanActivity.class);
    	startActivity(intent);
    }
    
    public void orderAhead(View v) {
    	notYetImplemented();
    }
    
    public void createParty(View v) {
    	notYetImplemented();
    }
    
    public void viewDishCard(View v) {
    	notYetImplemented();
    }
    
    private void notYetImplemented() {
    	Context context = getApplicationContext();
		CharSequence text = "Feature Not Yet Implemented";
		int duration = Toast.LENGTH_SHORT;
		
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
    }
    

}
