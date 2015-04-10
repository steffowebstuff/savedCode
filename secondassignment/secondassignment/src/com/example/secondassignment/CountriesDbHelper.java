package com.example.secondassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Class taken från course pdf
public class CountriesDbHelper extends SQLiteOpenHelper {

	public static final String COUNTRIES_TABLE_NAME = "tasks";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COUNTRY = "task";
	public static final String COLUMN_YEAR = "year";

	private static final String DATABASE_NAME = "tasks.db";
	private static final int DATABASE_VERSION = 2;

	/*
	private static final String DATABASE_CREATE = "create table "
			+ TASKS_TABLE_NAME + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TASK
			+ " text not null);";*/
	
	private static final String DATABASE_CREATE = "create table "
			+ COUNTRIES_TABLE_NAME + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_COUNTRY
			+ " text not null, " + COLUMN_YEAR + " integer);";

	public CountriesDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CountriesDbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + COUNTRIES_TABLE_NAME);
		onCreate(db);
	}

}
