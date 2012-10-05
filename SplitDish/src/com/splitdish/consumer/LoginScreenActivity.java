package com.splitdish.consumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginScreenActivity extends Activity {

	public final static String USERNAME_TEXT = "com.splitdish.consumer.USERNAME_TEXT";
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
    
    public void sendCredentials(View v) {
    	
    }
    
    public void signUp(View v) {
    	Intent intent = new Intent(this, SignUpActivity.class);
    	EditText editText = (EditText) findViewById(R.id.username_text);
    	String username = editText.getText().toString();
    	intent.putExtra(USERNAME_TEXT, username);
    	startActivity(intent);
    }
}
