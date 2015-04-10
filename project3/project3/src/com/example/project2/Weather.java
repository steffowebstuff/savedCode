package com.example.project2;

import java.io.IOException;
import java.net.URL;

//import com.example.testthree.VaxjoWeather.MyAdapter;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//import com.example.firststep.VaxjoWeather.WeatherRetriever;

//Class used for getting the weather from three different cities in Sweden
public class Weather {
	private String inweather = " here we are again";
	private String city = null;
	private WeatherReport report = null;
	private WeatherForecast wf = null;
	private URL widgeturl = null;

	public String getInWeather() {
		return inweather;
	}

	public String GetCity() {
		return "City = " + city;
	}

	public void SetCity(String incity) {
		city = incity;
	}

	public WeatherForecast GetForecast() {
		return this.wf;
	}

	

	public void ReceiveForecast2(IDone done) {
		WeatherForecast forecast = new WeatherForecast();
		try {
			if (city.equals("Stockholm")) {
				widgeturl = new URL(
						"http://www.yr.no/sted/Sverige/stockholm/stockholm/forecast.xml");
				AsyncTask task = new WeatherRetriever(done).execute(widgeturl);
				Log.d("task", " " + task);

			} else if (city.equals("Kalmar")) {
				widgeturl = new URL(
						"http://www.yr.no/sted/Sverige/kalmar/kalmar/forecast.xml");
				//city = "Kalmar";
				AsyncTask task = new WeatherRetriever(done).execute(widgeturl);
				Log.d("task", " " + task);
			} else if (city.equals("V�xj�")) {
				widgeturl = new URL(
						"http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
				AsyncTask task = new WeatherRetriever(done).execute(widgeturl);
				Log.d("task", " " + task);
			} else {
				Log.d("", "did not find any city, going for default city");
				widgeturl = new URL(
						"http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//Interface used to return value after the Async Task
	public interface IDone {
		public void onDone(WeatherReport report);
	}

	private void PrintReportToConsole() {
		if (this.report != null) {
			int count = 0;
			WeatherForecast forecasts[] = report.getForecasts().toArray(
					new WeatherForecast[report.getForecasts().size()]);
			this.wf = forecasts[0];

		} else {
			System.out.println("Weather report has not been loaded.");
		}
	}

	private class WeatherRetriever extends AsyncTask<URL, Void, WeatherReport> {
		private IDone done;
		public WeatherRetriever(IDone done) {
			this.done = done;
		}
		
		protected WeatherReport doInBackground(URL... urls) {
			try {
				//URL url = new URL(
				//		"http://www.yr.no/sted/Sverige/kalmar/kalmar/forecast.xml");
				return WeatherHandler.getWeatherReport(widgeturl, city);
				// return WeatherHandler.getWeatherReport(urls[0]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected void onProgressUpdate(Void... progress) {

		}

		protected void onPostExecute(WeatherReport result) {
			this.done.onDone(result);
			report = result;
			Log.d("report", "" + report);
			PrintReportToConsole();
		}
	}
}
