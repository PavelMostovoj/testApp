package com.example.testApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by root on 04.08.15.
 */
public class SplashScreenActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getActionBar().hide();
        setContentView(R.layout.splash_screen);

        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(2000);

                    startActivity(new Intent("com.example.CLEARSCREEN"));

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                }
            }
        };
        logoTimer.start();

    }
}
