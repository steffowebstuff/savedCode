package com.example.thirdassignment;

import java.util.List;

//import com.example.testthree.WeatherForecast;
//import com.example.testthree.VaxjoWeather.MyAdapter;
//import com.example.testthree.R;
//import com.example.testthree.WeatherForecast;
import com.example.thirdassignment.Weather.IDone;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Should maybe extend Broadcast
//public class WeatherWidget extends Activity {
public class WeatherWidget extends ListActivity {

	private Activity main_activity;
	private Weather weather = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			
		}
		Intent i = getIntent();
		SharedPreferences prefs = this.getSharedPreferences("prefs",
				Context.MODE_PRIVATE);

		if (i.getExtras() != null) {
			Bundle b = i.getExtras();
			String bundle_city = b.getString("widget_city");
			int id = b.getInt("widget_id");
			String kc = b.getString("kc");

			//title_display.setText("Current city: "+kc);
			weather = new Weather();
			//weather.SetCity(kc);
			String testcity = weather.GetCity();
			Toast.makeText(WeatherWidget.this, "Weather testing activity " + testcity,
					Toast.LENGTH_SHORT).show();
			//weather.ReceiveForecast . ();
			weather.ReceiveForecast2(new IDone() {
				@Override
				public void onDone(WeatherReport report) {
					Log.d(" ", " "+report);
					List<WeatherForecast> forecasts = report.getForecasts();
					WeatherForecast firstfc = forecasts.get(0);
					int temp = firstfc.getTemp();					
					Log.d("first forecast temp", " " + temp);
					WeatherForecast forecastarray[] = report.getForecasts().toArray(
							new WeatherForecast[report.getForecasts().size()]);
					MyAdapter adapter = new MyAdapter(WeatherWidget.this, forecastarray);
					setListAdapter(adapter);
				}
			}, kc); 
		}
	}
	
	class MyAdapter extends ArrayAdapter<WeatherForecast> {

		public MyAdapter(Context context, WeatherForecast forecast[]) {
			super(context, R.layout.adapted_row, forecast);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row;
			// Create new row view object
			if (convertView == null) { // Create new row view object
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.adapted_row, parent, false);
			} else {
				row = convertView;
			}

			TextView tvtitle = (TextView) row.findViewById(R.id.tvtitle);
			int weather_code = this.getItem(position).getWeatherCode();
			ImageView weathersymbol = (ImageView) row
					.findViewById(R.id.weathersymbol);

			String stringtemp = Integer.toString(this.getItem(position)
					.getTemp());

			tvtitle.setText(this.getItem(position).toString());
			
			//Could not find a good list of what the weather  types in yr.no stood for so I picked the ones that I could find.
			if (weather_code == 1){
				weathersymbol.setImageResource(R.drawable.sun);
			}
			
			else if (weather_code == 3 || weather_code == 9 || weather_code == 2){
				weathersymbol.setImageResource(R.drawable.rain);
			}
			else if (weather_code == 4){
				//cloudy
				weathersymbol.setImageResource(R.drawable.cloudy);
			}
			else if (weather_code == 13){
				//snow
				weathersymbol.setImageResource(R.drawable.snow);
			}
			else if (weather_code == 15){
				//foggy
				weathersymbol.setImageResource(R.drawable.foggy);
			}
			return row;
		}
		
		

	}

	
}
