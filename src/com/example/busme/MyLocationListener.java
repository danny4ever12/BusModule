package com.example.busme;




import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;



public class MyLocationListener implements LocationListener {
	public static double lon;
	public static double lat;

	public void onLocationChanged(Location location) {

		lat = location.getLatitude();
		lon = location.getLongitude();

	}

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
