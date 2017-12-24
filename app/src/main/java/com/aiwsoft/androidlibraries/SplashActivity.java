package com.aiwsoft.androidlibraries;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.aiwsoft.androidlibraries.custome.CustomActivity;

/**
 * Created by SONI on 12/24/2017.
 */

public class SplashActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, AllActivity.class));
                finish();
            }
        }, 2000);
    }
}
