package com.example.project2;

import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class AWidget extends AppWidgetProvider {

	public RemoteViews views = null;

	@Override
	// The provider class for the widget named AWidget, connected to
	// NewWidgetService.Java
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Intent serviceIntent = new Intent(context.getApplicationContext(),
				NewWidgetService.class);
		serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
				appWidgetIds);

		context.startService(serviceIntent);

	}

	
	/*
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
			Toast.makeText(context,
					"Problem receiving data because of air plane mode", Toast.LENGTH_SHORT)
					.show();

		}
	}*/

}
