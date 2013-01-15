package com.splitdish.consumer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreenActivity extends Activity {

	public final static String USERNAME_TEXT = "com.splitdish.consumer.USERNAME_TEXT";
	public final static String AUTH_TOKEN = "com.splitdish.consumer.AUTH_TOKEN";
	public String username;
	public String password;
	public String keepSignedIn;
	public ProgressDialog loginDialog;
	public String auth_token;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipCheckToken();
        setContentView(R.layout.activity_login_screen);
        doneKeyLogin(); //Done key from password EditText begins login
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login_screen, menu);
        return true;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	EditText passwordText = (EditText) findViewById(R.id.password_text);
    	passwordText.setText("");
    }
    
    public void sendCredentials(View v) {
    	
    	EditText usernameText = (EditText) findViewById(R.id.username_text);
    	username = usernameText.getText().toString();
    	EditText passwordText = (EditText) findViewById(R.id.password_text);
    	password = passwordText.getText().toString();
    	CheckBox stayLogged = (CheckBox) findViewById(R.id.stay_signed_in_check);
    	if(stayLogged.isChecked())
    		keepSignedIn = Boolean.valueOf(true).toString(); 
    	else
    		keepSignedIn = Boolean.valueOf(false).toString();
    	new LoginTask().execute(username,password,keepSignedIn);
    	
    }
    
    public void signUp(View v) {
    	Intent intent = new Intent(this, SignUpActivity.class);
    	EditText editText = (EditText) findViewById(R.id.username_text);
    	String username = editText.getText().toString();
    	intent.putExtra(USERNAME_TEXT, username);
    	startActivity(intent);
    }
    private class LoginTask extends AsyncTask<String,String,Boolean> {
    	class LoginInfo {
    		String info;
    		boolean success;
    		public LoginInfo(boolean success, String info) {
    			this.success = success;
    			this.info = info;
    		}
    	}
    	
    	@Override
    	public void onPreExecute() {
    		startProgress();
    	}
    	
    	@Override
    	public Boolean doInBackground(String... params) {
    		
    		LoginInfo result = remoteLogin(params[0],params[1]);
    		// If stay checked in box is selected
    		if(result.success) 
    			saveToken(result,params[2] == Boolean.toString(true));
    		if(!result.success)
    			publishProgress(result.info);
    		return result.success;
    	}
    	
    	@Override
    	public void onProgressUpdate(String... values) {
    		Toast t = Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG);
    		t.show();
    	}
    	
    	@Override
    	public void onPostExecute(Boolean result) {
    		completeLogin(result);
    	}
    	
    	private LoginInfo remoteLogin(String username, String password) {
    		URL url;
    		HttpURLConnection conn;
    		
    		try {
    			url = new URL("http://www.splitdish.com:3000/tokens.json");
    			String params = "email="+URLEncoder.encode(username, "UTF-8")
    					+"&password=" + URLEncoder.encode(password,"UTF-8");
    			
    			conn= (HttpURLConnection)url.openConnection();
    			conn.setDoOutput(true);

    			conn.setRequestMethod("POST");
    			conn.setFixedLengthStreamingMode(params.getBytes().length);
    			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    			conn.setConnectTimeout(3500);
    			//send the POST out
    			PrintWriter out = new PrintWriter(conn.getOutputStream());
    			out.print(params);
    			out.close();

    			//build the string to store the response text from the server
    			String response= "";
    			
    			//start listening to the stream
    			Scanner inStream = new Scanner(conn.getInputStream());
    			
    			//process the stream and store it in StringBuilder
    			while(inStream.hasNextLine())
    			response+=(inStream.nextLine());
    			return new LoginInfo(true,response);
    		}
    		//catch some error
    		catch(MalformedURLException ex) {  
    			return new LoginInfo(false,"MalformedURL");
    		}
    		catch(SocketTimeoutException ex) {
    			return new LoginInfo(false,"Unable to Connect");
    		}
    		// and some more
    		catch(IOException ex) {
    			return new LoginInfo(false,"Invalid Username/Password");
    		}
    	}
    	
    	private int saveToken(LoginInfo result, boolean persist) {
    		try {
	    		JSONObject jObject = new JSONObject(result.info);
	    		auth_token = jObject.getString("token");
    		}
    		catch(Exception e) {
    			return -1;
    		}
    		if(persist) {
	    		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    		prefs.edit().putString("authentication_token", auth_token).commit();
    		}
    		return 0;
    	}
    }
	private void startProgress() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("Logging in...");
    	loginDialog.show();
		
	}

	public void completeLogin(Boolean result) {
		loginDialog.dismiss();

		if(result==true) {
			Intent intent = new Intent(this, HomeActivity.class);
			intent.putExtra(AUTH_TOKEN, auth_token);
	    	startActivity(intent);
	    	finish();
		}
		else {
			EditText passwordText = (EditText) findViewById(R.id.password_text);
			passwordText.setText("");
		}
		
	}
	
	public void forgotPassword(View v) {
		//TODO Implement forgotten password methods
		Context context = getApplicationContext();
		CharSequence text = "Password Recovery Not Yet Implemented";
		int duration = Toast.LENGTH_SHORT;
		
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	private void doneKeyLogin() {
		EditText passwordText = (EditText) findViewById(R.id.password_text);
        passwordText.setOnKeyListener(new OnKeyListener() {
        	public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            sendCredentials(null);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
	}
	private void skipCheckToken() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
	private void checkToken() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        auth_token = prefs.getString("authentication_token", null);
        if(auth_token != null) {
        	Intent intent = new Intent(this, HomeActivity.class);
        	intent.putExtra(AUTH_TOKEN, auth_token);
	    	startActivity(intent);
	    	finish();
        }
	}
}


