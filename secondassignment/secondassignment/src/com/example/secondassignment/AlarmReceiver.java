package com.example.secondassignment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//The broadcast receiver that is called at the time when the alarm has been set 
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {	
		Intent newintent = new Intent(context, AlarmRinger.class);
		newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    context.startActivity(newintent);
	}

}