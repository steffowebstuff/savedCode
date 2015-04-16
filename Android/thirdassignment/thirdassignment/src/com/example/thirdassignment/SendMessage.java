package com.example.thirdassignment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends Activity {

	// Activity class before sending number, used for exercise 2
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_message);
		Button send_button = (Button) findViewById(R.id.send_button);
		final EditText message_input = (EditText) findViewById(R.id.message_input);
		Intent i = getIntent();

		if (i.getExtras() != null) {
			Bundle b = i.getExtras();
			String number = b.getString("phone_number");
			message_input.setText(number);
			/*Toast.makeText(SendMessage.this, "Extras here " + number,
					Toast.LENGTH_SHORT).show();*/
		} else {
			Toast.makeText(SendMessage.this, "No Extras", Toast.LENGTH_SHORT)
					.show();
		}

		send_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String message = message_input.getText().toString();
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
					sharingIntent.setType("text/plain");
					sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "subject");
					sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
					startActivity(Intent.createChooser(sharingIntent, " " ));
				} catch (Exception e) {
				}
			}
		});

	}

}
