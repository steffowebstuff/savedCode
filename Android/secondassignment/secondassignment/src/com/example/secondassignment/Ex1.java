package com.example.secondassignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.preference.PreferenceManager;

public class Ex1 extends ListActivity {
	// This is the primary class where the two first exercises with the database
	// are to be found.
	// Also uses Country, CreateCountry and UpdateCountry for the country input
	// and AdjustPrefs for the layout settings

	private Activity main_activity;
	public int textcolor = 1;
	public int textsize = 10;

	boolean neworderbox = false;
	boolean newsortbox = false;
	boolean newredfont = false;

	static final String[] allowedCountries = new String[] { "Afghanistan",
			"Albania", "Algeria", "American Samoa", "Andorra", "Angola",
			"Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
			"Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
			"Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
			"Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
			"Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil",
			"British Indian Ocean Territory", "British Virgin Islands",
			"Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cote d'Ivoire",
			"Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
			"Central African Republic", "Chad", "Chile", "China",
			"Christmas Island", "Cocos (Keeling) Islands", "Colombia",
			"Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia",
			"Cuba", "Cyprus", "Czech Republic",
			"Democratic Republic of the Congo", "Denmark", "Djibouti",
			"Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt",
			"El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
			"Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji",
			"Finland", "Former Yugoslav Republic of Macedonia", "France",
			"French Guiana", "French Polynesia", "French Southern Territories",
			"Gabon", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
			"Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
			"Guinea", "Guinea-Bissau", "Guyana", "Haiti",
			"Heard Island and McDonald Islands", "Honduras", "Hong Kong",
			"Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
			"Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
			"Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
			"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
			"Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Madagascar",
			"Malawi", "Malaysia", "Maldives", "Mali", "Malta",
			"Marshall Islands", "Martinique", "Mauritania", "Mauritius",
			"Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
			"Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
			"Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
			"New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
			"Niue", "Norfolk Island", "North Korea", "Northern Marianas",
			"Norway", "Oman", "Pakistan", "Palau", "Panama",
			"Papua New Guinea", "Paraguay", "Peru", "Philippines",
			"Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
			"Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe",
			"Saint Helena", "Saint Kitts and Nevis", "Saint Lucia",
			"Saint Pierre and Miquelon", "Saint Vincent and the Grenadines",
			"Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles",
			"Sierra Leone", "Singapore", "Slovakia", "Slovenia",
			"Solomon Islands", "Somalia", "South Africa",
			"South Georgia and the South Sandwich Islands", "South Korea",
			"Spain", "Sri Lanka", "Sudan", "Suriname",
			"Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
			"Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
			"The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga",
			"Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
			"Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
			"Ukraine", "United Arab Emirates", "United Kingdom",
			"United States", "United States Minor Outlying Islands", "Uruguay",
			"Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam",
			"Wallis and Futuna", "Western Sahara", "Yemen", "Yugoslavia",
			"Zambia", "Zimbabwe" };

	ArrayList<String> myCountries = new ArrayList<String>();
	private CountriesDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// The settings that are saved in the preference manager
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int sizeint = prefs.getInt("sizeint", this.textsize);
		int colorint = prefs.getInt("colorintsave", this.textcolor);
		boolean ordersave = prefs.getBoolean("orderboxsave", this.neworderbox);
		boolean yearsave = prefs.getBoolean("sortboxsave", this.newsortbox);
		boolean redfontsave = prefs.getBoolean("redfontsave", this.newredfont);

		this.textsize = sizeint;
		this.textcolor = colorint;
		this.neworderbox = ordersave;
		this.newsortbox = yearsave;
		this.newredfont = redfontsave;

		super.onCreate(savedInstanceState);
		datasource = new CountriesDataSource(this);
		datasource.open();

		List<Country> countryArray = datasource.getAllCountries(
				this.neworderbox, this.newsortbox);
		MyAdapter adapter = new MyAdapter(this,
				countryArray.toArray(new Country[countryArray.size()]));
		adapter.setcolornumber(this.textcolor);
		adapter.settextsize(this.textsize);
		adapter.setredcolour(this.newredfont);
		setListAdapter(adapter);
		main_activity = this;
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_country:
			Intent intent = new Intent(main_activity, CreateCountry.class);
			main_activity.startActivityForResult(intent, 0);
			return true;

		case R.id.add_colorsize:
			try {
				Intent intent2 = new Intent(main_activity, AdjustPrefs.class);
				main_activity.startActivityForResult(intent2, 1);
				return true;
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error " + e,
						Toast.LENGTH_LONG).show();
			}
		}

		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent result) {
		// Result from create country
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String name = result.getStringExtra("result");
				boolean found = false;

				// TODO: Modify this validation and put it in updatecountries as
				// well
				for (String country : allowedCountries) {
					if (country.equals(name)) {
						found = true;

					}
				}

				if (found == true) {
					String year = result.getStringExtra("year");
					int newyear = Integer.parseInt(year);
					datasource.createCountry(name, newyear);
					List<Country> countryArray = datasource.getAllCountries(
							this.neworderbox, this.newsortbox);
					MyAdapter adapter = new MyAdapter(this,
							countryArray.toArray(new Country[countryArray
									.size()]));
					adapter.setcolornumber(this.textcolor);
					adapter.settextsize(this.textsize);
					adapter.setredcolour(this.newredfont);
					setListAdapter(adapter);

				} else {
					Toast.makeText(
							getApplicationContext(),
							"Country not found. Remember to capitilize first letter. Plz Try again",
							Toast.LENGTH_LONG).show();
				}

				datasource = new CountriesDataSource(this);
				datasource.open();
				List<Country> countryArray = datasource.getAllCountries(
						this.neworderbox, this.newsortbox);
				MyAdapter adapter = new MyAdapter(this,
						countryArray.toArray(new Country[countryArray.size()]));
				adapter.setcolornumber(this.textcolor);
				adapter.settextsize(this.textsize);
				adapter.setredcolour(this.newredfont);
				setListAdapter(adapter);

			}
		}
		// If settings are changed
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				String red = result.getStringExtra("red");
				String blue = result.getStringExtra("blue");
				String green = result.getStringExtra("green");
				String size = result.getStringExtra("size");

				int newred = Integer.parseInt(red);
				int newgreen = Integer.parseInt(green);
				int newblue = Integer.parseInt(blue);
				int newsize = Integer.parseInt(size);

				if (newred > 255) {
					newred = 255;
					Toast.makeText(getApplicationContext(),
							"Red value to high, now set to 255",
							Toast.LENGTH_SHORT).show();
				}

				if (newgreen > 255) {
					newgreen = 255;
					Toast.makeText(getApplicationContext(),
							"Green value to high, now set to 255",
							Toast.LENGTH_SHORT).show();
				}

				if (newblue > 255) {
					newblue = 255;
					Toast.makeText(getApplicationContext(),
							"Blue value to high, now set to 255",
							Toast.LENGTH_SHORT).show();
				}

				if (newsize > 50) {
					newsize = 50;
					Toast.makeText(getApplicationContext(),
							"Size value to high, now set to 50",
							Toast.LENGTH_SHORT).show();
				}

				this.textcolor = Color.rgb(newred, newgreen, newblue);
				this.textsize = newsize;
				this.neworderbox = result
						.getBooleanExtra("orderchecked", false);
				this.newsortbox = result.getBooleanExtra("sortchecked", false);
				this.newredfont = result.getBooleanExtra("redfontchecked",
						false);
				datasource = new CountriesDataSource(this);
				datasource.open();

				List<Country> countryArray = datasource.getAllCountries(
						this.neworderbox, this.newsortbox);

				MyAdapter adapter = new MyAdapter(this,
						countryArray.toArray(new Country[countryArray.size()]));

				adapter.setcolornumber(this.textcolor);
				adapter.settextsize(this.textsize);
				adapter.setredcolour(this.newredfont);
				setListAdapter(adapter);

			}
		}

		// Update Country. Do not use the same requirements for the country check here, since the user might want to change the country name 
		// to something that is not on the list
		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {

				String updatename = result.getStringExtra("name");
				String updateyear = result.getStringExtra("year");
				String updatecid = result.getStringExtra("countryid");
				int newyear = Integer.parseInt(updateyear);

				long newcid = Long.parseLong(updatecid);
				datasource.updateCountry(newcid, updatename, newyear);
				datasource = new CountriesDataSource(this);
				datasource.open();

				List<Country> countryArray = datasource.getAllCountries(
						this.neworderbox, this.newsortbox);

				MyAdapter adapter = new MyAdapter(this,
						countryArray.toArray(new Country[countryArray.size()]));
				adapter.setcolornumber(this.textcolor);
				adapter.settextsize(this.textsize);
				adapter.setredcolour(this.newredfont);
				setListAdapter(adapter);
			}
		}

	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	class MyAdapter extends ArrayAdapter<Country> {

		public MyAdapter(Context context, Country[] countries) {
			super(context, R.layout.adapted_country_row, countries);

		}

		public int cn = 0;
		public int ts = 0;
		public boolean rc = false;

		public void setcolornumber(int cn) {
			this.cn = cn;
		}

		public void setredcolour(boolean rc) {
			this.rc = rc;
		}

		public void settextsize(int ts) {
			this.ts = ts;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View row;

			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.adapted_country_row, parent,
						false);
			} else {
				row = convertView;
			}

			int colornumber = this.cn;
			int textsize = this.ts;
			boolean rc = this.rc;
			final long myid = this.getItem(position).getId();
			final String myCountry = this.getItem(position).getCountry();

			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new AlertDialog.Builder(v.getContext())
							.setTitle("Choose action")
							.setMessage(
									"What do you want to do with this country?")
							.setPositiveButton("Edit country",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											Toast.makeText(
													getApplicationContext(),
													"Country to be edited ",
													Toast.LENGTH_SHORT).show();

											Intent intent3 = new Intent(
													main_activity,
													UpdateCountry.class);
											intent3.putExtra("countryid", myid);
											intent3.putExtra("countryname",
													myCountry);
											main_activity
													.startActivityForResult(
															intent3, 2);
										}
									})
							.setNegativeButton("Delete country",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											Toast.makeText(
													getApplicationContext(),
													"Country to be deleted ",
													Toast.LENGTH_SHORT).show();
											Country countrytodelete = datasource
													.getCountry(myid);
											datasource
													.deleteCountry(countrytodelete);

											List<Country> countryArray = datasource
													.getAllCountries(
															Ex1.this.neworderbox,
															Ex1.this.newsortbox);
											MyAdapter adapter = new MyAdapter(
													Ex1.this,
													countryArray
															.toArray(new Country[countryArray
																	.size()]));
											adapter.setcolornumber(Ex1.this.textcolor);
											adapter.settextsize(Ex1.this.textsize);
											adapter.setredcolour(Ex1.this.newredfont);
											setListAdapter(adapter);
										}
									}).show();
				}

			});

			TextView tvtitle = (TextView) row.findViewById(R.id.tvtitle);
			TextView tvyear = (TextView) row.findViewById(R.id.tvyear);
			LinearLayout ll2 = (LinearLayout) row.findViewById(R.id.ll2);
			tvtitle.setText(this.getItem(position).getCountry().toString());
			String yearstring = "" + this.getItem(position).getYear();
			tvyear.setText(yearstring);

			try {
				tvtitle.setBackgroundColor(colornumber);
				tvyear.setBackgroundColor(colornumber);
				ll2.setBackgroundColor(colornumber);
				tvtitle.setTextSize(textsize);
				tvyear.setTextSize(textsize);
				if (rc == true) {
					tvyear.setTextColor(Color.RED);
					tvtitle.setTextColor(Color.RED);
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "exception" + e,
						Toast.LENGTH_SHORT).show();
			}

			return row;
		}

		private void onClick() {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onResume() { // Activity comes in the foreground
		super.onResume();

	}

	@Override
	protected void onStop() {
		super.onStop();
		// Using the sharedpreferences to save the temporary settings
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("colorintsave", this.textcolor);
		edit.putInt("sizeint", this.textsize);
		edit.putBoolean("orderboxsave", this.neworderbox);
		edit.putBoolean("sortboxsave", this.newsortbox);
		edit.putBoolean("redfontsave", this.newredfont);
		edit.commit();
	}
}
