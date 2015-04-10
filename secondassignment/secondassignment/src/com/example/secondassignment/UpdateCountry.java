package com.example.secondassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateCountry extends Activity {

	// Class for updating country in Ex1
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_country);
		Button sendbutton = (Button) findViewById(R.id.sendbutton);
		sendbutton.setOnClickListener(new ButtonClick());

		TextView errordisplay = (TextView) findViewById(R.id.errordisplay);
		TextView idfield = (TextView) findViewById(R.id.idfield);

		Intent updateintent = getIntent();
		Bundle updatebundle = updateintent.getExtras();

		long countryid = (Long) updatebundle.get("countryid");
		String countryName = (String) updatebundle.get("countryname");
		EditText reader = (EditText) findViewById(R.id.countryinput);
		reader.setText(countryName);
		idfield.setText("" + countryid);

	}

	private class ButtonClick implements View.OnClickListener {
		public void onClick(View v) {

			TextView idfield = (TextView) findViewById(R.id.idfield);
			EditText reader = (EditText) findViewById(R.id.countryinput);
			EditText yearreader = (EditText) findViewById(R.id.yearinput);

			String name = reader.getText().toString();
			String year = yearreader.getText().toString();
			String cid = idfield.getText().toString();

			Intent reply = new Intent();
			reply.putExtra("name", name);
			reply.putExtra("year", year);
			reply.putExtra("countryid", cid);

			setResult(RESULT_OK, reply);
			System.out.println(name);
			finish();
		}
	}
}
