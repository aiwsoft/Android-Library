package com.aiwsoft.androidlibraries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.aiwsoft.androidlibraries.adapter.AllAdapter;
import com.aiwsoft.androidlibraries.custome.CustomActivity;
import com.aiwsoft.androidlibraries.utils.DipPixelHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SONI on 12/20/2017.
 */

public class AllActivity extends CustomActivity {

    private RecyclerView rv_libs;
    private AllAdapter adapter;
    private TextView toolbar_title;
    private Toolbar toolbar;
    private List<String> dataList;
    private int clickDetailsCounter = 0;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_all);
        rv_libs = findViewById(R.id.rv_libs);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Libraries");
        actionBar.setTitle("");
        dataList = getAllData();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Collections.sort(dataList, new Comparator<String>() {
            @Override
            public int compare(String text1, String text2) {
                return text1.compareToIgnoreCase(text2);
            }
        });
        adapter = new AllAdapter(dataList, getContext());
        rv_libs.setLayoutManager(new LinearLayoutManager(getContext()));
        mInterstitialAd = newInterstitialAd();
        rv_libs.setAdapter(adapter);
        loadInterstitial();
    }

    private Context getContext() {
        return AllActivity.this;
    }

    private SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getContext(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void filter(String text) {
        List<String> temp = new ArrayList();
        for (String d : dataList) {
            if (d.contains(text)) {
                temp.add(d);
            }
        }
        adapter.updateList(temp);
        rv_libs.scheduleLayoutAnimation();
    }

    public void openWebView(String data) {
        if (clickDetailsCounter >= 3) {
            clickDetailsCounter = 0;
            showInterstitial();
            return;
        }
        ++clickDetailsCounter;
        new FinestWebView.Builder(this)
                .titleDefault(data.split("@@")[0])
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
                .progressBarHeight(DipPixelHelper.getPixel(this, 3))
                .progressBarColorRes(R.color.colorAccent)
                .backPressToClose(false)
                .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                .show(data.split("@@")[1]);
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

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private List<String> getAllData() {
        List<String> allData = new ArrayList<>();
        allData.add("Month Calendar (Widget)" +
                "@@https://github.com/romannurik/Android-MonthCalendarWidget" +
                "@@A simple example of a responsive Month Calendar app widget for Android.");
        allData.add("BLE (Bluetooth low energy)" +
                "@@https://blog.stylingandroid.com/category/bluetooth/" +
                "@@All the examples by series to learn BLE concept and communication of the BLE sensor with the android application.");
        allData.add("MVP Example" +
                "@@https://github.com/antoniolg/androidmvp" +
                "@@MVP Android Example used to explain how to use this pattern in our Android apps. This code was created to support an article explanation.");
        allData.add("Filling Effect" +
                "@@https://github.com/fedestyla/FillingEffect" +
                "@@Create a filling effect for an ImageView");
        allData.add("Animations with fragments" +
                "@@https://cyrilmottier.com/2014/05/20/custom-animations-with-fragments/" +
                "@@Cool animations techniques to give awesome feel to android UI using fragments.");
        allData.add("Shadow effect with custom shapes" +
                "@@https://cyrilmottier.com/2014/05/20/custom-animations-with-fragments/" +
                "@@It’s easy to create a shadow for your views using custom shapes.\n" +
                "The idea it’s that you create the shadow layer as background first and the content layers on top of that.");
        allData.add("Email Intent Builder" +
                "@@https://github.com/cketti/EmailIntentBuilder" +
                "@@An Android Library for the creation of SendTo Intents with mailto: URI.");
        allData.add("Instagram with Material Design" +
                "@@https://github.com/frogermcs/InstaMaterial/tree/0b321ce58c6d98cf8cd8e461fe2db004fc9ab17c" +
                "@@Source code for implementation of Instagram with Material Design (based on Emmanuel Pacamalan's concept).");
        allData.add("Sliding Pane Layout (Partial)" +
                "@@http://blog.sqisland.com/2015/01/partial-slidingpanelayout.html" +
                "@@The side pane is always visible, showing icons when collapsed, cross fading to more details when expanded. How is it implemented?\n" +
                "So first observation is that the main pane slides when the side pane expands, so  it is not a NavigationDrawer. Let's try a SlidingPaneLayout.");
        allData.add("RecyclerView: Autofit grid" +
                "@@http://blog.sqisland.com/2014/12/recyclerview-autofit-grid.html" +
                "@@After making a RecyclerView grid with a header, we need to make it auto fit as well. So to achieve this, define the size of each item and let the system compute the spanCount automatically.*");
        allData.add("RecyclerView: Autofit grid" +
                "@@https://blog.joanzapata.com/robust-architecture-for-an-android-app/" +
                "@@This post will give possible way to structure an app with a good balance between flexibility, readability and robustness");
        allData.add("Observable ScrollView" +
                "@@https://github.com/ksoichiro/Android-ObservableScrollView" +
                "@@Android library to observe scroll events on scrollable views.\n" +
                "It's easy to interact with the Toolbar introduced in Android 5.0 Lollipop and may be helpful to implement look and feel of Material Design apps.");
        allData.add("Flip CheckBox" +
                "@@https://github.com/franlopjur/FlipCheckBox" +
                "@@Give your collections (on any View at all) a fancier look by adding a cool way to check elements. If your list can multi-select elements, you can add this component as a \"status indicator\". This is basically a customizable view flipper, with a \"front\" and \"rear\" faces, and an \"accept\" image in the \"rear\" that will animate changes in its state, mostly like the GMail app.");
        allData.add("ActiveAndroid" +
                "@@https://github.com/pardom/ActiveAndroid" +
                "@@ActiveAndroid is an active record style ORM (object relational mapper). What does that mean exactly? Well, ActiveAndroid allows you to save and retrieve SQLite database records without ever writing a single SQL statement. Each database record is wrapped neatly into a class with methods like save() and delete().\n" +
                "ActiveAndroid does so much more than this though. Accessing the database is a hassle, to say the least, in Android. ActiveAndroid takes care of all the setup and messy stuff, and all with just a few simple steps of configuration.");
        allData.add("FastScroll with RecyclerView" +
                "@@https://blog.stylingandroid.com/recyclerview-fastscroll-part-1/" +
                "@@RecyclerView does not have fast scroll built in so, in this short series, we’ll look at how to implement fast scroll in RecyclerView.");
        allData.add("Fab Progress" +
                "@@https://github.com/ckurtm/FabButton" +
                "@@Android Circular floating action button with integrated progress indicator ring As per material design docs.");
        allData.add("DialogPlus" +
                "@@https://github.com/orhanobut/dialogplus" +
                "@@DialogPlus provides android L dialog animation.");
        allData.add("Reverse engineering using Androguard" +
                "@@http://www.technotalkative.com/part-1-reverse-engineering-using-androguard/" +
                "@@In this part, you will cover how reverse engineering can be done by using Androguard, how to download and setup Androguard and how to decompile an apk using Androguard. You will look into the deep analysis in future parts of this series.");
        allData.add("Android Action Bar Style Generator" +
                "@@http://jgilfelt.github.io/android-actionbarstylegenerator/" +
                "@@The Android Action Bar Style Generator allows you to easily create a simple, attractive and seamless custom action bar style for your Android application. It will generate all necessary nine patch assets plus associated XML drawables and styles which you can copy straight into your project.");
        allData.add("Wireframing tool" +
                "@@https://www.fluidui.com/" +
                "@@Design using comprehensive, built-in libraries for Wireframe, Material Design, iOS and Web - or upload your existing designs.");
        allData.add("Hide show toolbar while scroll" +
                "@@https://mzgreen.github.io/2015/06/23/How-to-hideshow-Toolbar-when-list-is-scrolling%28part3%29/" +
                "@@How to hide/show Toolbar when list is scrolling.");
        allData.add("Awesome Validation" +
                "@@https://github.com/thyrlian/AwesomeValidation" +
                "@@Implement validation for Android within only 3 steps. Developers should focus on their awesome code, and let the library do the boilerplate. And what's more, this could help keep your layout file clean.");
        allData.add("JSON parsing library" +
                "@@https://github.com/bluelinelabs/LoganSquare" +
                "@@The fastest JSON parsing and serializing library available for Android. Based on Jackson's streaming API, LoganSquare is able to consistently outperform GSON and Jackson's Databind library by 400% or more. By relying on compile-time annotation processing to generate code, you know that your JSON will parse and serialize faster than any other method available.");
        allData.add("SharedPreferences replacement" +
                "@@https://github.com/orhanobut/hawk" +
                "@@Secure, simple key-value storage for Android.");
        allData.add("Paginize" +
                "@@https://github.com/neevek/Paginize" +
                "@@Paginize is a light-weight application framework for Android. It was designed to accelerate development cycles and make maintenance easier, it provides an intuitive programming model for writing Android applications. Paginize models a screen as a Page or part of the screen as an InnerPage, which in essence are just view wrappers. Paginize breaks down complex user interfaces into smaller units, provides APIs for easily handling page navigations, and offers flexibility for page inheritance and layout inheritance, which pushes code reuse in Android to another level.");
        allData.add("Material Calendar View" +
                "@@https://github.com/prolificinteractive/material-calendarview" +
                "@@A Material design back port of Android's CalendarView. The goal is to have a Material look and feel, rather than 100% parity with the platform's implementation.");
        allData.add("Beacon Keeper" +
                "@@https://github.com/m039/beacon-keeper" +
                "@@Beacon Keeper is a small, simple and solid library for working with iBeacons for Android. Before using it you may want to try it with a standalone application.");
        allData.add("Bluetooth Low Energy (BLE)" +
                "@@http://www.truiton.com/2015/04/android-bluetooth-low-energy-ble-example/" +
                "@@Bluetooth technology has been one of the most used technology when a connection has to be established with a remote device. But this technology also has a major limitation, i.e. high battery consumption. Therefore an improved version of this technology with low energy consumption was introduced, called BLE (Bluetooth Low Energy).");
        allData.add("Material Contextual Action Bar" +
                "@@https://github.com/afollestad/material-cab" +
                "@@Material CAB allows you to implement a customizable and flexible contextual action bar in your app. The traditional stock CAB on Android is limited to being placed at the top of your Activity, and the navigation drawer cannot go over it. This library lets you choose its exact location, and a toolbar is used allowing views to be be placed over and under it.");
        allData.add("Material ViewPager" +
                "@@https://github.com/florent37/MaterialViewPager" +
                "@@A Material Design ViewPager easy to use library");
        allData.add("Battery status use BroadcastReceiver" +
                "@@http://alexzh.com/tutorials/android-battery-status-use-broadcastreceiver/" +
                "@@In this post we will talk about how get battery status on Android device. For this task we will use BroadcastReceiver.");
        allData.add("Vector Compat" +
                "@@https://github.com/wnafee/vector-compat" +
                "@@A support library for VectorDrawable and AnimatedVectorDrawable introduced in Lollipop with fully backwards compatible tint support (api 14+ so far).");
        allData.add("Decor" +
                "@@https://github.com/chemouna/Decor" +
                "@@Decor is a library that applies decorators to Android layout with additional attributes without the need to extend and create a custom View for each functionality.");
        allData.add("RecyclerView Animators" +
                "@@https://github.com/wasabeef/recyclerview-animators" +
                "@@RecyclerView Animators is an Android library that allows developers to easily create RecyclerView with animations.");
        allData.add("Rich Editor" +
                "@@https://github.com/wasabeef/richeditor-android" +
                "@@A beautiful Rich Text WYSIWYG Editor for Android.");
        allData.add("Smart TabLayout" +
                "@@https://github.com/ogaclejapan/SmartTabLayout" +
                "@@A custom ViewPager title strip which gives continuous feedback to the user when scrolling.");
        allData.add("Waiting Dots" +
                "@@https://github.com/tajchert/WaitingDots" +
                "@@Small library that provides bouncing dots. This feature is used in number of messaging apps (such as Hangouts or Messenger), and lately in Android TV (for example when connecting to Wifi).");
        allData.add("Drag and Swipe RecyclerView" +
                "@@https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf" +
                "@@There are many tutorials, libraries, and examples for implementing “drag & drop” and “swipe-to-dismiss” in Android, using RecyclerView. Most are still using the old View.OnDragListener, and Roman Nurik’s SwipeToDismiss approach, even though there are newer, and better, methods available. A few use the newer APIs, but often rely on GestureDetectors and onInterceptTouchEvent, or the implementation is complex. There’s actually a really simple way to add these features to RecyclerView. It only requires one class, and it’s already part of the Android Support Library.");
        allData.add("QuickSand" +
                "@@https://github.com/blundell/QuickSand" +
                "@@When showing a really enchanting explanatory animation to your users, but you know that after a while it'll get tedious and would stop users wanting to use your app. QuickSand is here to solve that problem.");
        allData.add("TextView LinkBuilder" +
                "@@https://github.com/klinker24/Android-TextView-LinkBuilder" +
                "@@Insanely easy way to create clickable links within a TextView.");
        allData.add("Guillotine Menu Animation" +
                "@@https://github.com/Yalantis/GuillotineMenu-Android" +
                "@@Neat library, that provides a simple way to implement guillotine-styled animation.");
        allData.add("BottomSheet" +
                "@@https://github.com/Flipboard/bottomsheet" +
                "@@BottomSheet is an Android component which presents a dismissible view from the bottom of the screen. BottomSheet can be a useful replacement for dialogs and menus but can hold any view so the use cases are endless. This repository includes the BottomSheet component itself but also includes a set of common view components presented in a bottom sheet. These are located in the commons module.");
        allData.add("Frame Animations" +
                "@@https://www.bignerdranch.com/blog/frame-animations-in-android/" +
                "@@Animations add vivacity and personality to your apps. Let’s take a look at how to implement a subcategory of animations called “Frame Animations,” meaning that they’re drawn frame by frame.");
        allData.add("Sticky headers recyclerview" +
                "@@https://github.com/timehop/sticky-headers-recyclerview" +
                "@@This decorator allows you to easily create section headers for RecyclerViews using a LinearLayoutManager in either vertical or horizontal orientation.");
        allData.add("Coordinator Behavior" +
                "@@https://github.com/saulmm/CoordinatorBehaviorExample" +
                "@@Coordinator Behavior with toolbar & nested scrollView.");
        allData.add("Saripaar" +
                "@@https://github.com/ragunathjawahar/android-saripaar" +
                "@@Android Saripaar is a simple, feature-rich and powerful rule-based UI form validation library for Android. It is the SIMPLEST UI validation library available for Android.");
        allData.add("Expandable RecyclerView" +
                "@@https://www.bignerdranch.com/blog/expand-a-recyclerview-in-four-steps/" +
                "@@The Expandable RecyclerView library is a lightweight library that simplifies the inclusion of expandable dropdown rows in your RecyclerView. In it, you have two types of views. The parent view is the one visible by default, and it contains the children. The child view is the one that is expanded or collapsed, and will appear when a parent item is clicked.");
        allData.add("Charts library" +
                "@@https://github.com/diogobernardino/WilliamChart" +
                "@@ Android Library to help the implementation of charts in android applications. For the ones that would like to contribute, my idea is not only to implement the conventional chart features but instead something that could be pleasant and intuitive while representing and visualizing data. I would prefer to keep charts simple and clean rather than over featured.");
        allData.add("Blurry" +
                "@@https://github.com/wasabeef/Blurry" +
                "@@Blurry is an easy blur library for Android.");
        allData.add("Drag and Swipe with RecyclerView" +
                "@@https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-6a6f0c422efd" +
                "@@This article will expand on that example, adding support for grid layouts, “handle” initiated drags, indicating the selected view, and custom swipe animations.");
        allData.add("List of Android UI/UX Libraries" +
                "@@https://github.com/wasabeef/awesome-android-ui" +
                "@@A curated list of awesome Android UI/UX libraries.");
        allData.add("Motion on Android" +
                "@@https://labs.ribot.co.uk/exploring-meaningful-motion-on-android-1cd95a4bc61d" +
                "@@Exploring Meaningful Motion on Android.");
        allData.add("Runtime permission library (Dexter)" +
                "@@https://github.com/karumi/Dexter" +
                "@@Dexter is an Android library that simplifies the process of requesting permissions at runtime.");
        allData.add("TextSurface" +
                "@@https://github.com/elevenetc/TextSurface" +
                "@@A little animation framework which could help you to show message in a nice looking way.");
        allData.add("Screenshot Tests" +
                "@@http://facebook.github.io/screenshot-tests-for-android/" +
                "@@screenshot-test-for-android is a library that can generate fast deterministic screenshots while running instrumentation tests in android.");
        allData.add("ExplosionField" +
                "@@https://github.com/tyrantgit/ExplosionField" +
                "@@Explosive dust effect for views");
        allData.add("Transitions between Activities" +
                "@@https://github.com/lgvalle/Material-Animations" +
                "@@Animate activity layout content when transitioning from one activity to another.\n" +
                "Animate shared elements (Hero views) in transitions between activities.\n" +
                "Animate view changes within same activity.");
        allData.add("Dilating Dots ProgressBar" +
                "@@https://github.com/JustZak/DilatingDotsProgressBar" +
                "@@A customizable indeterminate progress bar.");
        allData.add("Image Cropping Library" +
                "@@https://github.com/Yalantis/uCrop" +
                "@@Aim to provide an ultimate and flexible image cropping experience.");
        allData.add("Floating SearchView" +
                "@@https://github.com/renaudcerrato/FloatingSearchView" +
                "@@Yet another floating search view implementation, also known as persistent search: that implementation fully supports menu (including submenu), logo and animated icon. Dropdown suggestions are backed by a RecyclerView and you can provide your own RecyclerView.Adapter, ItemDecorator or ItemAnimator.");
        allData.add("Like Button" +
                "@@https://github.com/jd-alexander/LikeButton" +
                "@@Like Button is a library that allows you to create a button with animation effects similar to Twitter's heart when you like something.");
        allData.add("Sublime Picker" +
                "@@https://github.com/vikramkakkar/SublimePicker" +
                "@@A customizable view that provisions picking of a date, time & recurrence option, all from a single user-interface. You can also view 'SublimePicker' as a collection of material-styled (API 23) DatePicker, TimePicker & RecurrencePicker, backported to API 14.");
        allData.add("ConstraintLayout" +
                "@@https://riggaroo.co.za/constraintlayout-101-new-layout-builder-android-studio/" +
                "@@A new type of layout that you can use in your Android App, it is compatible down to API level 9 and is part of the support library. Its aim is to reduce layout hierarchies (and improve performance of layouts) and it also reduces the complexity of trying to work with RelativeLayouts.  It is compatible with other layouts, so they can live in harmony together without having to choose one or the other.");
        allData.add("Ticker" +
                "@@https://github.com/robinhood/ticker" +
                "@@An Android text view with scrolling text change animation.");
        allData.add("DoorSignView" +
                "@@https://github.com/renaudcerrato/DoorSignView" +
                "@@Create static door signs using DoorSignView or, if you're into fancy things, give a try to AnimatedDoorSignView for adding a cool animation based on the device orientation sensor(s).");
        allData.add("Floating Navigation View" +
                "@@https://github.com/andremion/Floating-Navigation-View" +
                "@@A simple Floating Action Button that shows an anchored Navigation View.");
        allData.add("Panorama ImageView" +
                "@@https://github.com/gjiazhe/PanoramaImageView" +
                "@@An imageView can auto scroll with device rotating.");
        allData.add("ExpandIcon" +
                "@@https://github.com/zagum/Android-ExpandIcon" +
                "@@Nice and simple customizable implementation of Google style up/down arrow.");
        allData.add("Squint" +
                "@@https://github.com/IntruderShanky/Squint" +
                "@@Provide Diagonal cut on view with awesome customization");
        allData.add("Stencil" +
                "@@https://github.com/thoughtbot/stencil" +
                "@@Android library, written exclusively in Kotlin, for animating the path created from text");
        allData.add("Create library & publish as open source" +
                "@@https://medium.com/dualcores-studio/make-an-android-custom-view-publish-and-open-source-99a3d86df228" +
                "@@It will cover several fundamental concepts of android custom view, write a helpful README on GitHub, and how to publish your library.");
        allData.add("WaveLoading" +
                "@@https://github.com/race604/WaveLoading" +
                "@@This library provides a wave loading animation as a Drawable.");
        allData.add("PageLoader" +
                "@@https://github.com/arieridwan8/pageloader" +
                "@@PageLoader is a simple android library for loading page with easy customization.");
        allData.add("Crescento" +
                "@@https://github.com/developer-shivam/crescento/" +
                "@@Android library that adds a curve at the below of image views and relative layouts. CrescentoImageView and CrescentoContainer are the image view and relative layout respectively.");
        allData.add("RMSwitch" +
                "@@https://github.com/RiccardoMoro/RMSwitch" +
                "@@A simple View that works like a switch, but with more customizations. \n" +
                "With the option to choose between two or three states.");
        allData.add("Search Filter Animation in Kotlin" +
                "@@https://yalantis.com/blog/develop-filter-animation-kotlin-android/" +
                "@@Implementing Search Filter Animation in Kotlin for Quora Meets LinkedIn, Our App Design Concept.");
        allData.add("ChipsLayoutManager" +
                "@@https://github.com/BelooS/ChipsLayoutManager" +
                "@@Custom Recycler View's LayoutManager which moves item to the next line when no space left on the current.");
        allData.add("Spring animations" +
                "@@https://www.thedroidsonroids.com/blog/android/springanimation-examples" +
                "@@Have you ever wanted to do a bouncy animation like one of these on Android? If you have, you’re in for a treat!");
        allData.add("Cicerone" +
                "@@https://github.com/terrakok/Cicerone" +
                "@@Cicerone (a guide, one who conducts sightseers) is a lightweight library that makes the navigation in an Android app easy.\n" +
                "It was designed to be used with the MVP pattern (try Moxy), but will work great with any architecture.");
        allData.add("Autoplay Videos" +
                "@@https://github.com/Krupen/AutoplayVideos" +
                "@@This library is created with the purpose to implement recyclerview with videos easily.");
        allData.add("Insta Cropper" +
                "@@https://github.com/yasharpm/InstaCropper" +
                "@@A View for cropping images that is similar to Instagram's crop. Also an Activity for cropping is included.");
        allData.add("Offline support: “Try again, later”, no more." +
                "@@https://medium.com/@yonatanvlevin/offline-support-try-again-later-no-more-afc33eba79dc" +
                "@@A nice way to handle slow network or offline mode, this approach will not make bore to a user.");
        allData.add("Shuttle Music Player" +
                "@@https://github.com/timusus/Shuttle" +
                "@@Shuttle is an open source, local music player for Android..");
        allData.add("Alerter" +
                "@@https://github.com/Tapadoo/Alerter" +
                "@@This library aims to overcome the limitations of Toasts and Snackbars, while reducing the complexity of your layouts.");
        allData.add("MKloader" +
                "@@https://github.com/nntuyen/mkloader" +
                "@@Beautiful and smooth custom loading views.");
        allData.add("Chuck" +
                "@@https://github.com/jgilfelt/chuck" +
                "@@Chuck is a simple in-app HTTP inspector for Android OkHttp clients. Chuck intercepts and persists all HTTP requests and responses inside your application, and provides a UI for inspecting their content.");
        allData.add("PreviewSeekBar" +
                "@@https://github.com/rubensousa/PreviewSeekBar" +
                "@@A SeekBar suited for showing a preview of something. As seen in Google Play Movies.");
        allData.add("Picasso face detection transformation" +
                "@@https://github.com/aryarohit07/PicassoFaceDetectionTransformation" +
                "@@An Android image transformation library providing cropping above Face Detection (Face Centering) for Picasso.");
        allData.add("Jelly Toolbar" +
                "@@https://github.com/Yalantis/JellyToolbar" +
                "@@Make your toolbar more interesting, it will provide you a jelly view to your toolbar.");
        allData.add("SlidingRootNav" +
                "@@https://github.com/yarolegovich/SlidingRootNav" +
                "@@The library is a DrawerLayout-like ViewGroup, where a \"drawer\" is hidden under the content view, which can be shifted to make the drawer visible. It doesn't provide you with a drawer builder.");
        allData.add("Sector ProgressView" +
                "@@https://github.com/timqi/SectorProgressView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5904" +
                "@@A simple progress prompt or chart widget of android using circle and a sector.");
        allData.add("Email AutoCompleteTextView" +
                "@@https://github.com/greenhalolabs/EmailAutoCompleteTextView" +
                "@@EmailAutoCompleteTextView inherits from AutoCompleteTextView and uses the unique emails associated with the device to provide auto-complete functionality for email input fields. It also provides an \"x\" button on the right hand side as an easy way to clear text.");
        allData.add("Location tracker" +
                "@@https://github.com/quentin7b/android-location-tracker" +
                "@@Android Simple Location Tracker is an Android library that helps you get user location.");
        allData.add("ArcLayout" +
                "@@https://github.com/florent37/ArcLayout" +
                "@@With Arc Layout explore new styles and approaches on material design.");
        allData.add("Carousel with Circular Image and Card" +
                "@@https://github.com/anik123/Android-Carousel-with-Circular-Image-and-Card" +
                "@@Carousel effect to your view using cardView and imageView.");
        allData.add("More Libraries and Resources" +
                "@@http://alamkanak.github.io/android-libraries-and-resources/" +
                "@@List of awesome libraries, tools and other resources.");
        allData.add("Discrete ScrollView" +
                "@@https://github.com/yarolegovich/DiscreteScrollView" +
                "@@The library is a RecyclerView-based implementation of a scrollable list, where current item is centered and can be changed using swipes. It is similar to a ViewPager, but you can quickly and painlessly create layout, where views adjacent to the currently selected view are partially or fully visible on the screen.");
        allData.add("Codelabs" +
                "@@https://codelabs.developers.google.com/" +
                "@@Google Developers Codelabs provide a guided, tutorial, hands-on coding experience. Most codelabs will step you through the process of building a small application, or adding a new feature to an existing application. They cover a wide range of topics such as Android Wear, Google Compute Engine, Project Tango, and Google APIs on iOS.");
        allData.add("Google Samples" +
                "@@https://github.com/googlesamples" +
                "@@Sample codes provided by the Google itself.");
        allData.add("Material Shadows" +
                "@@https://github.com/harjot-oberai/MaterialShadows" +
                "@@A library for supporting convex material shadows.");
        allData.add("File manager" +
                "@@https://github.com/FirzenYogesh/FileListerDialog/" +
                "@@A simple file/ directory picker dialog for android.");
        allData.add("Bottom TabLayout" +
                "@@https://github.com/stfalcon-studio/BottomTabLayout" +
                "@@Simple library for bottom tab layout.");
        allData.add("Audio Recorder button" +
                "@@https://github.com/dewarder/HoldingButton?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5436" +
                "@@Button which is visible while user holds it. Main use case is controlling audio recording state.");
        allData.add("Login Buttons" +
                "@@https://github.com/shaishavgandhi05/LoginButtons?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=4194" +
                "@@A simple library for building beautiful login buttons. UI library for social login buttons.");
        allData.add("Magic Button" +
                "@@https://github.com/bloderxd/MagicButton?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3988" +
                "@@It's a new button in android that hypnotizes the user and make him give you a lot of money!!! \n" +
                "Ok, just kidding, this is not so magic yet... but it's a cute button.");
        allData.add("Easy Video Player" +
                "@@https://github.com/afollestad/easy-video-player" +
                "@@Easy Video Player is a simple but powerful view that you can plugin to your apps to quickly get video playback working.");
        return allData;
    }


}
