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

    private MobileServiceClient mClientAzureConnection;

    int failCountAzureConnection = 0;

    boolean firstTimeUnitDropdownRefreshed = false;
    boolean firstTimeAssignmentDropdownRefreshed = false;
    boolean firstTimeGroupDropdownRefreshed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Log.d(TAG, "Attempting to connect to Azure site");

            mClientAzureConnection = new MobileServiceClient(
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

        Button MarkStudentBtn = (Button) findViewById(R.id.MarkStudentBtn);
        MarkStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner unitSpinner = (Spinner) findViewById(R.id.SelectUnitSp);
                Spinner assignmentSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
                Spinner groupSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
                Spinner studentSpinner = (Spinner) findViewById(R.id.SelectStudentSp);

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

        SetUpSpinners();

        refreshUnitDropDownWithCloudData("deleted","false");
    }

    private void SetUpSpinners() {
        final Spinner unitSpinner = (Spinner) findViewById(R.id.SelectUnitSp);
        final Spinner assignmentSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
        final Spinner groupSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
        final Spinner studentSpinner = (Spinner) findViewById(R.id.SelectStudentSp);

        // TODO - put these into a function
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeUnitDropdownRefreshed){
                refreshAssignmentDropDownWithCloudData("LABGROUPUNIT", unitSpinner.getSelectedItem().toString());
                }
                firstTimeUnitDropdownRefreshed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        assignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeAssignmentDropdownRefreshed){
                refreshGroupDropDownWithCloudData("LABGROUPASSIGNMENTID", assignmentSpinner.getSelectedItem().toString());
              }
              firstTimeAssignmentDropdownRefreshed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeGroupDropdownRefreshed){
                refreshStudentDropDownWithCloudData("LABGROUPNUMBER", "LABGROUPASSIGNMENTID", groupSpinner.getSelectedItem().toString(), assignmentSpinner.getSelectedItem().toString());
                }

                firstTimeGroupDropdownRefreshed = true;
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

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.student_selection) {
            item.setChecked(true);
        } else if (id == R.id.make_mark_scheme) {
            item.setChecked(true);
        }
        else if (id == R.id.check_marks) {
            item.setChecked(true);
            Intent myIntent = new Intent(this, GradeConsult.class);
            this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // endregion

    void refreshUnitDropDownWithCloudData(String field, String condition){

        Log.d(TAG, "Refreshing course unit dropdown");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field).eq(condition)
                .execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

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
                    failCountAzureConnection = 0;
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                    if (failCountAzureConnection < 3){
                        refreshUnitDropDownWithCloudData("deleted","false");
                    }
                }
            }
        });
    }

    void refreshAssignmentDropDownWithCloudData(String field, String condition){

        Log.d(TAG, "Refreshing assignment dropdown");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPASSIGNMENTID", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> assignmentSpinnerList = new ArrayList<>();
                    assignmentSpinnerList.add(000000000);

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(assignmentSpinnerList.contains(unit.getLabGroupAssignmentID()))) {
                            assignmentSpinnerList.add(unit.getLabGroupAssignmentID());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, assignmentSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner assignmentSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
                    assignmentSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void refreshGroupDropDownWithCloudData(String field, String condition){

        Log.d(TAG, "Refreshing group dropdown");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPNUMBER", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> groupSpinnerList = new ArrayList<>();
                    groupSpinnerList.add(000000000);

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(groupSpinnerList.contains(unit.getLabGroupNumber()))) {
                            groupSpinnerList.add(unit.getLabGroupNumber());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, groupSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner groupSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
                    groupSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void refreshStudentDropDownWithCloudData(String field1, String field2, String condition1, String condition2){

        Log.d(TAG, "Refreshing student dropdown");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field1).eq(condition1).and().field(field2).eq(condition2)
                .orderBy("LABGROUPSTUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> studentSpinnerList = new ArrayList<>();
                    studentSpinnerList.add(000000000);

                    Log.d(TAG, "Dropdown content successfully retrieved from cloud");

                    for (LabGroup unit : result) {
                        if (!(studentSpinnerList.contains(unit.getLabGroupStudentID()))) {
                            studentSpinnerList.add(unit.getLabGroupStudentID());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, studentSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner studentSpinner = (Spinner) findViewById(R.id.SelectStudentSp);
                    studentSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }
}