package com.example.thirdassignment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.thirdassignment.Weather.IDone;

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

	// Code inspired by Vogella
	// http://www.vogella.com/tutorials/AndroidWidgets/article.html
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Weather weather = new Weather();
		final AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(this.getApplicationContext());
		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		//ComponentName thisWidget = new ComponentName(getApplicationContext(),
				//AWeather.class);
		//int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

		for (final int widgetId : allWidgetIds) {
			SharedPreferences prefs = getApplicationContext()
					.getSharedPreferences("prefs", Context.MODE_PRIVATE);
			String keycity = prefs.getString("key" + widgetId, "Press Update");
			Log.d("service key city", "" + keycity);

			// weather.SetCity(keycity);
			// String testcity = weather.GetCity();

			long currentTime = System.currentTimeMillis();
			Log.d("currenttime", " " + currentTime);

			final RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.main_widget);
			remoteViews.setTextViewText(R.id.widgetText, " " + widgetId + " "
					+ keycity);
			//remoteViews.setTextViewText(R.id.timeText, " " + currentTime);

			//the intent for the update button
			Intent clickIntent = new Intent(this.getApplicationContext(),
					AWeather.class);
			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			//intent for the activity
			Intent newintent = new Intent(this.getApplicationContext(),
					WeatherWidget.class);
			newintent.putExtra("kc", keycity);
			// Start activity when you press the weather button
			PendingIntent pi = PendingIntent.getActivity(
					this.getApplicationContext(), widgetId, newintent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.imageButton1, pi);

			// To update the widget when you press the update button
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), widgetId, clickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.updateButton,
					pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);

			weather.ReceiveForecast2(new IDone() {
				@Override
				public void onDone(WeatherReport report) {
					List<WeatherForecast> forecasts = report.getForecasts();
					WeatherForecast firstfc = forecasts.get(0);
					int temp = firstfc.getTemp();
					int weather_code = firstfc.getWeatherCode();

					if (weather_code == 1) {
						remoteViews.setImageViewResource(R.id.imageButton1,
								R.drawable.sun);
					}

					else if (weather_code == 3 || weather_code == 9
							|| weather_code == 2) {
						remoteViews.setImageViewResource(R.id.imageButton1,
								R.drawable.rain);
					} else if (weather_code == 4) {
						// cloudy
						remoteViews.setImageViewResource(R.id.imageButton1,
								R.drawable.cloudy);
					} else if (weather_code == 13) {
						// snow
						remoteViews.setImageViewResource(R.id.imageButton1,
								R.drawable.snow);
					} else if (weather_code == 15) {
						// foggy
						remoteViews.setImageViewResource(R.id.imageButton1,
								R.drawable.foggy);
					}
					Log.d("first forecast temp", " " + temp);
					remoteViews.setTextViewText(R.id.weatherText, " " + temp
							+ "dgr");

					appWidgetManager.updateAppWidget(widgetId, remoteViews);
				}
			}, keycity);
		}
		stopSelf();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d("service ", "stoppped");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private final IBinder binder = new WidgetBinder();
	 * 
	 * @Override public IBinder onBind(Intent intent) { Log.d("service",
	 * "from config"); bound = true; return binder;
	 * 
	 * }
	 * 
	 * public class WidgetBinder extends Binder { NewWidgetService getService()
	 * { return NewWidgetService.this; }
	 */

}
