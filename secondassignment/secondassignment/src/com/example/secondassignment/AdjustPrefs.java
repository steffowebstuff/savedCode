package com.example.secondassignment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

//Class used for changing the settings in the countries exercise. 
public class AdjustPrefs extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adjust_prefs);
		Button sendbutton = (Button) findViewById(R.id.sendbutton);
		sendbutton.setOnClickListener(new ButtonClick());		
	}
	private class ButtonClick implements View.OnClickListener {
    	public void onClick(View v) {
    		
    		TextView errordisplay = (TextView)findViewById(R.id.errordisplay);
    		EditText rinput = (EditText)findViewById(R.id.rinput);
    		EditText binput = (EditText)findViewById(R.id.binput);
    		EditText ginput = (EditText)findViewById(R.id.ginput);
    		EditText sinput = (EditText)findViewById(R.id.sinput);
    		
    		final CheckBox redfont = (CheckBox) findViewById(R.id.redfont);
    		final CheckBox sortbox = (CheckBox) findViewById(R.id.cbsort);
    		final CheckBox orderbox = (CheckBox) findViewById(R.id.cborder);
    		

    		String red = rinput.getText().toString();
    		String blue = binput.getText().toString();
    		String green = ginput.getText().toString();
    		String size = sinput.getText().toString();
    		
    		boolean redfontchecked = redfont.isChecked();
    		boolean sortchecked = sortbox.isChecked();
    		boolean orderchecked = orderbox.isChecked();

    		Intent reply = new Intent();
    		reply.putExtra("redfontchecked", redfontchecked);
    		reply.putExtra("sortchecked", sortchecked);
    		reply.putExtra("orderchecked", orderchecked);
    		reply.putExtra("red", red);
    		reply.putExtra("blue", blue);
    		reply.putExtra("green", green);
    		reply.putExtra("size", size);
    		setResult(RESULT_OK,reply);
    		finish();   
        }
    }	

}
 
