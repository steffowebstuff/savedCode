package com.example.thirdassignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//Main class for Exercise 3
public class Maps1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ArrayList<City> citylist = new ArrayList<City>();
		try {
			BufferedReader buffered_reader = new BufferedReader(
					new InputStreamReader(getAssets().open("coordinates.txt")));
			String line = null;
			line = buffered_reader.readLine();
			while ((line = buffered_reader.readLine()) != null) {
				String[] city_array = line.split(",");
				String city_name = city_array[0];
				double latitude = Double.parseDouble(city_array[1]);
				double longitude = Double.parseDouble(city_array[2]);
				citylist.add(new City(city_name, latitude, longitude));
			}
			buffered_reader.close();
		} catch (IOException e) {
			Log.d("in catch", " " + e);
		}
		final GoogleMap map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		LatLng cityCenter = new LatLng(56.875986, 14.807417);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCenter, 4));
		map.addMarker(new MarkerOptions().position(cityCenter));
		for (City city : citylist) {
			LatLng center = new LatLng(city.latitude, city.longitude);
			map.addMarker(new MarkerOptions().title(city.name)
					.snippet(city.name).position(center));
		}

		map.setOnMapClickListener(new OnMapClickListener() {
			// Method for toasting the city names when you click them on the map

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				Log.d("latitude", arg0.latitude + "longitude" + arg0.longitude);
				LatLng mapcenter = map.getCameraPosition().target;
				Log.d("mapcenter", " " + mapcenter.latitude + " "
						+ mapcenter.longitude);
			/*	Toast.makeText(getApplicationContext(), "Testing! " + "latitude" + arg0.latitude + "longitude" + arg0.longitude,
						Toast.LENGTH_SHORT).show();*/

				if (arg0.latitude > 57 && arg0.latitude < 58
						&& arg0.longitude > 11 && arg0.longitude < 13) {
					Toast.makeText(getApplicationContext(), "Gothenburg!",
							Toast.LENGTH_SHORT).show();
				}
				if (arg0.latitude > 56 && arg0.latitude < 58
						&& arg0.longitude > 15 && arg0.longitude < 18) {
					Toast.makeText(getApplicationContext(), "Kalmar!",
							Toast.LENGTH_SHORT).show();
				}
				
				if (arg0.latitude > 56 && arg0.latitude < 58
						&& arg0.longitude > 13 && arg0.longitude < 16) {
					Toast.makeText(getApplicationContext(), "Vaxjö!",
							Toast.LENGTH_SHORT).show();
				}
				
				if (arg0.latitude > 57 && arg0.latitude < 60
						&& arg0.longitude > 14 && arg0.longitude < 16) {
					Toast.makeText(getApplicationContext(), "Linkoping!",
							Toast.LENGTH_SHORT).show();
				}
				
				if (arg0.latitude > 55 && arg0.latitude < 57
						&& arg0.longitude > 14 && arg0.longitude < 17) {
					Toast.makeText(getApplicationContext(), "Karlskrona!",
							Toast.LENGTH_SHORT).show();
				}
				
				
				/* else if (arg0.latitude > 56 && arg0.latitude < 58
						&& arg0.longitude > 15 && arg0.longitude < 18) {
					Toast.makeText(getApplicationContext(), "Kalmar!",
							Toast.LENGTH_SHORT).show();
				} else if (arg0.latitude > 56 && arg0.latitude < 58
						&& arg0.longitude > 13 && arg0.longitude < 16) {
					Toast.makeText(getApplicationContext(), "Vaxjö!",
							Toast.LENGTH_SHORT).show();
				} else if (arg0.latitude > 57 && arg0.latitude < 60
						&& arg0.longitude > 14 && arg0.longitude < 16) {
					Toast.makeText(getApplicationContext(), "Linkoping!",
							Toast.LENGTH_SHORT).show();
				} else if (arg0.latitude > 55 && arg0.latitude < 57
						&& arg0.longitude > 14 && arg0.longitude < 17) {
					Toast.makeText(getApplicationContext(), "Karlskrona!",
							Toast.LENGTH_SHORT).show();
				}*/

				
				float inDistance = 40000;
				String inCity = null;
				for (City city : citylist) {
					float distance = distFrom((float) mapcenter.latitude,
							(float) mapcenter.longitude, (float) city.latitude,
							(float) city.longitude);
					Log.d("distance", " " + distance);
					if (distance < inDistance) {
						inDistance = distance;
						inCity = city.name;
					}

				}
				Log.d("inDistance ", "" + inDistance);
				Log.d("inCity ", "" + inCity);
			}
		});

		//Method for finding out which is the closest city and toasting it when you navigate the map
		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			public void onCameraChange(CameraPosition position) {
				map.clear();
				Log.d("camera changed ", "");
				LatLng mapcenter = map.getCameraPosition().target;

				float inDistance = 40000;
				String inCity = null;

				for (City city : citylist) {
					float distance = distFrom((float) mapcenter.latitude,
							(float) mapcenter.longitude, (float) city.latitude,
							(float) city.longitude);
					Log.d("distance", " " + distance);
					if (distance < inDistance) {
						inDistance = distance;
						inCity = city.name;
					}

				}

				Toast.makeText(getApplicationContext(),
						"Distance(km) " + inDistance + " city " + inCity,
						Toast.LENGTH_SHORT).show();
				Log.d("inDistance ", "" + inDistance);
				Log.d("inCity ", "" + inCity);
				map.addMarker(new MarkerOptions()
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.position(mapcenter));

				try {
					BufferedReader buffered_reader = new BufferedReader(
							new InputStreamReader(getAssets().open(
									"coordinates.txt")));
					String city_line = null;
					city_line = buffered_reader.readLine();
					while ((city_line = buffered_reader.readLine()) != null) {
						String[] city_array = city_line.split(",");
						String city_name = city_array[0];
						double latitude = Double.parseDouble(city_array[1]);
						double longitude = Double.parseDouble(city_array[2]);
						citylist.add(new City(city_name , latitude, longitude));
					}
					buffered_reader.close();
				} catch (IOException e) {
					Log.d("in catch", " "+ e);
				}

				for (City city : citylist) {
					LatLng center = new LatLng(city.latitude, city.longitude);
					map.addMarker(new MarkerOptions().title(city.name)
							.snippet(city.name).position(center));
				}

			}
		});
	}

	// Method taken from stackoverflow and is used to count the distance between
	// two coordinates in a global map.
	// http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 6371; // kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);
		return dist;
	}

	// Class to use when user navigates
	private class LocListener implements LocationListener {
		private int count = 0;

		@Override
		public void onLocationChanged(Location loc) {
			Log.d("location changed", " " + count);
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
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}

	}
}
