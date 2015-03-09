package com.example.simplebaseadapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView1;
	private List<String> str=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init_data();
		initView();
		listView1.setAdapter(new TestFoodListAdapter(this, str));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void initView(){
		listView1=(ListView)findViewById(R.id.listView1);
	}
	public void init_data(){
		for (int i = 0; i < 100; i++) {
			str.add("²âÊÔ"+i);
		}
	}

}
