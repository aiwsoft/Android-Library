package com.aiwsoft.androidlibraries;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aiwsoft.androidlibraries.application.MyApp;
import com.aiwsoft.androidlibraries.custome.CustomActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import de.cketti.mailto.EmailIntentBuilder;

public class LearnAndroidActivity extends CustomActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_android);

        mAdView = findViewById(R.id.adView);
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        handler = new Handler();
        handler.postDelayed(adsLoaderCallback, 100);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        handler.postDelayed(fullAdsLoaderCallback, 60000);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Library");

        setTouchNClick(R.id.txt_android);
        setTouchNClick(R.id.txt_java);
        setTouchNClick(R.id.txt_java_programs);
        setTouchNClick(R.id.txt_patterns);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_java) {
//            http://java.meritcampus.com/core-java-topics
//            http://www.studytonight.com/java/
            MyApp.showMassage(getContext(), "Coming Soon...");
        } else if (v.getId() == R.id.txt_android) {
            startActivity(new Intent(LearnAndroidActivity.this, UdemyCourcesActivity.class));
        } else if (v.getId() == R.id.txt_java_programs) {
            MyApp.showMassage(getContext(), "Coming Soon...");
        } else if (v.getId() == R.id.txt_patterns) {
            MyApp.showMassage(getContext(), "Coming Soon...");
        }
    }

    private Context getContext() {
        return LearnAndroidActivity.this;
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(adsLoaderCallback);
        handler.removeCallbacks(fullAdsLoaderCallback);
        super.onDestroy();
    }

    private Runnable adsLoaderCallback = new Runnable() {
        @Override
        public void run() {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            handler.postDelayed(adsLoaderCallback, 5000);
        }
    };

    private Runnable fullAdsLoaderCallback = new Runnable() {
        @Override
        public void run() {
            showInterstitial();
            handler.postDelayed(fullAdsLoaderCallback, 60000);
        }
    };

    public void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            goToNextLevel();
        }
    }

    private void goToNextLevel() {
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdClosed() {
                goToNextLevel();
            }
        });
        return interstitialAd;
    }
}
