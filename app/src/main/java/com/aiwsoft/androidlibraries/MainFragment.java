package com.aiwsoft.androidlibraries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aiwsoft.androidlibraries.adapter.AllAdapter;
import com.aiwsoft.androidlibraries.utils.DipPixelHelper;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView rv_libs;
    private AllAdapter adapter;
    private List<String> dataList;
    private int clickDetailsCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        rv_libs = view.findViewById(R.id.rv_libs);
        dataList = getAllData();
        Collections.sort(dataList, new Comparator<String>() {
            @Override
            public int compare(String text1, String text2) {
                return text1.compareToIgnoreCase(text2);
            }
        });
        adapter = new AllAdapter(dataList, MainFragment.this);
        rv_libs.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_libs.setAdapter(adapter);
        setHasOptionsMenu(true);
        return view;
    }

    private SearchView searchView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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
            ((DrawerActivity) getActivity()).showInterstitial();
            return;
        }
        ++clickDetailsCounter;
        new FinestWebView.Builder(getActivity())
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
                .progressBarHeight(DipPixelHelper.getPixel(getActivity(), 3))
                .progressBarColorRes(R.color.colorAccent)
                .backPressToClose(false)
                .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                .show(data.split("@@")[1]);
    }


    private List<String> getAllData() {
        List<String> allData = new ArrayList<>();
//        allData.add("" +
//                "@@" +
//                "@@");
//        allData.add("" +
//                "@@" +
//                "@@"); add more from https://android-arsenal.com/
        allData.add("White House for Android mobile application" +
                "@@https://github.com/whitehouse/wh-app-android" +
                "@@A native Android (Java) app designed to fetch, cache, and display multiple feeds containing articles, photos, and live and on demand video. These are displayed in a web view. Includes support for push notifications. Related to RSS feed.");
        allData.add("Update Checker" +
                "@@https://github.com/rampo/UpdateChecker" +
                "@@UpdateChecker is a class that can be used by Android Developers to increase the number of their apps' updates by showing a \"New update available\" Notification or Dialog.");
        allData.add("Two Way GridView" +
                "@@https://github.com/jess-anders/two-way-gridview" +
                "@@An Android GridView that can be configured to scroll horizontally or vertically.");
        allData.add("Token AutoComplete" +
                "@@https://github.com/splitwise/TokenAutoComplete" +
                "@@TokenAutoComplete is an Android Gmail style token auto-complete text field and filter. It's designed to have an extremely simple API to make it easy for anyone to implement this functionality while still exposing enough customization to let you make it awesome.");
        allData.add("Smart PNG and JPEG compression" +
                "@@https://tinypng.com/" +
                "@@Optimize your images with a perfect balance in quality and file size.");
        allData.add("TimesSquare for Android" +
                "@@https://github.com/square/android-times-square" +
                "@@Standalone Android widget for picking a single date from a calendar view.");
        allData.add("Telegram messenger for Android" +
                "@@https://github.com/DrKLO/Telegram" +
                "@@Telegram is a messaging app with a focus on speed and security. It’s superfast, simple and free. This repo contains the official source code for Telegram App for Android.");
        allData.add("SuperToasts Library" +
                "@@https://github.com/JohnPersano/SuperToasts" +
                "@@The SuperToasts library enhances and builds upon the Android Toast class. This library includes support for context sensitive SuperActivityToasts that can show progress and handle button clicks as well as non-context sensitive SuperToasts which offer many customization options over the standard Android Toast class.");
        allData.add("Sticky List Headers" +
                "@@https://github.com/emilsjolander/StickyListHeaders" +
                "@@StickyListHeaders is an Android library that makes it easy to integrate section headers in your ListView. These section headers stick to the top like in the new People app of Android 4.0 Ice Cream Sandwich. This behavior is also found in lists with sections on iOS devices. This library can also be used without the sticky functionality if you just want section headers.");
        allData.add("Sprinkles " +
                "@@https://github.com/emilsjolander/sprinkles" +
                "@@Sprinkles is a boiler-plate-reduction-library for dealing with databases in android applications. Some would call it a kind of ORM but I don't see it that way. Sprinkles lets SQL do what it is good at, making complex queries. SQL however is a mess (in my opinion) when is comes to everything else. This is why sprinkles helps you with things such as inserting, updating, and destroying models. Sprinkles will also help you with the tedious task of unpacking a cursor into a model. Sprinkles actively supports version 2.3 of Android and above but it should work on older versions as well.");
        allData.add("Spoon Tool" +
                "@@http://square.github.io/spoon/" +
                "@@Android's ever-expanding ecosystem of devices creates a unique challenge to testing applications. Spoon aims to simplify this task by distributing instrumentation test execution and displaying the results in a meaningful way.\n" +
                "Instead of attempting to be a new form of testing, Spoon makes existing instrumentation tests more useful. Using the application APK and instrumentation APK, Spoon runs the tests on multiple devices simultaneously. Once all tests have completed, a static HTML summary is generated with detailed information about each device and test.");
        allData.add("Spring Framework" +
                "@@http://projects.spring.io/spring-framework/" +
                "@@Core support for dependency injection, transaction management, web applications, data access, messaging, testing and more.");
        allData.add("Slide Expandable ListView" +
                "@@https://github.com/tjerkw/Android-SlideExpandableListView" +
                "@@Not happy with the Android ExpandableListView android offers? Want something like the Spotify app. This library allows you to have custom listview in wich each list item has an area that will slide-out once the users clicks on a certain button.");
        allData.add("Size Adjusting TextView" +
                "@@https://github.com/erchenger/SizeAdjustingTextView" +
                "@@This is based on an open source autosizing textview for Android I found a few weeks ago. The initial approach didn't resize multiple lines and wasn't maintained to keep up with changes in Android. I decided to go ahead and create this as a place to preserve the auto sizing text view as well as giving it a platform for some change and to possibly add some features and functionality.");
        allData.add("Selector Chapek" +
                "@@https://github.com/inmite/android-selector-chapek" +
                "@@This Android Studio plugin automatically generates drawable selectors from appropriately named Android resources.");
        allData.add("Shimmer for Android" +
                "@@https://github.com/RomainPiel/Shimmer-android" +
                "@@This library is DEPRECATED, as I don't have time to mainatin it anymore. But feel free to go through the code and copy that into your project, it still does its job.");
        allData.add("Android device shake detection." +
                "@@https://github.com/square/seismic" +
                "@@Android device shake detection.");
        allData.add("Scalpel" +
                "@@https://github.com/JakeWharton/scalpel" +
                "@@A surgical debugging tool to uncover the layers under your app.");
        allData.add("Progress Button" +
                "@@https://github.com/f2prateek/progressbutton" +
                "@@ProgressButton is a custom progress indicator with a tiny footprint. The default implementation provides a pin progress button as seen on the Android design site. ");
        allData.add("PhotoView" +
                "@@https://github.com/chrisbanes/PhotoView" +
                "@@PhotoView aims to help produce an easily usable implementation of a zooming Android ImageView.");
        allData.add("Parallax ViewPager" +
                "@@https://github.com/andraskindler/parallaxviewpager" +
                "@@Setup requires little extra effort, using the ParallaxViewPager is just like using a standard ViewPager, with the same adapter. Of course, there's no silver bullet - the developer has to supply a background tailored to the current needs (eg. the number of items in the adapter and the size of the ViewPager).");
        allData.add("Paging GridView" +
                "@@https://github.com/nicolasjafelle/PagingGridView" +
                "@@PagingGridView has the ability to add more items on it like PagingListView does. Basically is a GridView with the ability to add more items on it when reaches the end of the list.");
        allData.add("Muzei Live Wallpaper" +
                "@@https://github.com/romannurik/muzei" +
                "@@Muzei is a live wallpaper that gently refreshes your home screen each day with famous works of art. It also recedes into the background, blurring and dimming artwork to keep your icons and widgets in the spotlight. Simply double touch the wallpaper or open the Muzei app to enjoy and explore the artwork in its full glory.");
        allData.add("MultiChoice Adapter" +
                "@@https://github.com/ManuelPeinado/MultiChoiceAdapter" +
                "@@MultiChoiceAdapter is an implementation of ListAdapter which adds support for modal multiple choice selection as in the native Gmail app.");
        allData.add("Media Chooser" +
                "@@https://github.com/learnNcode/MediaChooser" +
                "@@Library to browse & select videos and images from disk.");
        allData.add("Material design palette" +
                "@@https://www.materialpalette.com/" +
                "@@Easy way to create app theme colors.");
        allData.add("Map Navigator" +
                "@@https://github.com/tyczj/MapNavigator" +
                "@@Easy to use library to get and display driving directions on Google Maps v2 in Android. This library gives you directions and displays the route on the map.");
        allData.add("Magnet" +
                "@@https://github.com/premnirmal/Magnet" +
                "@@This library enables you to create a window icon similar to Facebooks chat icon, and also similar to the Link Bubble app. See the demo project for sample implementations.");
        allData.add("ListView Animations" +
                "@@https://github.com/nhaarman/ListViewAnimations" +
                "@@ListViewAnimations is an Open Source Android library that allows developers to easily create ListViews with animations. Feel free to use it all you want in your Android apps provided that you cite this project and include the license in your app.");
        allData.add("JSON To Java" +
                "@@http://jsontojava.appspot.com/" +
                "@@JSON To Java as a Service (JTJaaS) is intended to lighten the burden of java developers who need to create POJOs to parallel a JSON API.");
        allData.add("Zoom ImageView by touch" +
                "@@https://github.com/sephiroth74/ImageViewZoom" +
                "@@ImageViewTouch is an android ImageView widget with zoom and pan capabilities. This is an implementation of the ImageView widget used in the Gallery app of the Android open source project.");
        allData.add("Image Layout" +
                "@@https://github.com/ManuelPeinado/ImageLayout" +
                "@@A layout that arranges its children in relation to a background image. The layout of each child is specified in image coordinates (pixels), and the conversion to screen coordinates is performed automatically.\n" +
                "The background image is adjusted so that it fills the available space.\n" +
                "For some applications this might be a useful replacement for the now deprecated AbsoluteLayout.");
        allData.add("Iconic TextView" +
                "@@https://github.com/akramfares/IconicTextView" +
                "@@IconicTextView is an extension of Android TextView class which provides support for some iconic fonts.");
        allData.add("Google ProgressBar" +
                "@@https://github.com/jpardogo/GoogleProgressBar" +
                "@@Android library to display different kind of google related animations for the progressBar.");
        allData.add("Glass ActionBar" +
                "@@https://github.com/ManuelPeinado/GlassActionBar" +
                "@@GlassActionBar is an Android library which implements a glass-like effect for the action bar.\n" +
                "\n" +
                "The three most commonly used action bar implementations are supported: stock (API >13), ActionBarCompat and ActionBarSherlock.");
        allData.add("DiscrollView = Scroll + discover" +
                "@@https://github.com/flavienlaurent/discrollview" +
                "@@With DiscrollView, I wanted to import this pattern on Android. This is an 0.0.1 alpha version because you have to do all the transformation work (fade, translation, scale etc) yourself base on a ratio value. I'm going to add some transformation presets (translation from left to right + fade in for example) to make the library more ready to use for lazy developers.");
        allData.add("Fading ActionBar" +
                "@@https://github.com/ManuelPeinado/FadingActionBar" +
                "@@FadingActionBar is a library which implements the cool fading action bar effect that can be seen in the new Play Music app.");
        allData.add("Edge Effect Override" +
                "@@https://github.com/AndroidAlliance/EdgeEffectOverride" +
                "@@EdgeEffectOverride is library designed to help override the blue overscroll_edge and overscroll_glow effects used by the the EdgeEffect class.");
        allData.add("Circular Floating ActionMenu" +
                "@@https://github.com/oguzbilgener/CircularFloatingActionMenu" +
                "@@An animated, customizable circular floating menu for Android, inspired by Path app.");
        allData.add("Barcode Scanner" +
                "@@https://github.com/dm77/barcodescanner" +
                "@@Android library projects that provides easy to use and extensible Barcode Scanner views based on ZXing and ZBar.");
        allData.add("Staggered GridView" +
                "@@https://github.com/etsy/AndroidStaggeredGrid" +
                "@@An Android staggered grid view which supports multiple columns with rows of varying sizes.");
        allData.add("Code Diet" +
                "@@http://androidannotations.org/" +
                "@@AndroidAnnotations is an Open Source framework that speeds up Android development. It takes care of the plumbing, and lets you concentrate on what's really important. By simplifying your code, it facilitates its maintenance.");
        allData.add("Process Button" +
                "@@https://github.com/dmytrodanylyk/android-process-button" +
                "@@Android Buttons With Built-in Progress Meters. ");
        allData.add("Android Sliding Up Panel" +
                "@@https://github.com/umano/AndroidSlidingUpPanel" +
                "@@This library provides a simple way to add a draggable sliding up panel (popularized by Google Music and Google Maps) to your Android application.");
        allData.add("Memory Size Class for viewing available storage" +
                "@@http://www.androidsnippets.com/memory-size-class-for-viewing-available-storage.html" +
                "@@Class used to view available and total storage for internal and external memory. Also does pretty formatting.");
        allData.add("Advanced Android TextView" +
                "@@http://chiuki.github.io/advanced-android-textview/#/" +
                "@@You will get a range of textView resources that you can use for you design patterns, with animations, shadow," +
                " vector and so on.");
         allData.add("Android-ActionItemBadge" +
                "@@https://mikepenz.github.io/Android-ActionItemBadge/" +
                "@@ActionItemBadge is a library which offers a simple and easy to use method to add a badge to your action item!");
        allData.add("Adjacent Fragment Pager" +
                "@@https://github.com/JakeWharton/adjacent-fragment-pager-sample" +
                "@@Demonstrates how to manage two fragments where portrait displays them in a ViewPager and landscape displays them side-by-side.\n" +
                "\n" +
                "Due the shenanigans performed by FragmentPagerAdapter we're forced to write a custom PagerAdapter which handles the instances our selves.\n" +
                "\n" +
                "This sample is very-much hard coded and specific to two pages. If you wanted something a bit more robust and generalized it wouldn't be too much work to do so.");
        allData.add("Android Button Maker" +
                "@@http://angrytools.com/android/button/" +
                "@@Android Button Maker is online tool to generate buttons code for Android Apps. Android API provide Drawable Resources where XML file defines geometric shape, including colors, border and gradients.\n" +
                "These button is generating based on shape drawable XML code which load faster compare to normal PNG buttons. You can customize button properties in setting panel and get source code.");
        allData.add("Cheatsheet for Graphic Designers" +
                "@@http://petrnohejl.github.io/Android-Cheatsheet-For-Graphic-Designers/" +
                "@@Graphic designers aren't programmers and sometimes don't know how to properly prepare graphic assets for developers. This simple cheatsheet should help them to do their job better, and to simplify developers' lives.");
        allData.add("Android Empty Layout" +
                "@@https://github.com/alamkanak/Android-Empty-Layout" +
                "@@A library for showing different types of layouts when a list view is empty. These layouts can be shown when,\n" +
                "\n" +
                "the list is loading\n" +
                "the list has no item to display\n" +
                "an error occured trying to load items\n" +
                "Loading animation is also supported.");
        allData.add("Android Floating Label Widgets" +
                "@@http://marvinlabs.github.io/android-floatinglabel-widgets/" +
                "@@A set of input widgets with a hint label that floats when input is not empty.");
        allData.add("Android Image Slider" +
                "@@https://github.com/daimajia/AndroidImageSlider" +
                "@@This is an amazing image slider for the Android platform. I decided to open source this because there is really not an attractive, convenient slider widget in Android.\n" +
                "\n" +
                "You can easily load images from an internet URL, drawable, or file. And there are many kinds of amazing animations you can choose.");
        allData.add("SegmentedButton" +
                "@@https://github.com/ceryle/SegmentedButton" +
                "@@Segmented Button is a IOS-like \"Segmented Control\" with animation.\n" +
                "For more Android-like segmented control, check Radio Real Button.");
        allData.add("Basics of Angular" +
                "@@https://codelabs.developers.google.com/codelabs/angular-codelab/index.html?index=..%2F..%2Findex#0" +
                "@@Angular is a development platform for building mobile and desktop applications. Angular lets you extend HTML's syntax to express your application's components clearly and succinctly. Angular's binding and Dependency Injection eliminate much of the code you would otherwise have to write.");
        allData.add("Make first app in Kotlin" +
                "@@https://codelabs.developers.google.com/codelabs/build-your-first-android-app-kotlin/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to build and run your first Android app in Kotlin. If you're looking for the Java version of this codelab, you can go here. Kotlin is a statically typed programming language that runs on the JVM and is completely interoperable with Java. Kotlin is an officially supported language for developing Android apps, along with Java.");
        allData.add("Build Node.js & Angular 2 Web app using Google Could Platform" +
                "@@https://codelabs.developers.google.com/codelabs/cloud-cardboard-viewer/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to deploy, monitor, debug, and scale a Node.js & Angular 2 web application on Google Cloud Platform.");
        allData.add("Google Assistant with Firebase and Dialogflow" +
                "@@https://codelabs.developers.google.com/codelabs/assistant-codelab/index.html?index=..%2F..%2Findex#0" +
                "@@Welcome to the Firebase App on Assistant codelab. In this codelab, you'll learn how to use Firebase, Dialogflow, and Google Assistant to create an App on Assistant.");
        allData.add("Beautiful UIs with Flutter" +
                "@@https://codelabs.developers.google.com/codelabs/flutter/index.html?index=..%2F..%2Findex#0" +
                "@@Flutter is an open source SDK for creating high-performance, high-fidelity mobile apps for iOS and Android. The Flutter framework makes it easy for you to build user interfaces that react smoothly in your app, while reducing the amount of code required to synchronize and update your app's view.");
        allData.add("Beacons! Proximity & Context-aware Apps" +
                "@@https://codelabs.developers.google.com/codelabs/hello-beacons/index.html?index=..%2F..%2Findex#6" +
                "@@Bluetooth Low Energy (BLE) Beacons are one-way transmitters that mark important places and objects in a way that users' devices understand. The Google beacon platform provides a set of resources and APIs to make interacting with beacons power-efficient and useful. This allows developers to create context aware and proximity based applications using the high quality signal provided by BLE beacons.");
        allData.add("Keep sensitive Data safe and private" +
                "@@https://codelabs.developers.google.com/codelabs/android-storage-permissions/index.html?index=..%2F..%2Findex#0" +
                "@@Keeping user data and other sensitive information secure and private is a key factor in building trust when using your app. This codelab helps you identify potential risks and take appropriate safeguards toward keeping this information safe.");
        allData.add("Media streaming with ExoPlayer" +
                "@@https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#0" +
                "@@ExoPlayer is the video player running in the Android YouTube app.");
        allData.add("Notification Channels and Badges" +
                "@@https://codelabs.developers.google.com/codelabs/notification-channels-java/index.html?index=..%2F..%2Findex#0" +
                "@@Notification channels and badges are part of the many updates in Android O. Starting in Android O, you divide all of your notifications into different notification channels, depending on the type of notification your app is sending. Using channels, you can easily differentiate between different types of communication and provide different default sounds, importance, icons, and more. Once a notification channel exists on the device, users have full control over the settings per notification channel.");
        allData.add("Playing music on cars and wearables" +
                "@@https://codelabs.developers.google.com/codelabs/android-music-player/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to adapt a music player app to work seamlessly on Android Auto and Android Wear using the latest Android media APIs.");
        allData.add("Removing dependencies on background services" +
                "@@https://codelabs.developers.google.com/codelabs/android-migrate-to-jobs/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to convert long-running or periodic background services into jobs that can be dispatched by the framework when it's ready. You'll work through a simple case study that demonstrates how to take advantage of either the open source Firebase JobDispatcher library (available on devices with Google Play Services installed) or the Android framework's JobScheduler (available on Lollipop+ devices).");
        allData.add("Seemless Sign In with Smart Locak" +
                "@@https://codelabs.developers.google.com/codelabs/android-smart-lock/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to use Smart Lock to provide your users with a simple onboarding process. Presenting a user with a login screen as few times as possible, likely only once.");
        allData.add("OCR with mobile vision Text api" +
                "@@https://codelabs.developers.google.com/codelabs/mobile-vision-ocr/index.html?index=..%2F..%2Findex" +
                "@@Optical Character Recognition (OCR) gives a computer the ability to read text that appears in an image, letting applications make sense of signs, articles, flyers, pages of text, menus, or any other place that text appears as part of an image. The Mobile Vision Text API gives Android developers a powerful and reliable OCR capability that works with most Android devices and won't increase the size of your app.");
        allData.add("Speedy Mobile checkour with Android Pay" +
                "@@https://codelabs.developers.google.com/codelabs/android-pay/index.html?index=..%2F..%2Findex#0" +
                "@@Welcome to the Android Pay codelab! In this codelab you will learn how to set up your Android app to collect payment information from users with just a few clicks. No more flaky credit card forms, this is how your users should be shopping in your application.");
        allData.add("Translate text with Translation Api" +
                "@@https://codelabs.developers.google.com/codelabs/cloud-translation-intro/index.html?index=..%2F..%2Findex#0" +
                "@@The Cloud Translation allows you to translate an arbitrary string into any supported language. Language detection is also available in cases where the source language is unknown.");
        allData.add("Background location updates in Android 'O'" +
                "@@https://codelabs.developers.google.com/codelabs/background-location-updates-android-o/index.html?index=..%2F..%2Findex#0" +
                "@@This codelab covers changes in background location gathering on devices running Android \"O\".");
        allData.add("Auto Backup" +
                "@@https://codelabs.developers.google.com/codelabs/android-backup-codelab/index.html?index=..%2F..%2Findex#0" +
                "@@Users invest a lot of time customizing settings in their apps. Restoring settings data for users when they upgrade to a new device is an important aspect of ensuring a great user experience. It's therefore important to minimize the number of steps that a user has to go through to pick up where they left off on their previous device. You can use Auto Backup to restore settings data even if a user doesn't log in to your app.");
        allData.add("Add web app to User's home screen" +
                "@@https://codelabs.developers.google.com/codelabs/add-to-home-screen/index.html?index=..%2F..%2Findex#0" +
                "@@This codelab will walk you through adding items to a web app that Chrome requires before it will prompt users to add the app to their home screens. Specifically:\n" +
                "Web app manifest\n" +
                "Service worker");
        allData.add("Artistic style transform & other advanced image editing" +
                "@@https://codelabs.developers.google.com/codelabs/android-style-transfer/index.html?index=..%2F..%2Findex#0" +
                "@@RenderScript is the parallel computing framework which is widely used on image processing related Android Applications. On the other hand, Deep Neural Net (DNN) based image filters are gaining more and more attention, which traditionally runs on desktops or servers. With the help of CPU & GPU acceleration of RenderScript, these compute intensive applications are now feasible on mobile devices.");
        allData.add("Weather station with BMP280 sensor hardware" +
                "@@https://codelabs.developers.google.com/codelabs/androidthings-weatherstation/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you're going to build a weather station that reads environmental temperature and pressure data from a BMP280 sensor, and displays the latest reading locally on the Rainbow HAT.");
        allData.add("Network security configuration" +
                "@@https://codelabs.developers.google.com/codelabs/android-network-security-config/index.html?index=..%2F..%2Findex#0" +
                "@@Although it's commonplace for apps to exchange data over the Internet, often with servers other than those you trust, you need to exercise caution when sending and receiving information that could be sensitive and private.");
        allData.add("Android N, Quick settings" +
                "@@https://codelabs.developers.google.com/codelabs/android-n-quick-settings/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to register a custom tile in the Quick Settings of an Android device.");
        allData.add("Adding leanback to your Android TV app" +
                "@@https://codelabs.developers.google.com/codelabs/androidtv-adding-leanback/index.html?index=..%2F..%2Findex#0" +
                "@@In this codelab, you'll learn how to quickly enable a mobile app for Android TV using the Leanback library. At the end of the codelab you can expect to have a UX compliant single apk for mobile devices and Android TV.");
        allData.add("AdMob Native Advanced Ads" +
                "@@https://codelabs.developers.google.com/codelabs/admob-native-advanced-feed-android/index.html?index=..%2F..%2Findex#0" +
                "@@Native is a component-based ad format that gives publishers the freedom to customize how ad assets like headlines and calls to action are presented in their apps. By choosing fonts, colors, and other details for themselves, publishers are able to create natural, unobtrusive ad presentations that can add to a rich user experience.");
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
        allData.add("Duo Navigation Drawer" +
                "@@https://github.com/PSD-Company/duo-navigation-drawer" +
                "@@This Android library provides an easy way to create an alternative navigation drawer for android. Instead of a drawer that slides over the main content of the Activity, this lets the content slide away and reveal a menu below it." +
                "\nThis app also implemented same drawer.");
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
        allData.add("RoadRunner" +
                "@@https://github.com/glomadrian/RoadRunner" +
                "Road Runner is a library for android which allow you to make your own loading animation using a SVG image");
        allData.add("FragmentAnimations" +
                "@@https://github.com/kakajika/FragmentAnimations" +
                " 3D animations for support-v4 Fragment transition.");
        return allData;
    }
}