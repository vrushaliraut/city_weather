package com.leftshift.wheatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class MainActivity extends Activity  implements OnClickListener
{
	ImageView ImgMultiplecity,ImgPercularcity;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        
    }


    private void initViews() 
    {
    	ImgMultiplecity = (ImageView)findViewById(R.id.ImgMultiplecity);
    	ImgMultiplecity.setOnClickListener(this);
    	
    	ImgPercularcity = (ImageView)findViewById(R.id.ImgSearch);
    	ImgPercularcity.setOnClickListener(this);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


	@Override
	public void onClick(View view)
	{
		switch (view.getId()) 
		{
			case R.id.ImgMultiplecity:
				Intent multiplecity = new Intent(MainActivity.this,MultipleCityListActivity.class);
				startActivity(multiplecity);
				break;
				
			case R.id.ImgSearch:
				Intent perticularcity = new Intent(MainActivity.this,PerticularCityActivity.class);
				startActivity(perticularcity);
				break;
				
			default:
				break;
		}
		
	}
}
