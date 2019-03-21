package com.tarambana.markit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.tarambana.markit.DataContainers.LabGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static public String TAG = "TASA_LOG: ";

    private MobileServiceClient mClient;

    boolean firstTimeUnit = false;
    boolean firstTimeAssignment = false;
    boolean firstTimeGroup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Log.d(TAG, "Attempting to connect to Azure site");

            mClient = new MobileServiceClient(
                    "https://bookchoice.azurewebsites.net",
                    this
            );

        } catch (MalformedURLException e){
            Log.d(TAG, "Malformed URL in connection to Azure site");
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        final Spinner unitSpinner = (Spinner) findViewById(R.id.SelectUnitSp);
        final Spinner assignmentSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
        final Spinner groupSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
        final Spinner studentSpinner = (Spinner) findViewById(R.id.SelectStudentSp);


        Button MarkStudentBtn = (Button) findViewById(R.id.MarkStudentBtn);
        MarkStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MarkStudent.class);
                Bundle selectionInfo = new Bundle();
                selectionInfo.putString("unit", unitSpinner.getSelectedItem().toString());
                selectionInfo.putInt("assignment", (int)assignmentSpinner.getSelectedItem());
                selectionInfo.putInt("group", (int)groupSpinner.getSelectedItem());
                selectionInfo.putInt("studentID", (int)studentSpinner.getSelectedItem());
                intent.putExtras(selectionInfo);
                Log.d(TAG, "Going to next activity");
                startActivity(intent);
            }
        });

        // this is the only one that will actually run at runtime, the others need to launch as listeners once a certain spinner is clicked
        refreshUnitDropDown("deleted","false");

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeUnit){
                refreshAssignmentDropDown("LABGROUPUNIT", unitSpinner.getSelectedItem().toString());
                }
                firstTimeUnit = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        assignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeAssignment){
                refreshGroupDropDown("LABGROUPASSIGNMENTID", assignmentSpinner.getSelectedItem().toString());
              }
              firstTimeAssignment = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeGroup){
                refreshStudentDropDown("LABGROUPNUMBER", groupSpinner.getSelectedItem().toString());
                }

                firstTimeGroup = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button markStudentButton = (Button) findViewById(R.id.MarkStudentBtn);
                markStudentButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // region TODO - deal with navigation bar
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.student_selection) {
            item.setChecked(true);
        } else if (id == R.id.make_mark_scheme) {
            item.setChecked(true);
        }
        else if (id == R.id.check_marks) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // endregion

    void refreshUnitDropDown(String field, String condition){

        Log.d(TAG, "Refreshing course unit dropdown");
        // This is how a basic query is executed, in this case all IDs
        mClient.getTable(LabGroup.class).where().field(field).eq(condition)
                .execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    // Great success in refreshing
                    // Do something if you wish, result contains your data
                    List<String> unitSpinnerList = new ArrayList<>();
                    unitSpinnerList.add("Please select a unit");

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(unitSpinnerList.contains(unit.getLabGroupUnit()))) {
                            unitSpinnerList.add(unit.getLabGroupUnit());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, unitSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner unitSpinner = (Spinner) findViewById(R.id.SelectUnitSp);
                    unitSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void refreshAssignmentDropDown(String field, String condition){

        Log.d(TAG, "Refreshing assignment dropdown");
        // This is how a basic query is executed, in this case all IDs
        mClient.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPASSIGNMENTID", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    // Great success in refreshing
                    // Do something if you wish, result contains your data
                    List<Integer> unitSpinnerList = new ArrayList<>();
                    unitSpinnerList.add(3142);

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(unitSpinnerList.contains(unit.getLabGroupAssignmentID()))) {
                            unitSpinnerList.add(unit.getLabGroupAssignmentID());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, unitSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner unitSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
                    unitSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void refreshGroupDropDown(String field, String condition){

        Log.d(TAG, "Refreshing group dropdown");
        // This is how a basic query is executed, in this case all IDs
        mClient.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPNUMBER", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    // Great success in refreshing
                    // Do something if you wish, result contains your data
                    List<Integer> unitSpinnerList = new ArrayList<>();
                    unitSpinnerList.add(3142);

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(unitSpinnerList.contains(unit.getLabGroupNumber()))) {
                            unitSpinnerList.add(unit.getLabGroupNumber());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, unitSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner unitSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
                    unitSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void refreshStudentDropDown(String field, String condition){

        Log.d(TAG, "Refreshing student dropdown");
        // This is how a basic query is executed, in this case all IDs
        mClient.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPSTUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    // Great success in refreshing
                    // Do something if you wish, result contains your data
                    List<Integer> unitSpinnerList = new ArrayList<>();
                    unitSpinnerList.add(3142);

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(unitSpinnerList.contains(unit.getLabGroupStudentID()))) {
                            unitSpinnerList.add(unit.getLabGroupStudentID());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, unitSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner unitSpinner = (Spinner) findViewById(R.id.SelectStudentSp);
                    unitSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }
}