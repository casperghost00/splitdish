package com.splitdish.consumer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
    private class LoginTask extends AsyncTask<String,Integer,Boolean> {
    	@Override
    	public void onPreExecute() {
    		startProgress();
    	}
    	
    	@Override
    	public Boolean doInBackground(String... params) {
    		try {
    			Thread.sleep(2000);
    		}
    		catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		return checkCredentials(params[0],params[1]);
    	}
    	
    	@Override
    	public void onProgressUpdate(Integer... values) {
    		
    	}
    	
    	@Override
    	public void onPostExecute(Boolean result) {
    		completeLogin(result);
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
			Intent intent = new Intent(this, SignUpActivity.class);
	    	EditText usernameText = (EditText) findViewById(R.id.username_text);
	    	String username = usernameText.getText().toString();
	    	intent.putExtra(USERNAME_TEXT, username);
	    	startActivity(intent);
		}
		else {
			Context context = getApplicationContext();
			CharSequence text = "Invalid Username/Password";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			EditText passwordText = (EditText) findViewById(R.id.password_text);
			passwordText.setText("");
		}
		
	}

	public Boolean checkCredentials(String username, String password) {
		
		Boolean correct_creds = false;
		
		if(username.compareTo("dmc")==0) {
			if(password.compareTo("123")==0) {
				correct_creds = true;
			}
		}
		return correct_creds;
	}
}


