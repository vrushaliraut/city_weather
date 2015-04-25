package com.leftshift.wheatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class JsonParsing extends Activity
{

	private ProgressDialog prgDialog;
	String selectcityname;
	ArrayList<String> arraylist = new ArrayList<String>();
	
	public static final int progress_bar_type = 0;
	private static String appId = "a6f0741300da518e5092d864a9217274";
	private static String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	JSONObject json = null;
	String str = "";
	HttpResponse response;
	
	HttpClient myClient;
	

	HttpPost myConnection;
	ArrayAdapter<String> adapter;
	GridView weatherGrid;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsonpasring);
	
		
		Intent intentcityname = getIntent();

		selectcityname = intentcityname.getStringExtra("city_name");	

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy); // for abnormal termination

		new DownloadWeatherfromInternet().execute(weatherUrl);


	} 


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
		case progress_bar_type:
			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("Downloading weatherforecast file. Please wait...");
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			prgDialog.setCancelable(false);
			prgDialog.show();
			return prgDialog;
		default:
			return null;
		}
	}

	class DownloadWeatherfromInternet extends AsyncTask<String,Void, String>
	{ 


		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}


		@Override
		protected String doInBackground(String... params)
		{


			myClient = new DefaultHttpClient();
			

			myConnection = new HttpPost(""+weatherUrl);

			try
			{
				response = myClient.execute(myConnection);
				
				str = getTheForecastData(selectcityname);

			} catch (ClientProtocolException e)
			{
				e.printStackTrace();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			try
			{
				Log.e("str", str);
				JSONObject jsonObj = new JSONObject(str);

				JSONObject city = jsonObj.getJSONObject("city");
				String cityname = city.getString("name");
				arraylist.add("City Name");
				arraylist.add(cityname);
				Log.e("city", cityname);

				// Getting JSON Array node
				JSONArray list = jsonObj.getJSONArray("list");
				int j = 1;
				// looping through All Contacts
				for (int i = 1; i < list.length(); i++) 
				{

					JSONObject c = list.getJSONObject(i);
					String dte = c.getString("dt");
					String speed = c.getString("speed");
					String deg = c.getString("deg");
					String clouds = c.getString("clouds");
					String snow;
					if (!c.isNull("snow")) 
					{
						snow = c.getString("snow");
					} 
					else 
					{
						snow = "0";
					}
					arraylist.add("Day");
					arraylist.add("" + j);

					arraylist.add("Dte");
					arraylist.add(dte);
					arraylist.add("Speed");
					arraylist.add(speed);
					arraylist.add("Clouds");
					arraylist.add(clouds);
					arraylist.add("Snow");
					arraylist.add(snow);
					Log.e("dte", dte);
					Log.e("speed", speed);
					Log.e("deg", deg);
					Log.e("clouds", clouds);
					Log.e("snow", snow);
					j++;
				}

				weatherGrid = (GridView) findViewById(R.id.weatherGrid);

				 adapter = new ArrayAdapter<String>(
						JsonParsing.this, android.R.layout.simple_list_item_1,
						arraylist);
			

				JSONObject city1 = jsonObj.getJSONObject("temp");
				String day = city1.getString("day");

				Log.e("day", day);

				JSONArray jArray = new JSONArray(str);
				json = jArray.getJSONObject(0);

			}
			catch (JSONException e) 
			{
				e.printStackTrace();
			}//end of catch
			return null;

		}//end of doinbackground
		public String getTheForecastData(String tempCity) 
		{
			HttpURLConnection myCon = null;
			InputStream is = null;

			try 
			{
				myCon = (HttpURLConnection) (new URL(weatherUrl + tempCity
						+ "&cnt=14&APPID=" + appId)).openConnection();
				myCon.setRequestMethod("GET");
				myCon.setDoInput(true);
				myCon.setDoOutput(true);
				myCon.connect();

				StringBuffer mybuff = new StringBuffer();
				is = myCon.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String tempString;
				while ((tempString = br.readLine()) != null) 
				{
					mybuff.append(tempString + "\r\n");
				}

				String bufferString = mybuff.toString();

				is.close();
				myCon.disconnect();
				return bufferString;

			} 
			catch (MalformedURLException e)
			{

				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally 
			{
				try
				{
					is.close();
				}
				catch (Exception e2) 
				{
				}
				try 
				{
					myCon.disconnect();
				} 
				catch (Exception e3)
				{
				}
			}
			return null;
		}//end of getTheForecastData
		
	
		protected void onProgressUpdate(String... progress)
		{
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
			

		}//end of onProgressUpdate

		@Override
		protected void onPostExecute(String file_url)
		{
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			weatherGrid.setAdapter(adapter);
			Toast.makeText(getApplicationContext(),"Download complete, Selcted city's weather", Toast.LENGTH_LONG).show();
		
		}

	}//
}//End of jsonparsing



