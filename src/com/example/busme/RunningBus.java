package com.example.busme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.busme.Login.mylocationlistener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class RunningBus extends Activity {

	
	
	
SharedPreferences myshare;
Geocoder gcd;

//protected LocationManager locationManager;

	String lat="",lon="",vehicleId="",cityName="";
	  
	
	public static Location location;
	private static final String TAG = "Debug";
	
	
	 private static final String TAG_SUCCESS = "success";
	
	 TextView tv1,tv2,tv3;
	//for json connect
		JSONParser jsonParser = new JSONParser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_running_bus);
		
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		
		myshare=PreferenceManager.getDefaultSharedPreferences(RunningBus.this);
		
		vehicleId=myshare.getString("vehicle_id", "");
		lat=myshare.getString("latitude", "");
		lon=myshare.getString("longitude", "");
		
		cityName=myshare.getString("current_location", "");
		
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv3=(TextView)findViewById(R.id.textView3);
		

        
        //tv1.setText("latitude:"+lat+"");
        //tv2.setText("longitude"+lon+"");
        //tv3.setText("current location: "+cityName+"");
		
		
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        
        
      lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,6000, 1, ll);	
		
	
	}

	
	 public class mylocationlistener implements LocationListener {
	
		 
			public void onLocationChanged(Location location) {
				Log.e("innnnnnnnnn", "innnnnn");
				// TODO Auto-generated method stub
				 
				if (location != null) {
					
					Toast.makeText(getBaseContext(),"Location changed : Lat: " +
							   location.getLatitude()+ " Lng: " + location.getLongitude(),
							   Toast.LENGTH_SHORT).show();
							            String longitude = "Longitude: " +location.getLongitude();  
							      Log.v(TAG, longitude);
							      String latitude = "Latitude: " +location.getLatitude();
							      Log.v(TAG, latitude);
			        Log.d("LOCATION CHANGED", location.getLatitude() + "");
			        Log.d("LOCATION CHANGED", location.getLongitude() + "");
//			      
			        
			  
			        lat=String.valueOf(location.getLatitude());
			        lon=String.valueOf(location.getLongitude());
				   
			        
			        
			        tv1.setText("latitude:"+lat+"");
			        tv2.setText("longitude"+lon+"");
			        
//			        
			        //----------to get City-Name from coordinates -------------// 
		
			        String LocalityName=null;
			        String subloc=null;
			        gcd = new Geocoder(getBaseContext(), Locale.ENGLISH);             
			          
			        try { 
			        	List<Address>  addresses;
			        	Log.e("inside try","Ddd");
			        addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);  
			        if (addresses.size() > 0)  
			           System.out.println(addresses.get(0).getLocality());  
			           cityName=addresses.get(0).getLocality();  
			          // cityName="keep";
			           tv3.setText("current location: "+cityName+"");
			           LocalityName = addresses.get(0).getAddressLine(0);
		               subloc=addresses.get(0).getSubLocality();
		            
		           
		           
			          } catch (IOException e) {            
			          e.printStackTrace();  
			        } 
			        
			        //vehicleId="kl123";
			    	List<NameValuePair> par = new ArrayList<NameValuePair>();
					par.add(new BasicNameValuePair("vehicleId", vehicleId));
					par.add(new BasicNameValuePair("current_location", cityName));
					par.add(new BasicNameValuePair("latitude", lat));
					par.add(new BasicNameValuePair("longitude", lon));
					
					
					// getting JSON Object
					// Note that create product url accepts POST method
					String url_check="http://"+myshare.getString("ipaddress", "")+"/walkie/updateposition.php";
					JSONObject js = jsonParser.makeHttpRequest(url_check,
										"POST", par);
								
								// check log cat fro response
								Log.d("Create Response", js.toString());

								try {
									// check for success tag
									int success = js.getInt(TAG_SUCCESS);

									if (success == 1) {
										//Log.d("ivide ethi","breakpoint");
										
										
										Toast.makeText(getApplicationContext(),"database  updated" ,Toast.LENGTH_LONG).show(); 
								        
										
										Log.d("updated", "KERI");						
										
										
									} else {
										Log.d("kittilaaa", "error in php");
										
										 //for toast
										
										Toast.makeText(getApplicationContext(),"database not updated" ,Toast.LENGTH_LONG).show(); 
										        
										    
										
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Log.d("update error", "kili poi");
								}		
	     
		    
		        
		        
		         }

		        
		
		}
		

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
        
      }
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.running_bus, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
}
