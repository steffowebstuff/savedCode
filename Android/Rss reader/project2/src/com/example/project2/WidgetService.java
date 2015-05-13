package com.example.project2;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.example.project2.RssReader.IDone;

//The service class that is used together with the widget
public class WidgetService extends Service {

	public boolean bound = false;
	int count = 0;
	RssReader rss_reader = null;

	public void onCreate() {
		rss_reader = new RssReader();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		RssReader rss_reader = new RssReader();
		final AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(this.getApplicationContext());
		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		for (final int widgetId : allWidgetIds) {
			SharedPreferences prefs = getApplicationContext()
					.getSharedPreferences("prefs", Context.MODE_PRIVATE);
			// Receives the shared prefs and information if the user has checked
			// any of the subject alternatives
			String topic = prefs.getString("key" + widgetId, "Press Update");
			String pol_checked = prefs.getString("pol" + widgetId, "No pol");
			String eco_checked = prefs.getString("eco" + widgetId, "No eco");

			/*
			 * Toast.makeText(getApplicationContext(), "Topic  " + topic +
			 * "pol " + pol_checked, Toast.LENGTH_SHORT) .show();
			 */

			final RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.main_widget);
			remoteViews.setTextViewText(R.id.widgetText, " " + widgetId + " "
					+ topic /* + System.currentTimeMillis() */);

			//intent for the activity
			Intent clickIntent = new Intent(this.getApplicationContext(),
					AWidget.class);
			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			Intent newintent = new Intent(this.getApplicationContext(),
					RssWidget.class);
			newintent.putExtra("kc", topic);
			newintent.putExtra("pc", pol_checked);
			newintent.putExtra("ec", eco_checked);
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

			rss_reader.ReceiveForecast2(new IDone() {
				@Override
				public void onDone(ArrayList<RssObject> rss_list) {
					remoteViews.setImageViewResource(R.id.imageButton1,
							R.drawable.rsspicturetwo);
					int size = rss_list.size();
					remoteViews.setTextViewText(R.id.widgetText2, " " + size
							+ " " /* + System.currentTimeMillis() */);
					try {
						String time = rss_list.get(0).GetTime();
						Toast.makeText(getApplicationContext(),
								"test_time  " + time, Toast.LENGTH_SHORT)
								.show();
						remoteViews.setTextViewText(R.id.widgetText3, " "
								+ time);
					} catch (Exception e) {
						Log.d("", "");
					}
					appWidgetManager.updateAppWidget(widgetId, remoteViews);

				}
			}, topic, pol_checked, eco_checked);
		}
		stopSelf();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
	}

	private final IBinder binder = new WidgetBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class WidgetBinder extends Binder {
		WidgetService getService() {
			return WidgetService.this;
		}
	}

}
