package com.example.busme;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class AddRoute extends Activity{
	
	
	EditText sourceET,destinationET;	
	String sourcestr,destinationstr;
	SharedPreferences myshare;	
	Editor myedit;
	String vehicleId,email,latitude,longitude,current_location;
	
	//for json connect
	JSONParser jsonParser = new JSONParser();
	
	private static final String TAG_SUCCESS = "success";
	
	  // Progress Dialog
		private ProgressDialog pDialog;
		
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.addroutes);			
		sourceET=(EditText) findViewById(R.id.sourceedit);
		destinationET=(EditText) findViewById(R.id.destinationedit);		
		myshare=PreferenceManager.getDefaultSharedPreferences(AddRoute.this);		
		
		vehicleId=myshare.getString("vehicle_id", "");
		email=myshare.getString("email", "");
		latitude=myshare.getString("latitude", "");
		longitude=myshare.getString("longitude", "");
		
		current_location=myshare.getString("current_location", "");
		TextView tv=(TextView)findViewById(R.id.tvEmail);
		tv.setText(email);
		Log.e("id", vehicleId);
		
		myedit=myshare.edit();
		Button sub=(Button)findViewById(R.id.submitbtn);
		Button bck=(Button)findViewById(R.id.backBtn);
		
		
		
		sub.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    onSubmitClick();	
			}
		});
		
		bck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			backTo();	
			}
		});
	}	
	public void backTo()
	{
		Intent backto=new Intent(AddRoute.this, Home.class);
		startActivity(backto);
	}
	public void onSubmitClick()
	{
		
		sourcestr=sourceET.getText().toString();
		destinationstr=destinationET.getText().toString();
		
		if(sourcestr.equals("")&&destinationstr.equals(""))
		{
			Toast.makeText(AddRoute.this, "Enter Source and DEstination",Toast.LENGTH_LONG).show();
		}
		else
		{
		 //starting asynctask	
		 new datadriver().execute();
		
		}
	}

	
	
	/**
	 * Background Async Task to Create new product
	 * */
	class datadriver extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddRoute.this);
			pDialog.setMessage("Updating bus database...!");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creeating product
		 * */
	
		protected String doInBackground(String... args) {
			
		
			Log.e("BACK", "GROUND");
			List<NameValuePair> par = new ArrayList<NameValuePair>();
			par.add(new BasicNameValuePair("vehicleId", vehicleId));
			par.add(new BasicNameValuePair("source", sourcestr));
			par.add(new BasicNameValuePair("destination", destinationstr));
			par.add(new BasicNameValuePair("current_location", current_location));
			par.add(new BasicNameValuePair("latitude", latitude));
			par.add(new BasicNameValuePair("longitude", longitude));
			
			
			// getting JSON Object
			// Note that create product url accepts POST method
			String url_check="http://"+myshare.getString("ipaddress", "")+"/walkie/bus_location.php";
			JSONObject js = jsonParser.makeHttpRequest(url_check,
								"POST", par);
						
						// check log cat fro response
						Log.d("Create Response", js.toString());

						try {
							// check for success tag
							int success = js.getInt(TAG_SUCCESS);

							if (success == 1) {
								//Log.d("ivide ethi","breakpoint");
								
								//for toast
								 Handler handler =  new Handler(getApplicationContext().getMainLooper());
								    handler.post( new Runnable(){
								        public void run(){
								            Toast.makeText(getApplicationContext(), "routes updated",Toast.LENGTH_LONG).show(); 
								        }
								    });
								
								//Toast.makeText(getApplicationContext(),"database updated" ,Toast.LENGTH_LONG).show(); 
								Log.d("ODUKKAM", "KERI");						
								
								Intent i = new Intent(getApplicationContext(), RunningBus.class);
								startActivity(i);
								
								// closing this screen
								finish();
								
							} else {
								Log.d("kittilaaa", "ffffffffffffff");
								
								 //for toast
								 Handler handler =  new Handler(getApplicationContext().getMainLooper());
								    handler.post( new Runnable(){
								        public void run(){
								            Toast.makeText(getApplicationContext(), "unable to update routes",Toast.LENGTH_LONG).show(); 
								        }
								    });
								
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Log.d("error adichh", "kili poi");
						}
			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}
}
