package com.example.secondassignment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmClock extends Activity {

	// Alarm clock main class, Also uses SetAlarm, AlarmReceiver, and Alarmringer.

	private Activity main_activity;
	public AlarmManager am;
	public PendingIntent alarmIntent;
	public Button setAlarmButton;
	public TextView alarmClockTitle;
	public Button stopAlarmButton;

	int count = 0;
	int newhours;
	int seconds;
	int minutes;
	boolean set;

	public Handler handler = new Handler();
	public TextView countview;
	protected static final long TIME_INTERVAL = 5000;

	// Timer method for the clock.
	Runnable updateTextRunnable = new Runnable() {
		public void run() {
			count++;
			Time nowtime = new Time();
			nowtime.setToNow();
			countview = (TextView) findViewById(R.id.countview);
			countview.setText(nowtime.hour + " : " + nowtime.minute + " : "
					+ nowtime.second + " : ");
			handler.postDelayed(this, TIME_INTERVAL);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_clock);

		Resources res = getResources();
		final String stopAlarm = String.format(res
				.getString(R.string.stop_alarm));
		final String resetAlarm = String.format(res
				.getString(R.string.reset_alarm));
		final String setAlarm = String
				.format(res.getString(R.string.set_alarm));
		final String noAlarmSet = String.format(res
				.getString(R.string.no_alarm_set));

		alarmClockTitle = (TextView) findViewById(R.id.alarmClockTitle);
		stopAlarmButton = (Button) findViewById(R.id.stopAlarmButton);
		setAlarmButton = (Button) findViewById(R.id.setAlarmButton);

		// The manager that sets the alarm
		am = (AlarmManager) getSystemService(ALARM_SERVICE);

		if (savedInstanceState != null) {
			set = savedInstanceState.getBoolean("set");
			if (set == true) {
				stopAlarmButton.setText(stopAlarm);
				setAlarmButton.setText(resetAlarm);

				newhours = savedInstanceState.getInt("hours");
				minutes = savedInstanceState.getInt("minutes");
				seconds = savedInstanceState.getInt("seconds");
				set = savedInstanceState.getBoolean("set");
				alarmClockTitle.setText("The alarm will go off at " + newhours
						+ " : " + minutes + " : " + seconds + "");
			}

			setAlarmButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Log.d("reset button", "click");
						Intent intent = new Intent(
								"com.example.secondassignment.ALARM_BROADCAST");
						alarmIntent = PendingIntent.getBroadcast(
								AlarmClock.this, 0, intent, 0);// hej
						am.cancel(alarmIntent);
						set = false;
						Toast.makeText(AlarmClock.this,
								"Alarm intent canceled from reset",
								Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Toast.makeText(AlarmClock.this, "in catch",
								Toast.LENGTH_SHORT).show();

					}
					try {
						Intent intent = new Intent(main_activity,
								SetAlarm.class);
						main_activity.startActivityForResult(intent, 0);
						set = true;
					} catch (Exception e) {
						Toast.makeText(AlarmClock.this, "in catch",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			stopAlarmButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(
								"com.example.secondassignment.ALARM_BROADCAST");
						alarmIntent = PendingIntent.getBroadcast(
								AlarmClock.this, 0, intent, 0);
						am.cancel(alarmIntent);
						set = false;
						setAlarmButton.setText(setAlarm);
						stopAlarmButton.setText(noAlarmSet);
						alarmClockTitle.setText(noAlarmSet);

					} catch (Exception e) {
						Toast.makeText(AlarmClock.this, "in catch",
								Toast.LENGTH_SHORT).show();

					}

				}
			});
		}

		// TODO Auto-generated method stub
		SharedPreferences prefs = getApplicationContext().getSharedPreferences(
				"prefs", Context.MODE_PRIVATE);

		handler.post(updateTextRunnable);
		main_activity = this;
		setAlarmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(main_activity, SetAlarm.class);
					main_activity.startActivityForResult(intent, 0);
				} catch (Exception e) {
				}
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent result) {

		if (resultCode == RESULT_OK) {
			SharedPreferences prefs = getApplicationContext()
					.getSharedPreferences("prefs", Context.MODE_PRIVATE);
			String stopped = prefs.getString("stopped", "nothing here");
			if (stopped.equals("true")) {
				Toast.makeText(AlarmClock.this, "alarm clock is stopped",
						Toast.LENGTH_SHORT).show();
			}
			SharedPreferences.Editor edit = prefs.edit();
			edit.putString("stopped", "false from activityresult");
			edit.commit();

			int yeartime = result.getIntExtra("year", 0);
			int monthtime = result.getIntExtra("month", 0);
			int daytime = result.getIntExtra("day", 0);
			int hourtime = result.getIntExtra("hour", 0);
			int minutetime = result.getIntExtra("minute", 0);

			Calendar adjustedCalendar = Calendar.getInstance();
			adjustedCalendar.set(Calendar.YEAR, yeartime);
			adjustedCalendar.set(Calendar.MONTH, monthtime);
			adjustedCalendar.set(Calendar.DAY_OF_MONTH, daytime);
			adjustedCalendar.set(Calendar.HOUR_OF_DAY, hourtime);
			adjustedCalendar.set(Calendar.MINUTE, minutetime);
			adjustedCalendar.set(Calendar.SECOND, 0);

			// Is used for the check if time has already been
			// This is the set off time. Notice that it is one
			// hour delayed
			Calendar timezoneAdjustedCalendar = Calendar.getInstance(TimeZone
					.getTimeZone("UTC"));

			// this should be the now time in millis
			long timezoneAdjustedMillis = timezoneAdjustedCalendar
					.getTimeInMillis();
			long currentTime = System.currentTimeMillis();
			long adjustcalendarmillis = adjustedCalendar.getTimeInMillis();

			// Checking if the time has already occured
			if (adjustcalendarmillis < currentTime) {
				Toast.makeText(AlarmClock.this,
						"you can not set a time that has already occured",
						Toast.LENGTH_SHORT).show();
				set = false;
				return;
			} else {
				set = true;
			}

			Intent intent = new Intent(
					"com.example.secondassignment.ALARM_BROADCAST");
			intent.putExtra("message", "The one-shot alarm has gone off");
			alarmIntent = PendingIntent.getBroadcast(AlarmClock.this, 0,
					intent, 0);
			// Where i send in the set off time to the alarm ringer
			am.set(AlarmManager.RTC_WAKEUP, adjustcalendarmillis, alarmIntent);

			seconds = (int) (adjustcalendarmillis / 1000.0d) % 60;
			minutes = (int) (adjustcalendarmillis / (1000.0d * 60.0d)) % 60;
			int hours = (int) (adjustcalendarmillis / (1000.0d * 60.0d * 60.0d)) % 24;

			// The view that is showing the time for the alarm to go off
			final TextView alarmClockTitle = (TextView) findViewById(R.id.alarmClockTitle);
			newhours = hours + 1;
			alarmClockTitle.setText("The alarm will go off at" + newhours
					+ " : " + minutes + " : " + seconds + ":");
			Resources res = getResources();
			final String stopAlarm = String.format(res
					.getString(R.string.stop_alarm));
			final String noAlarm = String.format(res
					.getString(R.string.no_alarm_set));
			final String setAlarm = String.format(res
					.getString(R.string.set_alarm));
			final String resetAlarm = String.format(res
					.getString(R.string.reset_alarm));
			setAlarmButton.setText(resetAlarm);
			stopAlarmButton.setText(stopAlarm);
			stopAlarmButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Log.d("stop button", "click");
						am.cancel(alarmIntent);
						set = false;
						Toast.makeText(AlarmClock.this,
								"Alarm intent canceled", Toast.LENGTH_SHORT)
								.show();
						setAlarmButton.setText(setAlarm);
						alarmClockTitle.setText(noAlarm);
						stopAlarmButton.setText(noAlarm);
					} catch (Exception e) {
						Toast.makeText(AlarmClock.this, "in catch",
								Toast.LENGTH_SHORT).show();

					}

				}
			});

			setAlarmButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Log.d("reset button", "click");
						am.cancel(alarmIntent);
						set = false;
						Toast.makeText(AlarmClock.this,
								"Alarm intent canceled from reset",
								Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Toast.makeText(AlarmClock.this, "in catch",
								Toast.LENGTH_SHORT).show();

					}
					try {
						Intent intent = new Intent(main_activity,
								SetAlarm.class);
						main_activity.startActivityForResult(intent, 0);
					} catch (Exception e) {
						Toast.makeText(AlarmClock.this, "in catch",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

		}

	}

	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save the user's current game state

		try {
			savedInstanceState.putInt("hours", newhours);
			savedInstanceState.putInt("minutes", minutes);
			savedInstanceState.putInt("seconds", seconds);
			savedInstanceState.putBoolean("set", set);
		}

		catch (Exception e) {

		}

		super.onSaveInstanceState(savedInstanceState);
	}

}
