package com.example.thirdassignment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//import com.example.testthree.Weather.WeatherRetriever;
//import com.example.testthree.WeatherHandler;
//import com.example.testthree.WeatherReport;
//import com.example.testthree.Weather.ISteffo;
//import com.example.testthree.WeatherHandler;
//import com.example.testthree.WeatherReport;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

//Only class currently used for Exercise 4
public class Maps extends FragmentActivity {

	private static String startCoords = null;
	private static String endCoords = null;
	private static String pathCoords = null;

	// pathNumber 1 goes to odessa, pathNumber 2 goes to Stockholm
	private int pathNumber = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final GoogleMap map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		URL url;
		
		
		
		//Method that is being called after having read the xml-file.
		IReady ready = new IReady() {

			@Override
			public void onDone(String[] coordlist) {
				// TODO Auto-generated method stub
				String[] startCoords = coordlist[0].split(",");
				String[] endCoords = coordlist[1].split(",");
				String[] pathCoords = coordlist[2].split("\\s+");

				ArrayList<LatLng> myCoords = new ArrayList<LatLng>();
				double firstlongitude = Double.parseDouble(startCoords[1]);
				double firstlatitude = Double.parseDouble(startCoords[0]);
				LatLng firstCoordsElement = new LatLng(firstlongitude,
						firstlatitude);
				map.addMarker(new MarkerOptions()
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
						.position(firstCoordsElement));
				myCoords.add(firstCoordsElement);

				//Adjust the zooming based on which path it is
				if (pathNumber == 1) {
					LatLng mapCenter = new LatLng(56.875986, 14.807417);
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter,
							3));
				}

				else {
					LatLng mapCenter = new LatLng(56.875986, 14.807417);
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter,
							6));
				}

				for (int i = 0; i < pathCoords.length; i++) {
					String[] newCoords = pathCoords[i].split(",");
					double longitude = Double.parseDouble(newCoords[1]);
					double latitude = Double.parseDouble(newCoords[0]);
					LatLng coordsElement = new LatLng(longitude, latitude);
					myCoords.add(coordsElement);
					Log.d("newCoords", " " + newCoords);
				}

				double lastlongitude = Double.parseDouble(endCoords[1]);
				double lastlatitude = Double.parseDouble(endCoords[0]);
				LatLng lastCoordsElement = new LatLng(lastlongitude,
						lastlatitude);
				myCoords.add(lastCoordsElement);

				// LatLng endPosition = new LatLng(10, 0);
				map.addMarker(new MarkerOptions()
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.position(lastCoordsElement));

				map.addPolyline(new PolylineOptions()
						.addAll(myCoords));
			}
		};
		try {
			if (pathNumber == 1) {
				url = new URL("http://cs.lnu.se/android/VaxjoToOdessa.kml");
			} else if (pathNumber == 2) {
				url = new URL("http://cs.lnu.se/android/VaxjoToStockholm.kml");
			} else {
				url = new URL("http://cs.lnu.se/android/VaxjoToCopenhagen.kml");
			}

			
		
			AsyncTask task = new KmlRetriever(ready).execute(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class KmlRetriever extends AsyncTask<URL, Void, String[]> {
		private IReady ready;

		public KmlRetriever(IReady ready) {
			this.ready = ready;
		}

		protected String[] doInBackground(URL... urls) {
			try {
				URL url = urls[0];
				return Maps.getKmlFile(url);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected void onProgressUpdate(Void... progress) {

		}

		protected void onPostExecute(String[] coordlist) {
			this.ready.onDone(coordlist);
			Log.d("firstcoords", "" + coordlist);

		}
	}

	// Method for reading the kml-file. Another one inspired by stack overflow
	// http://stackoverflow.com/questions/6251473/why-is-my-urlconnection-getcontentlength-size-always-1
	public static String[] getKmlFile(URL url) {
		// TODO Auto-generated method stub
		Document document = null;
		try {
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(connection.getInputStream());
			document.getDocumentElement().normalize();

		} catch (Exception e) {
		}

		//Method for retrieving path coordinates
		NodeList nodelist = document.getElementsByTagName("LineString");
		for (int x = 0; x < nodelist.getLength(); x++) {
			Node rootNode = nodelist.item(x);
			if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
				Element rootElement = (Element) rootNode;
				NodeList rootChildren = rootElement.getChildNodes();
				Node coordsNode = rootChildren.item(3);
				if (coordsNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) coordsNode;
					pathCoords = element.getTextContent();
					Log.d("pathCoords", " " + pathCoords);
				}
			}
		}

		//Method for retrieving start point and end point
		NodeList nodelist2 = document.getElementsByTagName("Point");
		int count = 0;
		for (int x = 0; x < nodelist2.getLength(); x++) {
			Node rootNode = nodelist2.item(x);
			if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
				Element rootElement = (Element) rootNode;
				NodeList rootChildren = rootElement.getChildNodes();
				for (int y = 0; y < rootChildren.getLength(); y++) {
					Node childNode = rootChildren.item(y);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) childNode;
						String pathCoords2 = element.getTextContent();
						Log.d("pathCoords2", " " + pathCoords2);
						if (count == 0) {
							startCoords = pathCoords2;
							Log.d("startCoords", " " + startCoords);
						} else {
							endCoords = pathCoords2;
							Log.d("endCoords", " " + endCoords);
						}
						count++;
					}
				}
			}
		}

		String[] coordList = new String[3];
		coordList[0] = startCoords;
		coordList[1] = endCoords;
		coordList[2] = pathCoords;
		return coordList;
	}

	//Inteface used to return coordinates when kml reading is done
	public interface IReady {
		public void onDone(String[] coordlist);
	}
}
