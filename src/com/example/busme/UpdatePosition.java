package com.example.busme;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.busme.Login.mylocationlistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class UpdatePosition  extends BroadcastReceiver {
	

	SharedPreferences myshare;
	
	double lat,lon;
	
	String vehicle_id="";
	
	
	

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Login l=new Login();
		mylocationlistener ml=l.new mylocationlistener();
		
		myshare=PreferenceManager.getDefaultSharedPreferences(context);
		
		vehicle_id=myshare.getString("vehicle_id", vehicle_id);
	
		
		lat=ml.lat;
		lon=ml.lon;
				
		try
		{
		
		WebServer web=new WebServer();
		
		
		List<NameValuePair> loginlist = new ArrayList<NameValuePair>(3);
		loginlist.add(new BasicNameValuePair("latitude", "" + lat));
		loginlist.add(new BasicNameValuePair("longitude", "" + lon));
		loginlist.add(new BasicNameValuePair("vehicle_id", vehicle_id));
		Log.e("lat","hh"+lat);
		Log.e("lon", "dd"+lon);
		Log.e("id", "ddd"+vehicle_id);

		JSONArray ja = web.doPost(loginlist, "http://" + myshare.getString("ipaddress", "")
				+ "/Walkie_Talkie/updatevehicleposition.jsp");
//		JSONArray ja = web.doPost(loginlist, "http://192.168.1.5:8084/Walkie_Talkie/updatevehicleposition.jsp");
		
		if(ja!=null)
    		{
		
 	    JSONObject jo = (JSONObject) ja.get(0);

		String msg = jo.getString("resp");
		
		if (msg.equals("success")) {

		}
		}
		else
		{
			
			Toast.makeText(context, "Connection Error",
					Toast.LENGTH_SHORT).show();
		}
		
		}
		catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		
		
		
		
	}

}

