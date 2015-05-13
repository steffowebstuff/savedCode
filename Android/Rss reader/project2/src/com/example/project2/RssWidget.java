package com.example.project2;

import java.util.ArrayList;
import java.util.List;
import com.example.project2.RssReader.IDone;
//import com.example.testthree.UpdateCountry;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//The activity that opens up each time user press the rss button on a widget.
public class RssWidget extends ListActivity {

	private RssReader rss_reader = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {

		}
		Intent intent = getIntent();
		//Takes care of the values from the shared prefs that are set in the configuration class
		if (intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();
			String topic = bundle.getString("kc");
			String pc = bundle.getString("pc");
			String ec = bundle.getString("ec");
			/*Toast.makeText(getApplicationContext(), "Topic  " + topic,
					Toast.LENGTH_SHORT).show();*/

			rss_reader = new RssReader();
			rss_reader.ReceiveForecast2(new IDone() {
				@Override
				public void onDone(ArrayList<RssObject> rss_list) {
					RssObject rss_array[] = rss_list
							.toArray(new RssObject[rss_list.size()]);
					MyAdapter adapter = new MyAdapter(RssWidget.this, rss_array);
					setListAdapter(adapter);

				}
			}, topic, pc, ec); 
		}
	}

	class MyAdapter extends ArrayAdapter<RssObject> {

		public MyAdapter(Context context, RssObject rss_object[]) {
			super(context, R.layout.adapted_row, rss_object);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row;
			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.adapted_row, parent, false);
			} else {
				row = convertView;
			}

			TextView tvtitle = (TextView) row.findViewById(R.id.tvtitle);
			TextView tvsource = (TextView) row.findViewById(R.id.tvsource);
			TextView tvlink = (TextView) row.findViewById(R.id.tvlink);
			TextView tvtime = (TextView) row.findViewById(R.id.tvtime);

			tvtitle.setText(this.getItem(position).GetTitle().toString());
			tvsource.setText(this.getItem(position).GetSource().toString());
			tvlink.setText(this.getItem(position).GetUrl());
			tvtime.setText(this.getItem(position).GetTime());
			final String mylink = this.getItem(position).GetUrl();

			//Event that makes it possible to go to the webpage that represents each ones of the news
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(mylink));
					startActivity(intent);

				}

			});

			return row;
		}

	}

}
