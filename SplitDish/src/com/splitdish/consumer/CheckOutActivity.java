package com.splitdish.consumer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CheckOutActivity extends Activity {

	private static final String SELECTED_ITEMS = "com.splitdish.consumer.SELECTED_ITEMS";
	private static final double taxRate = 8.5/100.0;
	private TicketItemList selectedItems;
	private TicketAdapter adapter;
	private ListView listView1;
	private int subtotal = 0;
	private int taxtotal = 0;
	private int tiptotal = 0;
	private int total = 0;
	private Context activity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_out);

		initializeTips();
		
		Intent intent = getIntent();
		activity = this;
		
		selectedItems = intent.getParcelableExtra(SELECTED_ITEMS);
		for(int i=0;i<selectedItems.size();i++) {
			selectedItems.get(i).selected=false;
			subtotal += selectedItems.get(i).price;
		}
		taxtotal = ((Double)(subtotal * taxRate)).intValue(); 
		total = subtotal + taxtotal + tiptotal;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_check_out, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		
		adapter = new TicketAdapter(this, R.layout.ticket_item_row, selectedItems);
		
		listView1 = (ListView)findViewById(R.id.itemRecapListView);
		
		listView1.setAdapter(adapter);
		
	    listView1.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			    
			           	
            }
	    });
	    
	    TextView subtotalView = (TextView)findViewById(R.id.subtotalNum);
	    subtotalView.setText("$"+String.format("%.2f", subtotal/100.0));
	    
	    TextView taxView = (TextView)findViewById(R.id.taxNum);
	    taxView.setText("$"+String.format("%.2f", taxtotal/100.0));
	    
	    TextView totalView = (TextView)findViewById(R.id.totalText);
	    totalView.setText("Total: $"+String.format("%.2f", total/100.0));

	}
	
	private void initializeTips() {
		final EditText percTipText = (EditText) findViewById(R.id.tipPercentNum);
		final EditText totalTipText = (EditText) findViewById(R.id.tipTotalNum);
		
		final InputMethodManager inputManager = (InputMethodManager)
        		this.getSystemService(Context.INPUT_METHOD_SERVICE);
		
		percTipText.setOnEditorActionListener(new OnEditorActionListener() {
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {

		        	String ptip = percTipText.getText().toString().replace("%","");
		        	
                    tiptotal = ((Double)(subtotal * 
                    		Double.parseDouble(ptip) / 100.0))
                    		.intValue()+1;
                    totalTipText.setText(String.format("$%.2f", tiptotal/100.0));
                    percTipText.setText(ptip+"%");
            		inputManager.toggleSoftInput(0, 0);
                    refreshTotal();
                    
                    return true;
                }
                return false;
            }
        });
		totalTipText.setOnEditorActionListener(new OnEditorActionListener() {
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {

		        	String ttip = totalTipText.getText().toString().replace("$","");
		        	
                    tiptotal = ((Double)Double.parseDouble(ttip)).intValue()*100;
                    percTipText.setText(String.format("%.2f", tiptotal/(double)subtotal*100.0)+"%");
                    totalTipText.setText("$"+ttip);
                    refreshTotal();
            		inputManager.toggleSoftInput(0, 0);
                    return true;
                }
                return false;
            }
        });
		percTipText.addTextChangedListener(new TextWatcher(){
	        @Override
			public void afterTextChanged(Editable s) {
	        	
	        }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(((Activity)activity).getCurrentFocus() == percTipText) {
					String ptip = percTipText.getText().toString().replace("%","");
		        	try {
		                tiptotal = ((Double)(subtotal * 
		                		Double.parseDouble(ptip) / 100.0))
		                		.intValue()+1;
		        	} catch(NumberFormatException e) {
		        		tiptotal = 0;
		        	}
	                totalTipText.setText(String.format("$%.2f", tiptotal/100.0));
	                refreshTotal();
				}
			}
		});
		totalTipText.addTextChangedListener(new TextWatcher(){
	        @Override
			public void afterTextChanged(Editable s) {
	        	
	        }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(((Activity)activity).getCurrentFocus() == totalTipText) {
					String ttip = totalTipText.getText().toString().replace("$","");
		        	try {
		        		tiptotal = ((Double)Double.parseDouble(ttip)).intValue()*100;
		        	} catch(NumberFormatException e) {
		        		tiptotal = 0;
		        	}
	                percTipText.setText(String.format("%.2f", tiptotal/(double)subtotal*100.0)+"%");
	                refreshTotal();
				}
			}
		});
	}
	
	private void refreshTotal() {
		total = subtotal + taxtotal + tiptotal;
		
		TextView totalView = (TextView)findViewById(R.id.totalText);
	    totalView.setText("Total: $"+String.format("%.2f", total/100.0));
	}

}
