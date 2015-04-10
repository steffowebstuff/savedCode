package com.example.thirdassignment;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.appwidget.AppWidgetManager;
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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Toast;

//The configuration class for the widget
public class WeatherConfiguration extends Activity {

	private WeatherConfiguration context;
	private int widgetID;
	private NewWidgetService service = null;

	public NewWidgetService getService() {
		return this.service;
	}

	/*
	private ServiceConnection connection = new ServiceConnection() {
		// @Override // Called when connection is made
		
		public void onServiceConnected(ComponentName cName, IBinder binder) {
			service = ((NewWidgetService.WidgetBinder) binder).getService();
		}

		// @Override //
		public void onServiceDisconnected(ComponentName cName) {
			service = null;
		}
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weatherconfigure);
		setResult(RESULT_CANCELED);
		context = this;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		final AppWidgetManager widgetManager = AppWidgetManager
				.getInstance(context);
		final RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.activity_main);

		Button widgetSelect = (Button) findViewById(R.id.widgetSelect);

		// TODO: Group these buttons
		final RadioButton kalmarButton = (RadioButton) findViewById(R.id.kalmarButton);
		final RadioButton stockholmButton = (RadioButton) findViewById(R.id.stockholmButton);
		final RadioButton vaxjoButton = (RadioButton) findViewById(R.id.vaxjoButton);
		final RadioButton kirunaButton = (RadioButton) findViewById(R.id.kirunaButton);
		final RadioButton halmstadButton = (RadioButton) findViewById(R.id.halmstadButton);

		widgetSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultValue = new Intent();
				resultValue.setAction("prefupdate");
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
						widgetID);
				setResult(RESULT_OK, resultValue);
				String cityString = null;

				if (kalmarButton.isChecked() == true) {

					cityString = "Kalmar";
				} else if (stockholmButton.isChecked() == true) {

					cityString = "Stockholm";
				} else if (vaxjoButton.isChecked() == true) {

					cityString = "Växjö";
				}

				else if (kirunaButton.isChecked() == true) {

					cityString = "Kiruna";
				}
				
				else if (halmstadButton.isChecked() == true) {

					cityString = "Halmstad";
				}

				else {
					cityString = "Växjö";
				}

				Toast.makeText(WeatherConfiguration.this,
						"chosen city" + cityString, Toast.LENGTH_SHORT).show();
				SharedPreferences prefs = getSharedPreferences("prefs",
						Context.MODE_PRIVATE);

				SharedPreferences.Editor edit = prefs.edit();
				edit.putString("city", cityString);
				edit.putInt("id", widgetID);
				edit.putString("key" + widgetID, cityString);
				edit.commit();
				finish();
			}
		});
	}
}
