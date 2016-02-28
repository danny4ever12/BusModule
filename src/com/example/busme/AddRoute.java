package com.example.busme;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRoute extends Activity{
	
	
	EditText sourceET,destinationET;	
	String sourcestr,destinationstr;
	SharedPreferences myshare;	
	Editor myedit;
	String vehicle_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.addroutes);			
		sourceET=(EditText) findViewById(R.id.sourceedit);
		destinationET=(EditText) findViewById(R.id.destinationedit);		
		myshare=PreferenceManager.getDefaultSharedPreferences(AddRoute.this);		
		vehicle_id=myshare.getString("vehicle_id", "");
		Log.e("id", vehicle_id);
		myedit=myshare.edit();
	}	
	public void backTo(View v)
	{
		Intent backto=new Intent(AddRoute.this, Home.class);
		startActivity(backto);
	}
	public void onSubmitClick(View v)
	{
		
		sourcestr=sourceET.getText().toString();
		destinationstr=destinationET.getText().toString();
		
		if(sourcestr.equals("")&&destinationstr.equals(""))
		{
			Toast.makeText(AddRoute.this, "Enter Source and DEstination", 5000).show();
		}
		else
		{
		try
		{
		WebServer loginweb=new WebServer();
		Log.e("inside", "try");
		List<NameValuePair> loginlist = new ArrayList<NameValuePair>(3);
		loginlist.add(new BasicNameValuePair("source", sourcestr));
		loginlist.add(new BasicNameValuePair("destination", destinationstr));
		loginlist.add(new BasicNameValuePair("vehicle_id", vehicle_id));

	JSONArray ja = loginweb
			.doPost(loginlist,
					"http://"+myshare.getString("ipaddress", "")+"/Walkie_Talkie/addroutes.jsp");
//		JSONArray ja=loginweb.doPost(loginlist, "http://10.0.2.2:8080/WalkieTalkie/addroutes.jsp");
	
		if(ja!=null)
		{
		JSONObject jo = (JSONObject) ja.get(0);
		
		if(jo.getString("resp").equals("success"))
		{
			myedit.putString("source", sourcestr);
			myedit.putString("destination", destinationstr);
			myedit.commit();	
			Toast.makeText(AddRoute.this, "Success", 5000).show();
//			finish();
		}
		
		}
		else
		{
			
			Toast.makeText(AddRoute.this, "Connection Error",
					Toast.LENGTH_SHORT).show();
		}
		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
	}

}
