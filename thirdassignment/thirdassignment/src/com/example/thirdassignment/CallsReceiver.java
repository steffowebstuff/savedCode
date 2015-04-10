package com.example.thirdassignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallsReceiver extends BroadcastReceiver {

	// The Receiver class for incoming calls
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String phonenumber = null;
		// Toast.makeText(context, "In CallsReceiver",
		// Toast.LENGTH_LONG).show();
		if (null == bundle)
			return;
		String state = bundle.getString(TelephonyManager.EXTRA_STATE);
		if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
			phonenumber = bundle
					.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			/*Toast.makeText(
					context,
					"Number " + phonenumber
							+ " is being added to your messagelist",
					Toast.LENGTH_SHORT).show();*/
			
			File numberPath =  Environment.getExternalStorageDirectory();
			File numberFile = new File(numberPath, "NumberFile.txt");
			String myState = Environment.getExternalStorageState();
			
			//Checking if it is possible to write to the file
			if (Environment.MEDIA_MOUNTED.equals(myState) && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(myState)) {
				try{
					numberPath.mkdirs();
					if(!numberFile.exists()){
						numberFile.createNewFile();
					}
					FileWriter fwriter = new FileWriter(numberFile.getAbsoluteFile(), true);
		
					BufferedWriter bwriter = new BufferedWriter(fwriter);
					bwriter.append(phonenumber);
					bwriter.newLine();
					bwriter.close();
				}
				catch(Exception e){
					
				}
			
			}
			
			
			
			//Saves the number in shared prefs just in case. However I wont use it in this application
			//since I might as well take the number from the built in call log.
			/*SharedPreferences prefs = context.getSharedPreferences("prefs",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor edit = prefs.edit();
			edit.putString("tel"+phonenumber, phonenumber);
			edit.commit();*/
		}

	}

}
