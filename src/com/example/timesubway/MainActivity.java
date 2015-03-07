package com.example.timesubway;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {
	
	String[] lines;
	String[] line1;
	String[] line2;
	ArrayAdapter<String> line1_adapter;
	ArrayAdapter<String> line2_adapter;
	Spinner departure_sp;
	Spinner destination_sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lines = getResources().getStringArray(R.array.lines);
		line1 = getResources().getStringArray(R.array.line1);
		line2 = getResources().getStringArray(R.array.line2);

		ArrayAdapter<String> lines_adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_dropdown_item, lines); 
		Spinner lines_sp = (Spinner) this.findViewById(R.id.spinner);
		lines_sp.setAdapter(lines_adapter);
		
		departure_sp = (Spinner) this.findViewById(R.id.departure);
		destination_sp = (Spinner) this.findViewById(R.id.destination);
		
		line1_adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_dropdown_item, line1); 
		line2_adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_dropdown_item, line2);
		
		
		
		lines_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View arg1,
		            int position, long id) {
		    	
		    	switch(position) {
		    	case 0 : 
		    		departure_sp.setAdapter(line1_adapter);
		    		destination_sp.setAdapter(line1_adapter);
		    		break;
		    	case 1 :
		    		departure_sp.setAdapter(line2_adapter);
		    		destination_sp.setAdapter(line2_adapter);
		    		break;
		    	default :
		    		departure_sp.setAdapter(null);
		    		destination_sp.setAdapter(null);
		    		break;
		    	}
		    }
		    
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
		    
		    }
		});	
		
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
