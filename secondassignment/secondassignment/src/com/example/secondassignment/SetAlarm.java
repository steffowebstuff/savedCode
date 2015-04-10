package com.example.secondassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlarm extends Activity {

	// Class used to set the alarm
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int day) {
		Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year,
				Toast.LENGTH_LONG).show();
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Toast.makeText(getApplicationContext(), "time changed " + hourOfDay + "/" + minute + "/",
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_alarm);
		Button sendbutton = (Button) findViewById(R.id.sendbutton);
		sendbutton.setOnClickListener(new ButtonClick());
	}

	private class ButtonClick implements View.OnClickListener {
		public void onClick(View v) {
			TextView errordisplay = (TextView) findViewById(R.id.errordisplay);
			TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
			DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
			
			int tpHour = timePicker.getCurrentHour();
			int tpMinute = timePicker.getCurrentMinute();			
			int dpYear = datePicker.getYear();
			int dpMonth = datePicker.getMonth();
			int dpDay = datePicker.getDayOfMonth();

			Intent reply = new Intent();
			reply.putExtra("year", dpYear);
			reply.putExtra("month", dpMonth);
			reply.putExtra("day", dpDay);
			reply.putExtra("hour", tpHour);
			reply.putExtra("minute", tpMinute);
			setResult(RESULT_OK, reply);
			finish();
		}
	}
}
