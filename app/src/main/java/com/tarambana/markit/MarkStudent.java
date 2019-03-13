package com.tarambana.markit;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.tarambana.markit.Adapters.SectionsPagerAdapter;
import com.tarambana.markit.DataContainers.MarkSchemePart;
import com.tarambana.markit.DataContainers.MarkSchemeSection;
import com.tarambana.markit.DataContainers.Student;
import com.tarambana.markit.DataContainers.localAssignment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class MarkStudent extends AppCompatActivity {

    static String TAG = "TASA_LOG:";

    private MobileServiceClient mClient;

    public localAssignment currentAssignment = new localAssignment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student);

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

        Bundle receivedInfo = getIntent().getExtras();
        String unitSelected = receivedInfo.getString("unit");
        int assignmentSelected = receivedInfo.getInt("assignment");
        int groupSelected = receivedInfo.getInt("group");
        int studentIDSelected = receivedInfo.getInt("studentID");
        Log.d(TAG, "Bundle retrieved correctly in 2nd activity");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(unitSelected + " | Assignment " + assignmentSelected + " | Group " + groupSelected);

        TextView studentIDTV = (TextView) findViewById(R.id.StudentIDTV);
        studentIDTV.setText("ID: " + studentIDSelected);

        currentAssignment.setStudentID(studentIDSelected);
        currentAssignment.setAssignmentNumber(assignmentSelected);

        getAssignmentPartsFromCloud("PARTASSIGNMENTID", assignmentSelected, studentIDSelected);
    }

    private void setUpTabLayoutFragments() {

        Log.d(TAG, "Setting up tab layout");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        setSupportActionBar(toolbar);

        viewPager.setOffscreenPageLimit(5);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), currentAssignment);
        Log.d(TAG, "TabLayout Adapter built");

        // PETA AQUI
        viewPager.setAdapter(adapter);
        Log.d(TAG, "Adapter set");

        tabLayout.setupWithViewPager(viewPager);
        Log.d(TAG, "View pager set");

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

    void getAssignmentPartsFromCloud(String field, final int condition, final int studentID){

        Log.d(TAG, "Getting assignment parts from cloud");

        mClient.getTable(MarkSchemePart.class).where().field(field).eq(condition).orderBy("PARTSECTIONID", QueryOrder.Ascending).orderBy("PARTPARTID", QueryOrder.Ascending)
                        .execute(new TableQueryCallback<MarkSchemePart>() {

            @Override
            public void onCompleted(java.util.List<MarkSchemePart> result, int count, Exception exception, ServiceFilterResponse response) {


                if (exception == null) {

                    List<String> partNames = new ArrayList<>();
                    List<Integer> partMarks = new ArrayList<>();
                    List<Integer> partNumbers = new ArrayList<>();

                    for (MarkSchemePart part : result) {
                        partNames.add(part.getPartName());
                        partMarks.add(part.getPartAvailableMarks());
                        partNumbers.add(part.getPartID());
                    }

                    Log.d(TAG, "Found " + partNames.size() + " assignment parts");

                    currentAssignment.setPartNames(partNames);
                    currentAssignment.setPartNumbers(partNumbers);
                    currentAssignment.setPartMarks(partMarks);

                    getAssignmentSectionsFromCloud("SECTIONASSIGNMENTID", condition, studentID);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void getAssignmentSectionsFromCloud(String field, int condition, final int studentID){

        Log.d(TAG, "Getting assignment parts from cloud");

        mClient.getTable(MarkSchemeSection.class).where().field(field).eq(condition)
                .orderBy("SECTIONID", QueryOrder.Ascending).execute(new TableQueryCallback<MarkSchemeSection>() {

            @Override
            public void onCompleted(java.util.List<MarkSchemeSection> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    List<String> sectionNames = new ArrayList<>();
                    List<Integer> sectionMarks = new ArrayList<>();
                    List<Integer> sectionNumbers = new ArrayList<>();

                    for (MarkSchemeSection section : result) {
                        sectionNames.add(section.getSectionName());
                        sectionMarks.add(section.getSectionAvailableMarks());
                        sectionNumbers.add(section.getSectionID());
                    }

                    Log.d(TAG, "Found " + sectionNames.size() + " assignment sections");

                    currentAssignment.setSectionNames(sectionNames);
                    currentAssignment.setSectionNumbers(sectionNumbers);
                    currentAssignment.setSectionMarks(sectionMarks);

                    getStudentInfoFromCloud("STUDENTID", studentID);
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void getStudentInfoFromCloud(String field, int condition){

        Log.d(TAG, "Getting assignment parts from cloud");

        mClient.getTable(Student.class).where().field(field).eq(condition)
                .orderBy("STUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<Student>() {

            @Override
            public void onCompleted(java.util.List<Student> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    for (Student student : result) {
                        currentAssignment.setStudentFirstName(student.getStudentFirstName());
                        currentAssignment.setStudentFirstName(student.getStudentFirstName());
                    }

                    setUpTabLayoutFragments();
                }

                else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }
}