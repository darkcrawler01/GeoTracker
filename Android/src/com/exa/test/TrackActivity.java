package com.exa.test;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.exa.constants.FreeBikeConstants;
import com.parse.ParseACL;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class TrackActivity extends Activity {
	
	public static TextView latitude;
	public static TextView longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track);
		Log.d(FreeBikeConstants.TAG, "in track activity");
		
		latitude = (TextView)findViewById(R.id.latitude);
		longitude = (TextView)findViewById(R.id.longitude);
		
		// Show the Up button in the action bar.
		LocationManager locationManager = (LocationManager) this.getSystemService(TrackActivity.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoListener());
		Log.d(FreeBikeConstants.TAG, "got location");
	}



}


class GeoListener implements LocationListener {
	@Override
    public void onLocationChanged(Location location) {
		Log.d(FreeBikeConstants.TAG, "Location changed");
      // Called when a new location is found by the network location provider.
		ParseGeoPoint place = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
		ParseObject track = new ParseObject("TrackLocation");
		track.put("location", place);
		track.put("parent", ParseUser.getCurrentUser());
		track.setACL(new ParseACL(ParseUser.getCurrentUser()));
		track.saveEventually();		
		Log.d(FreeBikeConstants.TAG, "location change Saved eventually");	
		TrackActivity.latitude.setText(Double.valueOf(location.getLatitude()).toString());
		TrackActivity.longitude.setText(Double.valueOf(location.getLongitude()).toString());		
    }

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

  }
