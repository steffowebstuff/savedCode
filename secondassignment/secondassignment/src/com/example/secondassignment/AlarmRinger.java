package com.example.secondassignment;


import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//The class that rings the alarm. When button is called the activity closes down and returns to the original one. 
public class AlarmRinger extends Activity {

	public Uri alarmUri;
	public Ringtone alarmsignal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_ringer);
		Button stopAlarmButton = (Button) findViewById(R.id.stopAlarmButton);
		alarmUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_ALARM);
		alarmsignal = RingtoneManager.getRingtone(getApplicationContext(),
				alarmUri);
		alarmsignal.play();

		stopAlarmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alarmsignal.stop();
				Intent newintent = new Intent(AlarmRinger.this, AlarmClock.class);
				newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    startActivity(newintent);
			}
		});

	}

}
