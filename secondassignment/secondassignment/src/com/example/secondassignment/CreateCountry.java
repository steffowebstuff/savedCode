package com.example.secondassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateCountry extends Activity {
	// Class for creating a new country. Belongs to Ex1

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_country);
		Button sendbutton = (Button) findViewById(R.id.sendbutton);
		sendbutton.setOnClickListener(new ButtonClick());
	}

	private class ButtonClick implements View.OnClickListener {
		public void onClick(View v) {

			try {
				TextView errordisplay = (TextView) findViewById(R.id.errordisplay);
				EditText reader = (EditText) findViewById(R.id.countryinput);
				EditText yearreader = (EditText) findViewById(R.id.yearinput);
				String name = reader.getText().toString();
				String year = yearreader.getText().toString();

				Intent reply = new Intent();
				reply.putExtra("result", name);
				reply.putExtra("year", year);

				setResult(RESULT_OK, reply);
				System.out.println(name);
				finish();
			} catch (Exception e) {
			}
			;
		}
	}

	public void onSaveInstanceState(Bundle savedInstanceState) {

		try {
			TextView numberdisplay = (EditText) findViewById(R.id.numberdisplay);
			TextView answerdisplay = (TextView) findViewById(R.id.answerdisplay);

			int number = Integer.valueOf(numberdisplay.getText().toString());
		} catch (Exception e) {
		}

		super.onSaveInstanceState(savedInstanceState);
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		TextView answerdisplay = (TextView) findViewById(R.id.answerdisplay);
		int number = 100;
		try {
			TextView numberdisplay = (TextView) findViewById(R.id.numberdisplay);
			number = savedInstanceState.getInt("number");
			numberdisplay.setText("" + number);
			answerdisplay.setText("");
		} catch (Exception e) {
		}
	}
}
