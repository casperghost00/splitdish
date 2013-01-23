package com.splitdish.consumer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ChooseVenueActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_venue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choose_venue, menu);
		return true;
	}

}
