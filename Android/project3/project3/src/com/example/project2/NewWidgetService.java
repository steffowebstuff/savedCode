package com.example.project2;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.project2.Weather.IDone;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.TrackInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

//The main service class for the widget
public class NewWidgetService extends Service {

	public boolean bound = false;
	int count = 0;
	WeatherForecast wf = null;
	Weather weather = null;
	String weather_forecast = null;

	public void onCreate() {
		System.out.println("onCreate()");
		Log.d("service", "im in newwidget");
		weather = new Weather();

	}



	//Code inspired by Vogella http://www.vogella.com/tutorials/AndroidWidgets/article.html
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Weather weather = new Weather();
		final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());
		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		ComponentName thisWidget = new ComponentName(getApplicationContext(),
				AWidget.class);
		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		Log.w("widgetsize", "From Intent" + String.valueOf(allWidgetIds.length));
		Log.w("widgetsize2", "Direct" + String.valueOf(allWidgetIds2.length));
		for (final int widgetId : allWidgetIds) {

			SharedPreferences prefs = getApplicationContext()
					.getSharedPreferences("prefs", Context.MODE_PRIVATE);
			String keycity = prefs.getString("key" + widgetId, "Press Update");
			Log.d("service key city", "" + keycity);

			weather.SetCity(keycity);
			//String testcity = weather.GetCity();
			
			long currentTime = System.currentTimeMillis();
			Log.d("currenttime", " " + currentTime);

			final RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.main_widget);
			remoteViews.setTextViewText(R.id.widgetText, " " + widgetId
					+ " " + keycity);
			
			//Same function but with millitime in case you need to see that the update works
			/*remoteViews.setTextViewText(R.id.widgetText, "newid " + widgetId
					+ " " + keycity + " " + currentTime);*/
			Intent clickIntent = new Intent(this.getApplicationContext(),
					AWidget.class);
			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			Intent newintent = new Intent(this.getApplicationContext(),
					WeatherWidget.class);
			newintent.putExtra("kc", keycity);
			PendingIntent pi = PendingIntent.getActivity(
					this.getApplicationContext(), widgetId, newintent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.imageButton1, pi);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), widgetId, clickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.updateButton,
					pendingIntent); 
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
			
			weather.ReceiveForecast2(new IDone() {
				@Override
				public void onDone(WeatherReport report) {
					Toast.makeText(getApplicationContext(),
							"Widgets AWeather are now being updated", Toast.LENGTH_SHORT)
							.show();
					Log.d(" ", " "+report);
					List<WeatherForecast> forecasts = report.getForecasts();
					WeatherForecast firstfc = forecasts.get(0);
					int temp = firstfc.getTemp();
					String weather_name = firstfc.getWeatherName();
					int weather_code = firstfc.getWeatherCode();
					//int weather_code = firstfc.getPeriodCode();
					
					//Could not find a good list of what the weather  types in yr.no stood for so I picked the ones that I could find at the moment.
					if (weather_code == 1){
						remoteViews.setImageViewResource(R.id.imageButton1, R.drawable.sun);
					}
					
					else if (weather_code == 3 || weather_code == 9 || weather_code == 2){
						remoteViews.setImageViewResource(R.id.imageButton1, R.drawable.rain);
					}
					else if (weather_code == 4){
						//cloudy
						remoteViews.setImageViewResource(R.id.imageButton1, R.drawable.cloudy);
					}
					else if (weather_code == 13){
						//snow
						remoteViews.setImageViewResource(R.id.imageButton1, R.drawable.snow);
					}
					else if (weather_code == 15){
						//foggy
						remoteViews.setImageViewResource(R.id.imageButton1, R.drawable.foggy);
					}
					/*Toast.makeText(getApplicationContext(),
							"weather name" + weather_name, Toast.LENGTH_SHORT)
							.show();*/
					Log.d("first forecast temp", " " + temp);
					remoteViews.setTextViewText(R.id.weatherText, " " + temp + "dgr");
					
					appWidgetManager.updateAppWidget(widgetId, remoteViews);
				}
			}); 
		}
		stopSelf();

		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void onDestroy() {
		Log.d("service ", "stoppped");
	}
	

	private final IBinder binder = new WidgetBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("service", "from config");
		bound = true;
		return binder;

	}

	public class WidgetBinder extends Binder {
		NewWidgetService getService() {
			return NewWidgetService.this;
		}
	}

}
