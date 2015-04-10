package com.example.thirdassignment;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

//Class used for start menu
public class Start extends ListActivity{
	String classes[] = {"Maps", "Maps1", "MessageSender"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Start.this, android.R.layout.simple_list_item_1, classes));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//TODO Auto-generated method stub
		
		super.onListItemClick(l, v, position, id);
		String data = classes[position];
		try{
		Class selectedClass = Class.forName("com.example.thirdassignment." + data);
		Intent selectedIntent = new Intent(Start.this, selectedClass);
		startActivity(selectedIntent);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}		
	}

}
