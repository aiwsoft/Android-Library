package com.aiwsoft.androidlibraries;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aiwsoft.androidlibraries.application.MyApp;
import com.aiwsoft.androidlibraries.custome.CustomActivity;
import com.aiwsoft.androidlibraries.utils.DipPixelHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.thefinestartist.finestwebview.FinestWebView;

public class UdemyCourcesActivity extends CustomActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udemy_cources);

        mAdView = findViewById(R.id.adView);
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
        actionBar.setTitle("Android courses");

        setTouchNClick(R.id.rl_avinash);
        setTouchNClick(R.id.rl_dheeraj);
        setTouchNClick(R.id.rl_bhavesh);
        setTouchNClick(R.id.txt_android);
        setTouchNClick(R.id.txt_details1);
        setTouchNClick(R.id.txt_android2);
        setTouchNClick(R.id.txt_details2);
        setTouchNClick(R.id.txt_android3);
        setTouchNClick(R.id.txt_details3);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_bhavesh || v.getId() == R.id.txt_android3 || v.getId() == R.id.txt_details3) {
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setMessage("Message!").setIcon(R.mipmap.ic_launcher)
                    .setMessage("To proceed with the course you need to copy the password and paste for the login details." +
                            "\nPlease remember login mail id is \"soni379bhavesh@gmail.com\"\nThank you");
            b.setPositiveButton("Copy & Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", "bsoni12345");
                    clipboard.setPrimaryClip(clip);
                    MyApp.showMassage(getContext(), "Password Copied");
                    dialog.dismiss();
                    openWebView("https://www.udemy.com/", "Android Java Masterclass");
                }
            }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else if (v.getId() == R.id.rl_dheeraj || v.getId() == R.id.txt_android2 || v.getId() == R.id.txt_details2) {
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setMessage("Message!").setIcon(R.mipmap.ic_launcher)
                    .setMessage("To proceed with the course you need to copy the password and paste for the login details." +
                            "\nPlease remember login mail id is\n \"dpandeyk.93@gmail.com\"\nThank you");
            b.setPositiveButton("Copy & Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", "Dheeraj@92");
                    clipboard.setPrimaryClip(clip);
                    MyApp.showMassage(getContext(), "Password Copied");
                    dialog.dismiss();
                    openWebView("https://www.udemy.com/", "Android Java Masterclass");
                }
            }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else if (v.getId() == R.id.rl_avinash || v.getId() == R.id.txt_android || v.getId() == R.id.txt_details1) {
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setMessage("Message!").setIcon(R.mipmap.ic_launcher)
                    .setMessage("To proceed with the course you need to copy the password and paste for the login details." +
                            "\nPlease remember login mail id is\n \"nitb.avi@gmail.com\"\nThank you");
            b.setPositiveButton("Copy & Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", "avinash@1302");
                    clipboard.setPrimaryClip(clip);
                    MyApp.showMassage(getContext(), "Password Copied");
                    dialog.dismiss();
                    openWebView("https://www.udemy.com/", "Android Java Masterclass");
                }
            }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
    }

    private Context getContext() {
        return UdemyCourcesActivity.this;
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

    public void openWebView(String url, String title) {
        showInterstitial();
        new FinestWebView.Builder(getContext())
                .titleDefault(title)
                .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                .gradientDivider(false)
                .dividerHeight(100)
                .toolbarColorRes(R.color.colorPrimary)
                .dividerColorRes(R.color.grey_hex_a3)
                .dividerHeight(0)
                .iconDefaultColorRes(R.color.white)
                .iconDisabledColorRes(R.color.grey_hex_f0)
                .iconPressedColorRes(R.color.white)
                .progressBarHeight(DipPixelHelper.getPixel(getContext(), 3))
                .progressBarColorRes(R.color.colorAccent)
                .backPressToClose(false)
                .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                .show(url);
    }
}
