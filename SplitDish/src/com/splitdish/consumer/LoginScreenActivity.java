package com.splitdish.consumer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreenActivity extends Activity {

	public final static String USERNAME_TEXT = "com.splitdish.consumer.USERNAME_TEXT";
	public String username;
	public String password;
	public ProgressDialog loginDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    	new LoginTask().execute(username,password);
    	
    }
    
    public void signUp(View v) {
    	Intent intent = new Intent(this, SignUpActivity.class);
    	EditText editText = (EditText) findViewById(R.id.username_text);
    	String username = editText.getText().toString();
    	intent.putExtra(USERNAME_TEXT, username);
    	startActivity(intent);
    }
    private class LoginTask extends AsyncTask<String,String,Boolean> {
    	class loginInfo {
    		String info;
    		boolean success;
    		public loginInfo(boolean success, String info) {
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
    		/*try {
    			Thread.sleep(2000);
    		}
    		catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		return checkCredentials(params[0],params[1]);
    		*/
    		loginInfo result = remoteLogin(params[0],params[1]);
    		if(result.success == false)
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
    	
    	public loginInfo remoteLogin(String username, String password) {
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
    			return new loginInfo(true,response);
    		}
    		//catch some error
    		catch(MalformedURLException ex) {  
    			return new loginInfo(false,"MalformedURL");
    		}
    		catch(SocketTimeoutException ex) {
    			return new loginInfo(false,"Unable to Connect");
    		}
    		// and some more
    		catch(IOException ex) {
    			return new loginInfo(false,"Invalid Username/Password");
    		}
    	}
    }
	public void startProgress() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("Logging in...");
    	loginDialog.show();
		
	}

	public void completeLogin(Boolean result) {
		loginDialog.dismiss();

		if(result==true) {
			Intent intent = new Intent(this, HomeActivity.class);
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
	
	
}


