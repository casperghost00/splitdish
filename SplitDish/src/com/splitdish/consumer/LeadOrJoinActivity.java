package com.splitdish.consumer;

import net.sourceforge.zbar.camera.CameraTestActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class LeadOrJoinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lead_or_join);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lead_or_join, menu);
		return true;
	}
	
	public void qrScan(View v) {
		Intent intent = new Intent(this, CameraTestActivity.class);
		startActivityForResult(intent, 0);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == 0) {
	      if (resultCode == RESULT_OK) {
	         String contents = intent.getStringExtra("SCAN_RESULT");
	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         // Handle successful scan
	      } else if (resultCode == RESULT_CANCELED) {
	         // Handle cancel
	      }
	   }
	}

}
