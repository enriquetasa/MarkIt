package com.tarambana.markit;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.tarambana.markit.Adapters.SectionsPagerAdapter;


public class MarkStudent extends AppCompatActivity {

    static String TAG = "TASA_LOG:";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setOffscreenPageLimit(5);            // A maximum of 5 fragments (Sections) will be loaded at a time. If a certain mark scheme has more to display, the will be generated automatically when the cursor is "near them"

        // Create an adapter that knows which fragment should be shown on each page
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        Bundle receivedInfo = getIntent().getExtras();
        String unitSelected = receivedInfo.getString("unit");
        int assignmentSelected = receivedInfo.getInt("assignment");
        int groupSelected = receivedInfo.getInt("group");
        int studentIDSelected = receivedInfo.getInt("studentID");

        // TODO - some logic to get the data we need for the setup of the pager and the mark scheme itself I guess


        Log.d(TAG, "Finished with onCreate()");
    }

    // region TODO - deal with the navigation bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mark_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}

// TODO - So here's the deal. This activity gets launched and forms itself but also launches a fragment "fragment_mark_student.xml" that needs to be dealt with (where the information must go and be processed). I feel like I need to understand the whole ViewPager system to be able to follow the flow of the application, set up what I need and then query the cloud and get the data I need.