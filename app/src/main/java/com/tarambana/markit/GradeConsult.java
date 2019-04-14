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
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.tarambana.markit.DataContainers.StudentTotalMarks;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class GradeConsult extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   final static String TAG = "TASA_LOG ";

    // Object declared to interact with Azure cloud
   private MobileServiceClient mClientAzureConnection;

   boolean firstTimeStudentDropdownRefreshed = false;

   int failCountAzureConnection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_consult);

        try {
            Log.d(TAG, "GradeConsult: attempting to connect to Azure site");

            mClientAzureConnection = new MobileServiceClient(
                    "https://bookchoice.azurewebsites.net",
                    this
            );

            Log.d(TAG, "GradeConsult: success in connecting to Azure");

        } catch (MalformedURLException e){
            Log.d(TAG, "Malformed URL in connection to Azure site");
            e.printStackTrace();
        }

        // Set up toolbar, drawer and navigation bar for UI
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGC);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_grade_consult);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        final Spinner assignmentSpinner = (Spinner) findViewById(R.id.GCSelectAssignmentSp);
        final Spinner studentSpinner = (Spinner) findViewById(R.id.GCSelectStudentSP);
        final Button checkMarksBtn = (Button) findViewById(R.id.ConsultGradeBtn);

        checkMarksBtn.setEnabled(true);
        checkMarksBtn.setVisibility(VISIBLE);

        checkMarksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStudentGrade("TOTALSTUDENTID", "TOTALASSIGNMENTID", studentSpinner.getSelectedItem().toString(), assignmentSpinner.getSelectedItem().toString());
            }
        });

        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTimeStudentDropdownRefreshed){
                String studentID = studentSpinner.getSelectedItem().toString();
                refreshAssignmentDropDown("TOTALSTUDENTID", studentID);
                firstTimeStudentDropdownRefreshed = false;

                }

                else firstTimeStudentDropdownRefreshed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        refreshStudentDropDown("TOTALSTUDENTID", "*");

    }

    // This method downloads a "Student" class with the information required from the Azure SQL database, and allows that class to be inspected and manipulated for use of the data it contains. The other methods below with similar names work the same but download different types of data, and thus different classes
    void refreshStudentDropDown(String field, String condition){

        Log.d(TAG, "GradeConsult: downloading student data");

        try{
        mClientAzureConnection.getTable(StudentTotalMarks.class).execute(new TableQueryCallback<StudentTotalMarks>() {

                    // Listener that automatically gets set for the result of the transaction with Azure
                    @Override
                    public void onCompleted(java.util.List<StudentTotalMarks> result, int count, Exception exception, ServiceFilterResponse response) {
                        if (exception == null) {

                            List<Integer> studentSpinnerList = new ArrayList<>();
                            studentSpinnerList.add(000000000);

                            Log.d(TAG, "GradeConsult: student data download successful");

                            for (StudentTotalMarks student : result) {
                                if (!(studentSpinnerList.contains(student.getStudentTotalMarksStudentID()))) {
                                    studentSpinnerList.add(student.getStudentTotalMarksStudentID());
                                }
                            }

                            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                                    getBaseContext(), android.R.layout.simple_spinner_item, studentSpinnerList);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Spinner studentSpinner = (Spinner) findViewById(R.id.GCSelectStudentSP);
                            studentSpinner.setAdapter(adapter);

                        } else if (failCountAzureConnection < 3) {
                            Log.d(TAG, "GradeConsult: Exception found: " + exception.getMessage());
                            Log.d(TAG, "GradeConsult: reattempting student data download");
                            refreshStudentDropDown("TOTALSTUDENTID", "*");
                            failCountAzureConnection++;
                        } else {
                            Log.d(TAG, "GradeConsult: exception found: " + exception.getMessage());
                        }
                    }
                });
        }
                catch (Exception e){
                    e.printStackTrace();
                }
    }

    void refreshAssignmentDropDown(String field, String condition){

        Log.d(TAG, "GradeConsult: downloading assignment data");
        // This is how a basic query is executed, in this case all IDs
        mClientAzureConnection.getTable(StudentTotalMarks.class).where().field(field).eq(condition).execute(new TableQueryCallback<StudentTotalMarks>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<StudentTotalMarks> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<Integer> assignmentSpinnerList = new ArrayList<>();
                    assignmentSpinnerList.add(000000000);

                    Log.d(TAG, "GradeConsult: assignment data download successful");

                    for (StudentTotalMarks unit : result) {
                        if (!(assignmentSpinnerList.contains(unit.getStudentTotalMarksAssignmentID()))) {
                            assignmentSpinnerList.add(unit.getStudentTotalMarksAssignmentID());
                        }
                    }

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            getBaseContext(), android.R.layout.simple_spinner_item, assignmentSpinnerList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner assignmentSpinner = (Spinner) findViewById(R.id.GCSelectAssignmentSp);
                    assignmentSpinner.setAdapter(adapter);
                }

                else {
                    Log.d(TAG, "GradeConsult: exception found: " + exception.getMessage());
                }
            }
        });
    }

    void GetStudentGrade(String field1, String field2, final String condition, final String condition2){

        Log.d(TAG, "GradeConsult: downloading grade data");
        // This is how a basic query is executed, in this case all IDs
        mClientAzureConnection.getTable(StudentTotalMarks.class).where().field(field1).eq(condition).and().field(field2).eq(condition2).execute(new TableQueryCallback<StudentTotalMarks>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<StudentTotalMarks> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<String> outputInfo = new ArrayList<>();

                    Log.d(TAG, "GradeConsult: grade data download successful");

                    for (StudentTotalMarks grade : result) {
                        outputInfo.add("Student " + condition + " achieved " + grade.getStudentTotalMarksAchieved() + " marks in assignment " + condition2);
                        }

                        String text = "";
                        TextView gradeDisplayTV = (TextView) findViewById(R.id.StudentGradeInfo);

                        for (String line : outputInfo){
                        text = text + line + "\n";
                        gradeDisplayTV.setText(text);
                        }
                }

                else {
                    Log.d(TAG, "GradeConsult: exception found: " + exception.getMessage());
                }
            }
        });
    }

    // System required method
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_grade_consult);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // System required method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // System required method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This method determines what happens when something is selected in the navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.student_selection) {
            Log.d(TAG, "GradeConsult: student selection activity selected in navigation drawer");
            item.setChecked(true);
            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        } else if (id == R.id.make_mark_scheme) {
            Log.d(TAG, "GradeConsult: make mark scheme activity selected in navigation drawer");
            item.setChecked(true);
        }
        else if (id == R.id.check_marks) {
            Log.d(TAG, "GradeConsult: grade consult activity selected in navigation drawer");
            item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_grade_consult);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
