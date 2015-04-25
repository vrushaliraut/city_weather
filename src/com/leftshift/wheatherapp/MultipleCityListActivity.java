package com.leftshift.wheatherapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MultipleCityListActivity extends Activity implements OnClickListener
{
	
	//initialize variables
	Button btnAdd_City,btnSelectedCities;
	EditText editAddCity;
	ImageView ImgAddCity;
	LinearLayout container;
	ArrayList<String> mylist = new ArrayList<String>();
	
	View row=null;
	View addView=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiple_city_list);

		InitViews();
		
	}
	/***
	 *Retrieve  UI controls 
	 */
	
	private void InitViews()
	{
		btnAdd_City = (Button)findViewById(R.id.btnAdd_City);

		btnSelectedCities = (Button)findViewById(R.id.btnSelectedCities);
		btnSelectedCities.setOnClickListener(this);
		editAddCity = (EditText)findViewById(R.id.editAddCity);
		ImgAddCity = (ImageView)findViewById(R.id.ImgAddCity);
		ImgAddCity.setOnClickListener(this);
		container = (LinearLayout)findViewById(R.id.container) ;


	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multiple_city_list, menu);
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
	/**
	 * Handling Onclick events
	 * */
	@Override
	public void onClick(View v)
	{
		int count = 0;
		switch (v.getId()) 
		{
			
		case R.id.ImgAddCity:
			TextView textcitynames;
			if(!editAddCity.getText().toString().equals(""))
			{
				LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				addView = layoutInflater.inflate(R.layout.citynames, null);

				textcitynames = (TextView)addView.findViewById(R.id.textcitynames);

				Toast.makeText(getApplicationContext(),""+editAddCity.getText().toString(),Toast.LENGTH_SHORT).show();

				textcitynames.setText(editAddCity.getText().toString());
				editAddCity.setText("");
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Please Enter Employee Name..",Toast.LENGTH_SHORT).show();
				return;
			}
			Button buttonRemove = (Button)addView.findViewById(R.id.remove);

			buttonRemove.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) 
				{
					((LinearLayout)addView.getParent()).removeView(addView);
				}
			});
			container.addView(addView);
			count = container.getChildCount(); 
			Log.d("Count values",""+count);

			//clear the arraylist
			mylist.clear();

			/**
			 *  get multiple city names from Linear layout and adding into city list 
			 * */
			
			for (int i = 0; i < count; i++)
			{
				row =container.getChildAt(i);

				textcitynames = (TextView)row.findViewById(R.id.textcitynames);

				String data = textcitynames.getText().toString();

				Log.d("Count values",""+data);
				mylist.add(data);
		
			}//end of for

			break;

		case R.id.btnSelectedCities:
			Intent i = new Intent(getApplicationContext(),CitiListActivity.class);
			i.putExtra("mylist",mylist);
			i.putExtra("count",count);
			startActivity(i);
			finish();
			//break;

		default:
			break;
		}

	}
}
