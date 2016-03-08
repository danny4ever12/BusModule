package com.example.busme;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class Login extends Activity {
	String s;
	 
	  Geocoder gcd;
	
	
	//for json connect
	JSONParser jsonParser = new JSONParser();
	
	
    // Progress Dialog
	private ProgressDialog pDialog;
	
	// url to create new product
	//private static String url_create_product = "http://192.168.1.103/walkie/driver.php";
    // JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_VEHICLE_ID = "vehicleId";
	private static final String TAG_EMAIL = "email";
		

	EditText unameEt, passwordEt, regnoET;
	String username, password, regNo;
	ImageView passimg;
	String username_pref;
		
		protected LocationManager locationManager;
		
		public static Location location;
		private static final String TAG = "Debug";
		
	
		SharedPreferences myshare;
		Editor myedit;
		String LocalityName;
		
		
		
	
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);
	
			unameEt = (EditText) findViewById(R.id.usernameedit);
			passwordEt = (EditText) findViewById(R.id.passwordedit);
			regnoET = (EditText) findViewById(R.id.regnoedit);
			Button ipbtn=(Button) findViewById(R.id.IPsett);
			Button lgbn=(Button)findViewById(R.id.login_btn);
			
			myshare=PreferenceManager.getDefaultSharedPreferences(Login.this);
			
			myedit=myshare.edit();
			
			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        LocationListener ll = new mylocationlistener();
	        
	        
	      lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,30000, 10, ll);
			
			lgbn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onLoginClick();
				}
			});
	        
	     
		  
			
			ipbtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openOptionsMenu();
				  
					
					
				}
			});
			
		
		
		}
		
	/**
		//----Method to Check GPS is enable or disable ----- 
		 private Boolean displayGpsStatus() {
		  ContentResolver contentResolver = getBaseContext()
		  .getContentResolver();
		  boolean gpsStatus = Settings.Secure
		  .isLocationProviderEnabled(contentResolver, 
		  LocationManager.GPS_PROVIDER);
		  if (gpsStatus) {
		   return true;
	
		  } else {
		   return false;
		  }
		 }
		**/
		 /*----------Method to create an AlertBox ------------- */
		 protected void alertbox(String title, String mymessage) {
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setMessage("Your Device's GPS is Disable")
		  .setCancelable(false)
		  .setTitle("** Gps Status **")
		  .setPositiveButton("Gps On",
		   new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
		   // finish the current activity
		   // AlertBoxAdvance.this.finish();
		   Intent myIntent = new Intent(
		   Settings.ACTION_SECURITY_SETTINGS);
		   startActivity(myIntent);
		      dialog.cancel();
		   }
		   })
		   .setNegativeButton("Cancel",
		   new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
		    // cancel the dialog box
		    dialog.cancel();
		    }
		   });
		  AlertDialog alert = builder.create();
		  alert.show();
		 }
		 
		 
		 public class mylocationlistener implements LocationListener {
			 double lat,lon;
			 String cityName;
			 
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
				        
				      //shared preferences
				        String latshare,longshare;
				        latshare=String.valueOf(location.getLatitude());
				        longshare=String.valueOf(location.getLongitude());
						myedit.putString("latitude", latshare);
						myedit.putString("longitude", longshare);
						myedit.commit();
	//			        
				        /*----------to get City-Name from coordinates ------------- */
						cityName=null; 
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
				            LocalityName = addresses.get(0).getAddressLine(0);
			            subloc=addresses.get(0).getSubLocality();
	
			            myedit.putString("current_location", cityName);
						myedit.commit();
				          } catch (IOException e) {            
				          e.printStackTrace();  
				        } 
				            
				         s = longitude+"\n"+latitude +
			     "\n\nMy Currrent City is: "+cityName+"\nLocalityName:"+LocalityName+"\nSubLoc:"+subloc;
			        Toast.makeText(getApplicationContext(),"k"+s,Toast.LENGTH_SHORT).show();

		     
			         }

			        
				SharedPreferences pref=getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
				Editor editor = pref.edit();
				 editor.putString("loc_name", LocalityName);
				 editor.commit();
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
    	// TODO Auto-generated method stub
    	menu.add("Set IP");
		return true;
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		String title=item.getTitle().toString();
		if(title.equals("Set IP"))
		{
			final Dialog dialog = new Dialog(this);
			dialog.setTitle("Specify IP");
			dialog.setContentView(R.layout.ipdialog);
			dialog.show();
			Button b1 = (Button) dialog.findViewById(R.id.ipsubmitButton);
			Button b2 = (Button) dialog.findViewById(R.id.ipcancelButton);
			final EditText ipEdit = (EditText) dialog.findViewById(R.id.ipaddressET);
			b1.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					String ipaddrress=ipEdit.getText().toString();
					//ipaddrress=ipaddrress;
					myedit.putString("ipaddress", ipaddrress);
					myedit.commit();
					dialog.dismiss();
				}
			});
			b2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
		}
		return true;
	}

	public void onLoginClick()
	{
		
		
		
		 username=unameEt.getText().toString();
		 password=passwordEt.getText().toString();
		 regNo=regnoET.getText().toString();
		
		
			if(username.equals(""))
			{
				Toast.makeText(Login.this, "Enter username", Toast.LENGTH_LONG).show();
			}
			else if(password.equals(""))
			{
				Toast.makeText(Login.this, "Enter password", Toast.LENGTH_LONG).show();	
			}
			else if(regNo.equals(""))
			{
				Toast.makeText(Login.this, "Enter Registration no", Toast.LENGTH_LONG).show();	
			}
			else
			{
			
			// creating new product in background thread
			// TODO Auto-generated method stub
				new CreateNewProduct().execute();
			
			}
				
	}
		
	
	
	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Authenticating...!");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
	
		protected String doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("regNo", regNo));
         
			// getting JSON Object
			// Note that create product url accepts POST method
			String url_check="http://"+myshare.getString("ipaddress", "")+"/walkie/driver.php";
			JSONObject json = jsonParser.makeHttpRequest(url_check,
					"GET", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());
			
			
			try {
				// check for success tag
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.d("ivide ethi","breakpoint");
					
					// successfully received product details
					JSONArray productObj = json
							.getJSONArray(TAG_PRODUCT); // JSON Array
					
					// get first product object from JSON Array
					JSONObject product = productObj.getJSONObject(0);
					String vehicle=product.getString(TAG_VEHICLE_ID);
					String email_id=product.getString(TAG_EMAIL);
					
					//shared preferences
					myedit.putString("vehicle_id", vehicle);
					myedit.putString("email", email_id);
					myedit.commit();
					
					Log.e("vehicleid", ""+vehicle+""+email_id+"");
														
					// successfully authenticated
					 Handler handler =  new Handler(getApplicationContext().getMainLooper());
					    handler.post( new Runnable(){
					        public void run(){
					            Toast.makeText(getApplicationContext(),"logged in successfully" ,Toast.LENGTH_LONG).show(); 
					        }
					    });
					
					Intent i = new Intent(getApplicationContext(), AddRoute.class);
					startActivity(i);
					
					// closing this screen
					finish();
				} else {
					// failed to authenticate
					 Handler handler =  new Handler(getApplicationContext().getMainLooper());
					    handler.post( new Runnable(){
					        public void run(){
					            Toast.makeText(getApplicationContext(), "invalid credentials",Toast.LENGTH_LONG).show(); 
					        }
					    });
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
				
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

