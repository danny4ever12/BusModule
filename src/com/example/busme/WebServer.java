package com.example.busme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.StrictMode;
import android.util.Log;

public class WebServer {
	
	public JSONArray doPost(List<NameValuePair> namevaluepair, String url) {
		JSONArray myjsonarray = null;

		


		    if (android.os.Build.VERSION.SDK_INT > 9) {
		    	
		        StrictMode.ThreadPolicy policy = 
		        new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		            StrictMode.setThreadPolicy(policy);
		     }



			



			HttpClient myclient = new DefaultHttpClient();
			HttpPost newhttppost = new HttpPost(url);
			Log.d("inside webserver","url "+url);
			try {
				newhttppost.setEntity(new UrlEncodedFormEntity(namevaluepair));
				HttpResponse myresponse = myclient.execute(newhttppost);
				int stCode=myresponse.getStatusLine().getStatusCode();
				Log.d("status code",""+stCode);
				Log.d("status code",""+stCode);
				Log.d("status code",""+stCode);
				Log.d("status code",""+stCode);
				Log.d("status code",""+stCode);
				Log.i("post response", "" + myresponse);
				String line = "";
				StringBuilder total = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						myresponse.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					total.append(line);
				}

				myjsonarray = new JSONArray(total.toString());
				return myjsonarray;
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				Log.d("inside catch", e.getLocalizedMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.d("inside catch", e.getLocalizedMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("inside catch", e.getLocalizedMessage());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.d("inside catch", e.getLocalizedMessage());
			}

			
			
			
	
		
		return null;

	}

}
