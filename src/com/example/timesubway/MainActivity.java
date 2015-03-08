package com.example.timesubway;

import java.util.Currency;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	String[] lines;
	String[] line1;
	String[] line2;
	int station_count;
	int minute;
	int second = 0;
	Handler handler;
	Thread thread1;

	ArrayAdapter<String> lines_adapter;
	ArrayAdapter<String> line1_adapter;
	ArrayAdapter<String> line2_adapter;
	Spinner lines_sp;
	Spinner departure_sp;
	Spinner destination_sp;
	TextView count;
	TextView timer;
	Button start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		count = (TextView)findViewById(R.id.count);
		timer = (TextView)findViewById(R.id.timer);
		start = (Button)findViewById(R.id.start);	

		handler = new Handler();

		// spinner 등록
		lines_sp = (Spinner) this.findViewById(R.id.spinner);
		departure_sp = (Spinner) this.findViewById(R.id.departure);
		destination_sp = (Spinner) this.findViewById(R.id.destination);

		setLine();
		setSpinnerAdapter();

		// spinner adapter 연결
		lines_sp.setAdapter(lines_adapter);

		setSpinnerListener();
	}

	void setLine() {
		// 정거장 등록
		lines = getResources().getStringArray(R.array.lines);
		line1 = getResources().getStringArray(R.array.line1);
		line2 = getResources().getStringArray(R.array.line2);
	}

	void setSpinnerAdapter() {
		// spinner adapter 등록
		lines_adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_dropdown_item, lines);
		line1_adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_dropdown_item, line1); 
		line2_adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_dropdown_item, line2);
	}

	void setSpinnerListener() {
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

		// 출발지 선택했을 떄
		departure_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long id) {

				int total_station = departure_sp.getAdapter().getCount();
				int departure_count = (int)departure_sp.getSelectedItemId();
				int destination_count = (int)destination_sp.getSelectedItemId();

				if(lines_sp.getSelectedItemId() == 1) {	// 2호선 일 때만
					if(departure_count > destination_count) {
						if(departure_count - destination_count > destination_count + (total_station - departure_count)) {
							station_count = destination_count + (total_station - departure_count);
						} else {
							station_count = departure_count - destination_count;
						}
					} else {
						if(destination_count - departure_count > departure_count + (total_station - destination_count)) {
							station_count = departure_count + (total_station - destination_count);
						} else {
							station_count = destination_count - departure_count;
						}
					}
				} else {
					station_count = (int)Math.abs(departure_sp.getSelectedItemId() - destination_sp.getSelectedItemId());					
				}
				
				count.setText("출발 : " + departure_sp.getSelectedItem().toString() + 
						"\n도착 : " + destination_sp.getSelectedItem().toString() +
						"\n역 개수 : " + station_count +
						"\n예상시간 : " + station_count * 2 + "분");
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});


		// 도착지 선택했을 때
		destination_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long id) {

				int total_station = departure_sp.getAdapter().getCount();
				int departure_count = (int)departure_sp.getSelectedItemId();
				int destination_count = (int)destination_sp.getSelectedItemId();

				if(lines_sp.getSelectedItemId() == 1) {	// 2호선 일 때만
					if(departure_count > destination_count) {
						if(departure_count - destination_count > destination_count + (total_station - departure_count)) {
							station_count = destination_count + (total_station - departure_count);
						} else {
							station_count = departure_count - destination_count;
						}
					} else {
						if(destination_count - departure_count > departure_count + (total_station - destination_count)) {
							station_count = departure_count + (total_station - destination_count);
						} else {
							station_count = destination_count - departure_count;
						}
					}
				} else {
					station_count = (int)Math.abs(departure_sp.getSelectedItemId() - destination_sp.getSelectedItemId());					
				}
				
				count.setText("출발 : " + departure_sp.getSelectedItem().toString() + 
						"\n도착 : " + destination_sp.getSelectedItem().toString() +
						"\n역 개수 : " + station_count +
						"\n예상시간 : " + station_count * 2 + "분");
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				second = 0;
				minute = station_count * 2;

				Log.v("wkdgusdn3", "" + 1);
				if(thread1 != null && thread1.isAlive()) {
					Log.v("wkdgusdn3", "" + 2);
					thread1.interrupt();
					Log.v("wkdgusdn3", "" + 3);
				}
				else {
					Log.v("wkdgusdn3", "" + 4);
				}
					
				thread1 = new Thread(new Runnable() {
					public void run() {

						while(minute != 0 || second != 0) {

							try{
								Thread.sleep(1000);
							} catch(Exception e) {
								e.printStackTrace();
							}

							if(second == 0) {
								minute--;
								second = 60;
							}

							handler.post(new Runnable() {
								public void run() {
									int departure_count = (int)departure_sp.getSelectedItemId();
									int destination_count = (int)destination_sp.getSelectedItemId();
									int count = (station_count - minute/2);
									
									Log.v("wkdgusdn3", "" + station_count  + " " + minute + " " + count);
									
									if(departure_sp.getSelectedItemId() < destination_sp.getSelectedItemId()) {
										timer.setText("" + minute + " 분 " + second + " 초 남았습니다." +
												"\n 현재역 : " + departure_sp.getItemAtPosition(departure_count + count));	
									} else {
										timer.setText("" + minute + " 분 " + second + " 초 남았습니다." +
												"\n 현재역 : " + destination_sp.getItemAtPosition(departure_count - count));	
									}
								}
							});

							second--;
						}
						
						handler.post(new Runnable() {
							public void run() {
								timer.setText("도착했습니다.");								
							}
						});
					}
				}); 
				
				thread1.start();
					
				
			}
		});

	}

	void calCount() {

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
