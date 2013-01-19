package com.splitdish.consumer;

import net.sourceforge.zbar.camera.ScanQRCodeActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class LeadOrJoinActivity extends Activity {
	
	public final static String QR_DATA = "com.splitdish.consumer.QR_DATA";
	static final int SCAN_QR_CODE = 0;

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
		Intent intent = new Intent(this, ScanQRCodeActivity.class);
		startActivityForResult(intent, SCAN_QR_CODE);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == SCAN_QR_CODE) {
	      if (resultCode == RESULT_OK) {
	         String contents = intent.getStringExtra(QR_DATA);
	         Context context = getApplicationContext();
 	 		 CharSequence text = contents;
	 		 int duration = Toast.LENGTH_SHORT;
	 		
	 		 Toast toast = Toast.makeText(context, text, duration);
	 		 toast.show();
	      } else if (resultCode == RESULT_CANCELED) {
	         // Handle cancel
	      }
	   }
	}

}
