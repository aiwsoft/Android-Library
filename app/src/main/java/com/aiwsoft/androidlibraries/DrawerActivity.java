package com.aiwsoft.androidlibraries;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.aiwsoft.androidlibraries.adapter.MenuAdapter;
import com.aiwsoft.androidlibraries.application.MyApp;
import com.aiwsoft.androidlibraries.custome.CustomActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;

import de.cketti.mailto.EmailIntentBuilder;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class DrawerActivity extends CustomActivity implements DuoMenuView.OnMenuClickListener {

    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private ArrayList<String> mTitles = new ArrayList<>();

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        // Initialize the views
        mViewHolder = new ViewHolder();
        // Handle toolbar actions
        handleToolbar();
        // Handle menu actions
        handleMenu();
        // Handle drawer actions
        handleDrawer();
        // Show main fragment in container
        goToFragment(new MainFragment(), false);
        mMenuAdapter.setViewSelected(-1, false);
//        setTitle(mTitles.get(0));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        handler = new Handler();
        handler.postDelayed(adsLoaderCallback, 100);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        handler.postDelayed(fullAdsLoaderCallback, 60000);
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
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
//        mLevelTextView.setText("Level " + (++mLevel));
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void loadInterstitial() {
//        MobileAds
        // Disable the next level button and load the ad.
//        mNextLevelButton.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
//        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
//        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }


    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        if (position == 0) {
            startActivity(new Intent(DrawerActivity.this, LearnAndroidActivity.class));
        } else if (position == 1) {
            MyApp.popMessage("About App", "Android Library is the application to explore available resources to make development easier.\n" +
                            "You will find the best available libraries that you can use easily in to you application. We will include android tutorials & useful coding skills into app soon." +
                            "\nYou will find demo for each library very soon, so you will identify exact use of the available library resource.",
                    DrawerActivity.this);
        } else if (position == 2) {
            shareApp();
        } else if (position == 3) {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (position == 4) {
            EmailIntentBuilder.from(DrawerActivity.this)
                    .to("myaiwsoft@gmail.com")
//                        .cc("bob@example.org")
//                        .bcc("charles@example.org")
                    .subject("Support mail")
                    .body("")
                    .start();
        } else if (position == 5) {
            MyApp.popMessage("About Us", "AIWSOFT is a startup software company, and our aim to help out most of the developers by this application, it's just " +
                    "initial step to enter in IT field. As you can see currently it's only showing the libraries that we can use to " +
                    "make your application more easy to develop. There is a lots of ready-made code available to save development time" +
                    " and to give better look to your app.\nJust keep in touch with this app, we are going to include android tutorials &" +
                    " magical coding things to grow your coding skills." +
                    "\nWe are trying our best to provide best available resources as much as possible " +
                    "\nThank you", DrawerActivity.this);
        }
//        setTitle(mTitles.get(position));

        // Set the right options selected
//        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
//        switch (position) {
//            default:
//                goToFragment(new MainFragment(), false);
//                break;
//        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
        }
    }

    private void shareApp() {
        String shareBody = "https://play.google.com/store/apps/details?" + "id=com.aiwsoft.androidlibraries";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android Library (Open it in Google Play Store to Download the Application)");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
    }

}
