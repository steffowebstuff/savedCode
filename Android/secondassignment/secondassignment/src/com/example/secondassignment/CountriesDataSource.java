package com.example.secondassignment;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Columns;
import android.util.Log;

//Helper class for the countries database
//Have pretty much copied the code from the instructions. 
public class CountriesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private CountriesDbHelper dbHelper;
	private String[] allColumns = { CountriesDbHelper.COLUMN_ID,
			CountriesDbHelper.COLUMN_COUNTRY, CountriesDbHelper.COLUMN_YEAR };

	/*
	 * private String[] allColumns = { CountriesDbHelper.COLUMN_ID,
	 * CountriesDbHelper.COLUMN_TASK, CountriesDbHelper.COLUMN_YEAR };
	 */

	public CountriesDataSource(Context context) {
		dbHelper = new CountriesDbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Country createCountry(String country, int year) {
		ContentValues values = new ContentValues();
		values.put(CountriesDbHelper.COLUMN_COUNTRY, country);
		values.put(CountriesDbHelper.COLUMN_YEAR, year);
		long insertId = database.insert(CountriesDbHelper.COUNTRIES_TABLE_NAME,
				null, values);
		Cursor cursor = database.query(CountriesDbHelper.COUNTRIES_TABLE_NAME,
				allColumns, CountriesDbHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Country newCountry = cursorToCountry(cursor);
		cursor.close();
		return newCountry;
	}

	public void deleteCountry(Country country) {
		long id = country.getId();
		System.out.println("Country deleted with id: " + id);
		database.delete(CountriesDbHelper.COUNTRIES_TABLE_NAME,
				CountriesDbHelper.COLUMN_ID + " = " + id, null);
	}

	public Country getCountry(long countryId) {
		String restrict = CountriesDbHelper.COLUMN_ID + "=" + countryId;
		Cursor cursor = database.query(true,
				CountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, restrict,
				null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			Country country = cursorToCountry(cursor);
			return country;
		}
		// Make sure to close the cursor
		cursor.close();
		return null;
	}

	public boolean updateCountry(long countryId, String country, int year) {
		ContentValues args = new ContentValues();
		args.put(CountriesDbHelper.COLUMN_COUNTRY, country);
		args.put(CountriesDbHelper.COLUMN_YEAR, year);

		String restrict = CountriesDbHelper.COLUMN_ID + "=" + countryId;
		return database.update(CountriesDbHelper.COUNTRIES_TABLE_NAME, args,
				restrict, null) > 0;
	}

	// Order is for sort by year, sort is for sort by asc
	public List<Country> getAllCountries(boolean order, boolean sort) {

		List<Country> countries = new ArrayList<Country>();
		Cursor cursor = null;

		//Descending based on letters
		if (sort == false && order == false) {
			cursor = database.query(CountriesDbHelper.COUNTRIES_TABLE_NAME,
					allColumns, null, null, null, null,
					CountriesDbHelper.COLUMN_COUNTRY + " DESC");

		}

		//Ascending based on letters
		else if (sort == true && order == false) {
			cursor = database.query(CountriesDbHelper.COUNTRIES_TABLE_NAME,
					allColumns, null, null, null, null,
					CountriesDbHelper.COLUMN_COUNTRY + " ASC");

		}

		//Descending based on year
		else if (sort == false && order == true) {
			cursor = database.query(CountriesDbHelper.COUNTRIES_TABLE_NAME,
					allColumns, null, null, null, null,
					CountriesDbHelper.COLUMN_YEAR + " DESC");

		}

		//Ascending based on year
		else {
			cursor = database.query(CountriesDbHelper.COUNTRIES_TABLE_NAME,
					allColumns, null, null, null, null,
					CountriesDbHelper.COLUMN_YEAR + " ASC");

		}

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Country country = cursorToCountry(cursor);
			countries.add(country);
			cursor.moveToNext();
		}
		cursor.close();
		return countries;

	}

	private Country cursorToCountry(Cursor cursor) {
		Country country = new Country();
		country.setId(cursor.getLong(0));
		country.setCountry(cursor.getString(1));
		country.setYear(cursor.getInt(2));
		return country;
	}
}
