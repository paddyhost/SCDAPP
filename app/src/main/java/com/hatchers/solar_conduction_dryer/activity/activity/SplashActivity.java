package com.hatchers.solar_conduction_dryer.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hatchers.solar_conduction_dryer.R;
import com.hatchers.solar_conduction_dryer.activity.Preference_Manager.PrefManager;
import com.hatchers.solar_conduction_dryer.activity.user_registration.UserRegistration;

public class SplashActivity extends AppCompatActivity {


    private int SPLASH_TIME_OUT = 3000;
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashTimer();

    }
    private void splashTimer()
    {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                check();
            }
        }, SPLASH_TIME_OUT);

    }

    public void check()
    {
        prefManager = new PrefManager(this);
        if(!prefManager.isSkippedRegistration()) {
            if (!prefManager.isLoggedIn()) {

                Intent intent = new Intent(SplashActivity.this, UserRegistration.class);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(SplashActivity.this, Menu_Activity.class);
                startActivity(intent);
                finish();
            }
        }
        else
        {
            Intent intent = new Intent(SplashActivity.this, Menu_Activity.class);
            startActivity(intent);
            finish();
        }
    }

}
