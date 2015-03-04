package com.example.timesubway;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {
	
	ArrayList<String> arraylist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		arraylist = new ArrayList<String>();
		arraylist.add("1호선");
		arraylist.add("2호선");
		arraylist.add("3호선");
		arraylist.add("4호선");
		arraylist.add("5호선");
		arraylist.add("6호선");
		arraylist.add("7호선");
		arraylist.add("8호선");
		arraylist.add("9호선");
		arraylist.add("분당선");
		arraylist.add("인천");
		arraylist.add("신분당");
		arraylist.add("경의중앙선");
		arraylist.add("경춘선");
		arraylist.add("공항");
		arraylist.add("의정부");
		arraylist.add("수인선");
		arraylist.add("에버라인");
		


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_dropdown_item, arraylist); 

		Spinner sp = (Spinner) this.findViewById(R.id.spinner);
		sp.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
