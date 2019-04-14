package com.tarambana.markit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.tarambana.markit.Adapters.SectionsPagerAdapter;
import com.tarambana.markit.DataContainers.MarkSchemePart;
import com.tarambana.markit.DataContainers.MarkSchemeSection;
import com.tarambana.markit.DataContainers.Student;
import com.tarambana.markit.DataContainers.StudentTotalMarks;
import com.tarambana.markit.DataContainers.localAssignment;
import com.tarambana.markit.Fragments.MarkStudentFragment;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class MarkStudent extends AppCompatActivity implements MarkStudentFragment.MarksToActivityPasser, NavigationView.OnNavigationItemSelectedListener {

    static String TAG = "TASA_LOG:";

    int failCountAzureConnection = 0;

    private MobileServiceClient mClientAzureConnection;

    public localAssignment currentActiveAssignment = new localAssignment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student);

        try {
            Log.d(TAG, "Attempting to connect to Azure site");

            mClientAzureConnection = new MobileServiceClient(
                    "https://bookchoice.azurewebsites.net",
                    this
            );

        } catch (MalformedURLException e) {
            Log.d(TAG, "Malformed URL in connection to Azure site");
            e.printStackTrace();
        }

        Toolbar toolbarNavBar = (Toolbar) findViewById(R.id.toolbarMark);
        setSupportActionBar(toolbarNavBar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_mark_student);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarNavBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        Bundle receivedInfoFromMainActivity = getIntent().getExtras();
        String unitSelected = receivedInfoFromMainActivity.getString("unit");
        int assignmentSelected = receivedInfoFromMainActivity.getInt("assignment");
        int groupSelected = receivedInfoFromMainActivity.getInt("group");
        int studentIDSelected = receivedInfoFromMainActivity.getInt("studentID");
        Log.d(TAG, "Bundle retrieved correctly in 2nd activity");

        toolbarNavBar.setTitle(unitSelected + " | Assignment " + assignmentSelected + " | Group " + groupSelected);

        TextView studentIDTV = (TextView) findViewById(R.id.StudentIDTV);
        studentIDTV.setText("ID: " + studentIDSelected);

        currentActiveAssignment.setStudentID(studentIDSelected);
        currentActiveAssignment.setAssignmentNumber(assignmentSelected);

        getSectionInfoFromCloud("SECTIONASSIGNMENTID", assignmentSelected, studentIDSelected);
        UpdateTotalMarksTag(currentActiveAssignment);
    }

    // region TODO - deal with navigation bar
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_mark_student);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.student_selection) {
            item.setChecked(true);
            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        } else if (id == R.id.make_mark_scheme) {
            item.setChecked(true);
        }
        else if (id == R.id.check_marks) {
            item.setChecked(true);
            Intent myIntent = new Intent(this, GradeConsult.class);
            this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_mark_student);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // endregion

    // region CLOUD FUNCTIONS
    void getSectionInfoFromCloud(final String field, final int condition, final int studentID) {

        Log.d(TAG, "Getting Section Info from cloud");

        // This query must download
        mClientAzureConnection.getTable(MarkSchemeSection.class)
                .where().field(field).eq(condition)
                .orderBy("SECTIONID", QueryOrder.Ascending)
                .execute(new TableQueryCallback<MarkSchemeSection>() {

            @Override
            public void onCompleted(java.util.List<MarkSchemeSection> resultSection, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {


                    for (MarkSchemeSection sectionDownloaded : resultSection) {
                        currentActiveAssignment.setSectionIDSectionName(sectionDownloaded.getSectionID(), sectionDownloaded.getSectionName());
                    }

                    getPartInfoFromCloud("PARTASSIGNMENTID", condition, studentID);

                } else if (failCountAzureConnection < 3) {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                    getSectionInfoFromCloud(field, condition, studentID);
                    failCountAzureConnection++;
                }
                else {
                    Log.d(TAG, "4th time an exception has been found found: " + exception.getMessage());
                }
            }
        });
    }

    void getPartInfoFromCloud(final String field, final int condition, final int studentID) {

        Log.d(TAG, "Getting Part Info from cloud");

        // This query must download
        mClientAzureConnection.getTable(MarkSchemePart.class)
                .where().field(field).eq(condition)
                .orderBy("PARTASSIGNMENTID", QueryOrder.Ascending)
                .execute(new TableQueryCallback<MarkSchemePart>() {

                    @Override
                    public void onCompleted(java.util.List<MarkSchemePart> resultPart, int count, Exception exception, ServiceFilterResponse response) {

                        if (exception == null) {

                            Log.d(TAG, "Transaction correct");

                            for (MarkSchemePart partDownloaded : resultPart) {
                                currentActiveAssignment.setPartIDPartName(partDownloaded.getPartID(), partDownloaded.getPartName());
                                currentActiveAssignment.setPartNamePartMark(partDownloaded.getPartName(), partDownloaded.getPartAvailableMarks());
                                currentActiveAssignment.setPartIDSectionID(partDownloaded.getPartID(), partDownloaded.getPartSectionID());
                                currentActiveAssignment.setPartIDPartCorrect(partDownloaded.getPartID(), false);
                            }

                            getStudentInfoFromCloud("STUDENTID", studentID);

                        } else {
                            Log.d(TAG, "Exception found: " + exception.getMessage());
                        }
                    }
                });
    }

    void getStudentInfoFromCloud(String field, int condition) {

        Log.d(TAG, "Getting student info from cloud");

        mClientAzureConnection.getTable(Student.class).where().field(field).eq(condition)
                .orderBy("STUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<Student>() {

            @Override
            public void onCompleted(java.util.List<Student> resultStudentInfo, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    for (Student student : resultStudentInfo) {
                        // Set up all of the student information in the local assignment
                        currentActiveAssignment.setStudentFirstName(student.getStudentFirstName());
                        currentActiveAssignment.setStudentLastName(student.getStudentLastName());
                        currentActiveAssignment.setStudentID(student.getStudentID());
                        currentActiveAssignment.setStudentMarks(0);
                    }

                    setUpTabLayoutFragments();
                    setUpSaveButton();

                } else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void addItemsToTable(localAssignment assignmentToSave){

        final StudentTotalMarks studentMarks = new StudentTotalMarks();
        studentMarks.setStudentTotalMarksStudentID(assignmentToSave.studentID);
        studentMarks.setStudentTotalMarksAssignmentID(assignmentToSave.assignmentID);
        studentMarks.setStudentTotalMarksAchieved(assignmentToSave.getStudentMarks());

        // Insert statement, directly pass the container class
        mClientAzureConnection.getTable(StudentTotalMarks.class).insert(studentMarks, new TableOperationCallback<StudentTotalMarks>() {

            @Override
            public void onCompleted(StudentTotalMarks entity, Exception exception, ServiceFilterResponse response) {

                // Always check for exceptions/completion
                if (exception == null){
                    studentMarks.totalMarksAchieved = 0;
                }

                else{
                    exception.printStackTrace();
                }
            }
        });
    }

    // endregion

    // region INTERFACE FUNCTIONS
    @Override
    public void sendDataToActivity(HashMap<Integer, Boolean> partIDPartCorrect){
        for (Map.Entry<Integer, Boolean> entry : partIDPartCorrect.entrySet()){
            if (currentActiveAssignment.partIDPartCorrect.containsKey(entry.getKey())){
                    currentActiveAssignment.partIDPartCorrect.put(entry.getKey(), entry.getValue());
            }
        }

        UpdateTotalMarksTag(currentActiveAssignment);
    }
    // endregion

    private void setUpTabLayoutFragments() {

        Log.d(TAG, "Setting up tab layout");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        setSupportActionBar(toolbar);

        viewPager.setOffscreenPageLimit(5);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), currentActiveAssignment);
        Log.d(TAG, "TabLayout Adapter built");

        viewPager.setAdapter(adapter);
        Log.d(TAG, "Adapter set");

        tabLayout.setupWithViewPager(viewPager);
        Log.d(TAG, "View pager set");

    }

    private void setUpSaveButton(){

        Button saveButton = (Button) findViewById(R.id.SaveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(saveStudentMarksInCloud(currentActiveAssignment)){
                    Toast.makeText(getBaseContext(), "Student marks updated on cloud", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "Student marks not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean saveStudentMarksInCloud(localAssignment assignmentToSave){
        for (Map.Entry<String, Integer> entry : assignmentToSave.partNamePartMark.entrySet()){
            if (assignmentToSave.partIDPartCorrect.get(getKeyForValueInHashMap(assignmentToSave.partIDPartName, entry.getKey()))){
                assignmentToSave.studentMarks += assignmentToSave.partNamePartMark.get(entry.getKey());
            }
        }
        addItemsToTable(assignmentToSave);

        assignmentToSave.studentMarks = 0;

        return true;
    }

    private void UpdateTotalMarksTag(localAssignment assignment){
        int marksCounter = 0;
        for (Map.Entry<String, Integer> entry : assignment.partNamePartMark.entrySet()){
            if (assignment.partIDPartCorrect.get(getKeyForValueInHashMap(assignment.partIDPartName, entry.getKey()))){
                marksCounter += assignment.partNamePartMark.get(entry.getKey());
            }
        }

        TextView totalMarksCounter = (TextView) findViewById(R.id.TotalMarksTV);
        totalMarksCounter.setText("Total Marks: " + marksCounter);
    }

    Integer getKeyForValueInHashMap(Map<Integer, String> inputHashMap, String value) {

        Integer keyToReturn = 0;

        if(inputHashMap.containsValue(value))
        {
            for (Map.Entry<Integer, String> entry : inputHashMap.entrySet())
            {
                if (entry.getValue().equals(value))
                {
                    keyToReturn = entry.getKey();
                }
            }
        }
        return keyToReturn;
    }
}