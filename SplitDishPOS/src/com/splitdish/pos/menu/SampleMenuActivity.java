package com.splitdish.pos.menu;

import com.splitdish.pos.R;
import com.splitdish.pos.R.layout;
import com.splitdish.pos.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SampleMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_menu_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample_menu_main, menu);
		return true;
	}

}
