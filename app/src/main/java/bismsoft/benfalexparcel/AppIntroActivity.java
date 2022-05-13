package bismsoft.benfalexparcel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AppIntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter ;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim ;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);


//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (restorePrefData()) {


                Intent mainActivitys = new Intent(getApplicationContext(), Splash.class );
                startActivity(mainActivitys);
                finish();


        }
        else {

            setContentView(R.layout.activity_app_intro);
            // hide the action bar

          //  getSupportActionBar().hide();

            // ini views
            btnNext = findViewById(R.id.btn_next);
            btnGetStarted = findViewById(R.id.btn_get_started);
            tabIndicator = findViewById(R.id.tab_indicator);
            btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
            tvSkip = findViewById(R.id.tv_skip);

            // fill list screen

            final List<AppIntroModelClass> mList = new ArrayList<>();
            mList.add(new AppIntroModelClass("PACKAGE RECEIVING"
                    ,"For when you just can’t be available personally to receive " +
                    "your packages, we have the solution. We’ll provide an alternate address to " +
                    "safely receive your pre-ordered packages and then schedule a personal package" +
                    " delivery when it’s convenient for our customers.",R.drawable.logo));


            mList.add(new AppIntroModelClass("PACKAGE PICKUP"
                    ,"With the rise in online shopping and more and more consumers wanting " +
                    "their packages delivered to their homes, the increase in porch pirates has " +
                    "been on the rise as well and is not likely to go away anytime soon. It is " +
                    "estimated that approximately 23 million Americans have had a package stolen " +
                    "from their business, front porch, or mailboxes over the past few years. With " +
                    "our PACKAGE Delivery SERVICES, we provide the solution by preventing the package and data theft" +
                    " of our customer’s. We’ll pick-up your “delivered” packages, hold, " +
                    "and deliver them when you’re available.",R.drawable.logo));


            mList.add(new AppIntroModelClass("PICKUP & DELIVER"
                    ,"If it’s needed today. We provide same day local solutions for our busy " +
                    "customers who want to provide an exceptional service to their clientele.",
                    R.drawable.logo));
            mList.add(new AppIntroModelClass("PICKUP & PREP FOR SHIPMENT"

                    ,"Benfalex Parcel Delivery is also a mobile mailroom solution for small" +
                    " businesses that can’t facilitate the expenses of an in-house mailroom. We’ll " +
                    "pick-up, package/box, weigh, and label all your small items.",
                    R.drawable.logo));

            // setup viewpager
            screenPager =findViewById(R.id.screen_viewpager);
            introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
            screenPager.setAdapter(introViewPagerAdapter);

            // setup tablayout with viewpager

            tabIndicator.setupWithViewPager(screenPager);

            // next button click Listner

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    position = screenPager.getCurrentItem();
                    if (position < mList.size()) {

                        position++;
                        screenPager.setCurrentItem(position);


                    }

                    if (position == mList.size()-1) { // when we rech to the last screen

                        // TODO : show the GETSTARTED Button and hide the indicator and the next button

                        loaddLastScreen();


                    }



                }
            });

            // tablayout add change listener


            tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    if (tab.getPosition() == mList.size()-1) {

                        loaddLastScreen();

                    }


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });



            // Get Started button click listener

            btnGetStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //open main activity
                    savePrefsData();

                    Intent mainActivitys = new Intent(getApplicationContext(), Splash.class );
                    startActivity(mainActivitys);
                    finish();

                    // also we need to save a boolean value to storage so next time when the user run the app
                    // we could know that he is already checked the intro screen activity
                    // i'm going to use shared preferences to that process




                }
            });

            // skip button click listener

            tvSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    screenPager.setCurrentItem(mList.size());
                }
            });



        }



     }



    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;



    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);



    }



}
