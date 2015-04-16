package com.example.thirdassignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

//import com.example.testthree.VaxjoWeather.MyAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//The class that is used for presenting the incoming call and give the user the choice to use the numbers
public class MessageSender extends ListActivity {

	ArrayList<String> myCalls = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		File numberPath = Environment.getExternalStorageDirectory();
		File numberFile = new File(numberPath, "NumberFile.txt");
		String myState = Environment.getExternalStorageState();
		BufferedReader breader = null;
		ArrayList<String> incomingCalls = null;

		if (Environment.MEDIA_MOUNTED.equals(myState)) {
			try {
				breader = new BufferedReader(new FileReader(numberFile));
				String line = "";
				incomingCalls = new ArrayList<String>();
				while ((line = breader.readLine()) != null) {
					if (line.length() > 0) {
						incomingCalls.add(line);
					}
				}
			} catch (Exception e) {

			}
			
			Log.d("", "hej");
		}

		// Found out that the numbers were automatically saved in the system.
		// Thought I might as well use this one instead of my own file
		// since it saves the incoming calls anyway
		Uri call_list = Uri.parse("content://call_log/calls");
		Cursor cursor = getContentResolver().query(call_list, null, null, null,
				null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String number = cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.NUMBER));// for number
			int type = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.TYPE)));//
			if (CallLog.Calls.INCOMING_TYPE == type) {
				Log.d("incoming number ", "" + number);
				myCalls.add("" + number);
			} else {
				Log.d("something", "else");
			}
			cursor.moveToNext();
		}
		cursor.close();

		
		
		String incomingCallsArray[] = incomingCalls.toArray(new String[incomingCalls.size()]);
		Toast.makeText(getApplicationContext(),
				"incomingCallsArray " + incomingCallsArray.length, Toast.LENGTH_LONG).show();
		String calls[] = myCalls.toArray(new String[myCalls.size()]);
		//MyAdapter adapter = new MyAdapter(this, calls);
		MyAdapter adapter = new MyAdapter(this, incomingCallsArray);
		setListAdapter(adapter);

	}

	class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, String calls[]) {
			super(context, R.layout.adapted_row, calls);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row;
			// Create new row view object
			if (convertView == null) { // Create new row view object
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.adapted_row2, parent, false);
			} else {
				row = convertView;
			}

			TextView tvtitle = (TextView) row.findViewById(R.id.tvtitle);
			tvtitle.setText(this.getItem(position).toString());

			final String rowtelnr = this.getItem(position).toString();

			// Used an alert dialog and changed the options. Have just rewritten
			// the options
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("test", "ing");
					new AlertDialog.Builder(v.getContext())
							// Could also work with sending in mainactivity.this
							.setTitle("Choose action")
							.setMessage(
									"What do you want to do with this number?")
							.setPositiveButton("Call",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											Toast.makeText(
													getApplicationContext(),
													"make a call " + rowtelnr,
													Toast.LENGTH_SHORT).show();
											Uri number = Uri.parse("tel:"
													+ rowtelnr);
											Intent callIntent = new Intent(
													Intent.ACTION_DIAL, number);
											startActivity(callIntent);
										}
									})
							.setNegativeButton("Send message",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											Toast.makeText(
													getApplicationContext(),
													"send a message "
															+ rowtelnr,
													Toast.LENGTH_SHORT).show();
											Intent intent = new Intent(
													MessageSender.this,
													SendMessage.class);
											intent.putExtra("phone_number",
													rowtelnr);
											startActivity(intent);
										}
									})
							.setIcon(android.R.drawable.ic_dialog_alert).show();
				}
			});
			return row;
		}

		private void onClick() {
			// TODO Auto-generated method stub

		}

	}
}
