package com.leftshift.wheatherapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CitiListActivity extends Activity 
{


	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citi_list);

		Intent in = getIntent();
		ArrayList<String> mylist = in.getStringArrayListExtra("mylist");
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_list_item_1, mylist);
     
		 listView.setAdapter(adapter);
		
		 listView.setOnItemClickListener(new OnItemClickListener() 
		 {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) 
	            {
	                
	                String item = ((TextView)view).getText().toString();
	                
	                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
	            	Intent i = new Intent(getApplicationContext(),JsonParsing.class);
	    			i.putExtra("city_name",item);
	    			startActivity(i);
	    			finish();
	    		
	                
	            }
	        });
		
	  
	}//end of oncreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.citi_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}//end of citylist activity
