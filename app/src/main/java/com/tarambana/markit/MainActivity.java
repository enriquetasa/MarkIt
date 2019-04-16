package com.tarambana.markit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.tarambana.markit.DataContainers.LabGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static public String TAG = "TASA_LOG ";

    // Object declared to interact with Azure cloud
    private MobileServiceClient mClientAzureConnection;

    int failCountAzureConnection = 0;

    boolean firstTimeUnitDropdownRefreshed = true;
    boolean firstTimeAssignmentDropdownRefreshed = true;
    boolean firstTimeGroupDropdownRefreshed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Log.d(TAG, "MainActivity: attempting to connect to Azure site");

            mClientAzureConnection = new MobileServiceClient(
                    "https://bookchoice.azurewebsites.net",
                    this
            );
            Log.d(TAG, "MainActivity: success in connecting to Azure");

        } catch (MalformedURLException e){
            Log.d(TAG, "MainActivity: malformed URL in connection to Azure site");
            Toast.makeText(getApplicationContext(), "Internet connection issue, reconnect and try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Log.d(TAG, "MarkStudent: bundle retrieved correctly in 2nd activity");
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

        Log.d(TAG, "MainActivity: toolbar, drawer and navigation view setup correctly");

        // Define what happens when the button is clicked (open MarkStudent activity, sending it selected student and assignment data)
        Button MarkStudentBtn = (Button) findViewById(R.id.MarkStudentBtn);
        MarkStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner unitSpinner = (Spinner) findViewById(R.id.SelectUnitSp);
                Spinner assignmentSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
                Spinner groupSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
                Spinner studentSpinner = (Spinner) findViewById(R.id.SelectStudentSp);

                // Intent built and keys added to it to specify the course, assignment, group and student ID selected for marking. This data will reach the newly started activity and will be used there.
                Intent intent = new Intent(getApplicationContext(), MarkStudent.class);
                Bundle selectionInfo = new Bundle();
                selectionInfo.putString("unit", unitSpinner.getSelectedItem().toString());
                selectionInfo.putInt("assignment", (int)assignmentSpinner.getSelectedItem());
                selectionInfo.putInt("group", (int)groupSpinner.getSelectedItem());
                selectionInfo.putInt("studentID", (int)studentSpinner.getSelectedItem());
                intent.putExtras(selectionInfo);
                Log.d(TAG, "MainActivity: going to MarkStudent activity with assignment " + (int)assignmentSpinner.getSelectedItem());
                startActivity(intent);
            }
        });

        SetUpSpinners();

        refreshUnitDropDownWithCloudData("deleted","false");
    }

    // This method will set up the dropdown menus and refresh them sequentially when the user selects data from them
    private void SetUpSpinners() {
        final Spinner unitSpinner = (Spinner) findViewById(R.id.SelectUnitSp);
        final Spinner assignmentSpinner = (Spinner) findViewById(R.id.SelectAssignmentSp);
        final Spinner groupSpinner = (Spinner) findViewById(R.id.SelectGroupSp);
        final Spinner studentSpinner = (Spinner) findViewById(R.id.SelectStudentSp);

        // When a value is selected in the spinner
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // If this is not the first time a value is selected, download the data (first time happens when UI is drawn, this is just a way of guaranteeing user input in populating these)
                if (!firstTimeUnitDropdownRefreshed){
                refreshAssignmentDropDownWithCloudData("LABGROUPUNIT", unitSpinner.getSelectedItem().toString());
                }

                firstTimeUnitDropdownRefreshed = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        assignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!firstTimeAssignmentDropdownRefreshed){
                refreshGroupDropDownWithCloudData("LABGROUPASSIGNMENTID", assignmentSpinner.getSelectedItem().toString());
              }

              firstTimeAssignmentDropdownRefreshed = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!firstTimeGroupDropdownRefreshed){
                refreshStudentDropDownWithCloudData("LABGROUPNUMBER", "LABGROUPASSIGNMENTID", groupSpinner.getSelectedItem().toString(), assignmentSpinner.getSelectedItem().toString());
                }

                firstTimeGroupDropdownRefreshed = false;
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

    // This method downloads a "LabGroup" class with the information required from the Azure SQL database, and allows that class to be inspected and manipulated for use of the data it contains. The other methods below with similar names work the same but download different types of data, and thus different classes
    void refreshUnitDropDownWithCloudData(String field, String condition){

        Log.d(TAG, "MainActivity: populating course unit dropdown with cloud data");

        // This line is Azure's adaptor's way of executing an SQL query: the LabGroup class downloaded contains the information where the passed field meets the passed condition
        mClientAzureConnection.getTable(LabGroup.class).where().field(field).eq(condition)
                .execute(new TableQueryCallback<LabGroup>() {

                    // Listener for the result of the transaction with Azure
                    @Override
                    public void onCompleted(java.util.List<LabGroup> resultLabGroup, int count, Exception exception, ServiceFilterResponse response) {

                        // If there's no exception and all goes well, set up the spinner from the result of the transaction
                        if (exception == null) {

                            List<String> unitSpinnerList = new ArrayList<>();
                            unitSpinnerList.add("Please select a unit");

                            Log.d(TAG, "MainActivity: course unit spinner data successfully retrieved from Azure");

                            for (LabGroup unit : resultLabGroup) {
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
                        else if (failCountAzureConnection < 3){
                            Log.d(TAG, "MainActivity: reattempting course unit data download");
                            refreshUnitDropDownWithCloudData("deleted","false");
                        }
                        else {
                            Log.d(TAG, "MainActivity: exception found: " + exception.getMessage());
                            Toast.makeText(getApplicationContext(), "Internet connection issue, reconnect and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void refreshAssignmentDropDownWithCloudData(String field, String condition){

        Log.d(TAG, "MainActivity: populating assignment dropdown with cloud data");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPASSIGNMENTID", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> assignmentSpinnerList = new ArrayList<>();
                    assignmentSpinnerList.add(000000000);

                    Log.d(TAG, "MainActivity: assignment spinner data successfully retrieved from Azure");

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

        Log.d(TAG, "MainActivity: populating lab group dropdown with cloud data");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field).eq(condition)
                .orderBy("LABGROUPNUMBER", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> groupSpinnerList = new ArrayList<>();
                    groupSpinnerList.add(000000000);

                    Log.d(TAG, "MainActivity: lab group spinner data successfully retrieved from Azure");

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

        Log.d(TAG, "MainActivity: populating student dropdown with cloud data");

        mClientAzureConnection.getTable(LabGroup.class).where().field(field1).eq(condition1).and().field(field2).eq(condition2)
                .orderBy("LABGROUPSTUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<LabGroup>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<LabGroup> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> studentSpinnerList = new ArrayList<>();
                    studentSpinnerList.add(000000000);

                    Log.d(TAG, "MainActivity: student spinner data successfully retrieved from Azure");

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

    // System required method
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // System required method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // System required method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This method determines what happens when something is selected in the navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.student_selection) {
            Log.d(TAG, "MainActivity: student selection activity selected in navigation drawer");
            item.setChecked(true);
        } else if (id == R.id.make_mark_scheme) {
            Log.d(TAG, "MainActivity: make mark scheme activity selected in navigation drawer");
            item.setChecked(true);
        } else if (id == R.id.check_marks) {
            Log.d(TAG, "MainActivity: grade consult activity selected in navigation drawer");
            item.setChecked(true);
            Intent myIntent = new Intent(this, GradeConsult.class);
            this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}