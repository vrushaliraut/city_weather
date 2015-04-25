package com.leftshift.wheatherapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity implements AnimationListener 
{
	private ImageView splashImg;
	private TextView splashText;
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    
	// Animation
	Animation animMove, animFadeIn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
      // resource retrieved for drawing things to the screen
		Drawable d = getResources().getDrawable(R.drawable.home_layout_menu);
		getActionBar().setBackgroundDrawable(d);
		
		/***
		 *Retrieve  UI controls 
		 */
		 
		
		splashImg = (ImageView)findViewById(R.id.splashimg);
		splashText = (TextView)findViewById(R.id.splashText);
		
		// load the animation
		animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
		
		// set animation listener
		animMove.setAnimationListener(this);
		animFadeIn.setAnimationListener(this);
		
		splashImg.startAnimation(animMove);
		
		 new Handler().postDelayed(new Runnable() {
        	 
	            /*
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run()
	            {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	                Intent i = new Intent(SplashActivity.this, MainActivity.class);
	                startActivity(i);
	 
	                // close this activity
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
		
	}//end of onCreate method

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}//end of menu onCreate

	//Animation task start
	@Override
	public void onAnimationEnd(Animation animation) 
	{
		// Take any action after completing the animation

		// check for zoom in animation
		if (animation == animMove) 
		{
			splashText.setVisibility(View.VISIBLE);
			splashText.startAnimation(animFadeIn);
		}

	}
	
	/**
	 * Animation methods
	 * */
	
	@Override
	public void onAnimationRepeat(Animation animation) 
	{
		

	}

	@Override
	public void onAnimationStart(Animation animation)
	{
		

	}
	//end of animation listener
	
}//end of SplashActivity class
