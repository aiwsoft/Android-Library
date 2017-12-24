package com.aiwsoft.androidlibraries;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aiwsoft.androidlibraries.application.MyApp;
import com.aiwsoft.androidlibraries.custome.CustomActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import de.cketti.mailto.EmailIntentBuilder;

public class SettingsActivity extends CustomActivity {
    // Remove the below line after defining your own ad unit ID.
    private AdView mAdView;
    private TextView toolbar_title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Settings");
        actionBar.setTitle("");


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        setTouchNClick(R.id.txt_share);
        setTouchNClick(R.id.txt_about_app);
        setTouchNClick(R.id.txt_about_us);
        setTouchNClick(R.id.txt_feedback);

        TextView txt_mail_us = findViewById(R.id.txt_mail_us);
        doClickSpanForString("Mail us for any query\nat\n ", "myaiwsoft@gmail.com", txt_mail_us);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_share) {
            shareApp();
        } else if (v.getId() == R.id.txt_feedback) {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (v.getId() == R.id.txt_about_us) {
            MyApp.popMessage("About Us", "AIWSOFT is a startup software company, and our aim to help out most of the developers with their help, it's just " +
                    "initial step to enter in IT field. As you can see currently it's only showing the libraries that we can use to " +
                    "make your application more easy to develop. There is a lots of ready-made code available to save development time" +
                    " and to give better look to your app.\nJust keep in touch with this app, we are going to include android tutorials &" +
                    " magical coding things to grow your coding skills." +
                    "\nThank you", SettingsActivity.this);
        } else if (v.getId() == R.id.txt_about_app) {
            MyApp.popMessage("About App", "Android Library is the application to explore available resources to make development easier.\n" +
                    "You will find the best available libraries that you can use easily in to you application. We will include android tutorials & useful coding skills into app soon.", SettingsActivity.this);
        }
    }

    private void doColorSpanForFirstString(String firstString,
                                           String lastString, TextView txtSpan) {

        String changeString = (firstString != null ? firstString : "");

        String totalString = changeString + lastString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(getResources()
                .getColor(R.color.Color_Blue)), 0, changeString.length(), 0);

        txtSpan.setText(spanText);
    }


    private void doColorSpanForSecondString(String firstString,
                                            String lastString, TextView txtSpan) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(getResources()
                .getColor(R.color.Color_Blue)), String.valueOf(firstString)
                .length(), totalString.length(), 0);
        txtSpan.setText(spanText);
    }

    private void doStyleSpanForFirstString(String firstString,
                                           String lastString, TextView txtSpan) {
        String changeString = (firstString != null ? firstString : "");
        String totalString = changeString + lastString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD), 0,
                changeString.length(), 0);
        txtSpan.setText(spanText);
    }

    private void doStyleSpanForSecondString(String firstString,
                                            String lastString, TextView txtSpan) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD),
                String.valueOf(firstString).length(),
                totalString.length(), 0);
        txtSpan.setText(spanText);
    }

    private void doClickSpanForString(String firstString,
                                      String lastString, TextView txtSpan) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new MyClickableSpan(totalString),
                String.valueOf(firstString).length(),
                totalString.length(), 0);
        txtSpan.setText(spanText);
        txtSpan.setMovementMethod(LinkMovementMethod.getInstance());
    }

    class MyClickableSpan extends ClickableSpan {
        public MyClickableSpan(String string) {
            super();
        }

        public void onClick(View tv) {
            EmailIntentBuilder.from(SettingsActivity.this)
                    .to("myaiwsoft@gmail.com")
//                        .cc("bob@example.org")
//                        .bcc("charles@example.org")
                    .subject("Support mail")
                    .body("")
                    .start();
        }

        public void updateDrawState(TextPaint ds) {

            ds.setColor(getResources().getColor(R.color.blue));
            ds.setUnderlineText(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
