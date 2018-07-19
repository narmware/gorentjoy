package com.rohitsavant.gorentjoy.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rohitsavant.gorentjoy.R;

public class SplashActivity extends AppCompatActivity {
    private static int TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashActivity.this,NavigationMainActivity.class);
                startActivity(intent);
                finish();

                }
        },TIMEOUT);
    }
}
