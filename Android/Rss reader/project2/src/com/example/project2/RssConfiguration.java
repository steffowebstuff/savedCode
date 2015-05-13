package com.example.project2;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Toast;

//The configuration class for the widget
public class RssConfiguration extends Activity {

	private RssConfiguration context;
	private int widgetID;
	private WidgetService service = null;

	public WidgetService getService() {
		return this.service;
	}

	private ServiceConnection connection = new ServiceConnection() {
		public void onServiceConnected(ComponentName cName, IBinder binder) {
			service = ((WidgetService.WidgetBinder) binder).getService();
		}

		// @Override //
		public void onServiceDisconnected(ComponentName cName) {
			service = null;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_configure);
		setResult(RESULT_CANCELED);
		context = this;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		Button widgetSelect = (Button) findViewById(R.id.widgetSelect);
		final EditText topic_text = (EditText) findViewById(R.id.topicInput);
		final CheckBox chk_pol = (CheckBox) findViewById(R.id.checkPolitics);
		final CheckBox chk_eco = (CheckBox) findViewById(R.id.checkEco);

		widgetSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultValue = new Intent();
				resultValue.setAction("prefupdate");
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
						widgetID);
				setResult(RESULT_OK, resultValue);
				String topic = topic_text.getText().toString();

				SharedPreferences prefs = getSharedPreferences("prefs",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putInt("id", widgetID);
				edit.putString("key" + widgetID, topic);
				
				//Checking if boxes for politics and economy are checked
				if (chk_pol.isChecked() == true) {
					edit.putString("pol" + widgetID, "true");
				}
				if (chk_eco.isChecked() == true) {
					edit.putString("eco" + widgetID, "true");
				}
				edit.commit();
				finish();
			}
		});
	}
}
