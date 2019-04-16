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

    static String TAG = "TASA_LOG ";

    int failCountAzureConnection = 0;

    // Object declared to interact with Azure cloud
    private MobileServiceClient mClientAzureConnection;

    public localAssignment currentActiveAssignment = new localAssignment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student);

        try {
            Log.d(TAG, "MarkStudent: attempting to connect to Azure site");

            mClientAzureConnection = new MobileServiceClient(
                    "https://bookchoice.azurewebsites.net",
                    this
            );
            Log.d(TAG, "MarkStudent: success in connecting to Azure");

        } catch (MalformedURLException e) {
            Log.d(TAG, "MarkStudent: malformed URL in connection to Azure site");
            Toast.makeText(getApplicationContext(), "Internet connection issue, reconnect and try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        // Set up toolbar, drawer and navigation bar for UI
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

        // Retrieve information sent by MainActivity in bundle
        Bundle receivedInfoFromMainActivity = getIntent().getExtras();
        String unitSelected = receivedInfoFromMainActivity.getString("unit");
        int assignmentSelected = receivedInfoFromMainActivity.getInt("assignment");
        int groupSelected = receivedInfoFromMainActivity.getInt("group");
        int studentIDSelected = receivedInfoFromMainActivity.getInt("studentID");
        Log.d(TAG, "MarkStudent: bundle retrieved correctly in 2nd activity");

        // Set toolbar title
        toolbarNavBar.setTitle(unitSelected + " | Assignment " + assignmentSelected + " | Group " + groupSelected);
        Log.d(TAG, "MarkStudent: toolbar string set");

        // Set student ID
        TextView studentIDTV = (TextView) findViewById(R.id.StudentIDTV);
        studentIDTV.setText("ID: " + studentIDSelected);

        currentActiveAssignment.setStudentID(studentIDSelected);
        currentActiveAssignment.setAssignmentNumber(assignmentSelected);

        // Download section information from cloud
        getSectionInfoFromCloud("SECTIONASSIGNMENTID", assignmentSelected, studentIDSelected);

        // Start marks tag with 0 marks
        UpdateTotalMarksTag(currentActiveAssignment);
    }

    // This method downloads a "MarkSchemeSection" class with the information required from the Azure SQL database, and allows that class to be inspected and manipulated for use of the data it contains. The other methods below with similar names work the same but download different types of data, and thus different classes
    void getSectionInfoFromCloud(final String field, final int condition, final int studentID) {

        Log.d(TAG, "MarkStudent: downloading section information from Azure");

        // This line is Azure's adaptor's way of executing an SQL query: the MarkSchemeSection class downloaded contains the information where the passed field meets the passed condition
        mClientAzureConnection.getTable(MarkSchemeSection.class)
                .where().field(field).eq(condition)
                .orderBy("SECTIONID", QueryOrder.Ascending)
                .execute(new TableQueryCallback<MarkSchemeSection>() {

            @Override
            public void onCompleted(java.util.List<MarkSchemeSection> resultSection, int count, Exception exception, ServiceFilterResponse response) {

                // If there's no exception and all goes well, set up the spinner from the result of the transaction
                if (exception == null) {

                    Log.d(TAG, "MarkStudent: section data successfully retrieved from Azure");

                    for (MarkSchemeSection sectionDownloaded : resultSection) {
                        currentActiveAssignment.setSectionIDSectionName(sectionDownloaded.getSectionID(), sectionDownloaded.getSectionName());
                    }

                    getPartInfoFromCloud("PARTASSIGNMENTID", condition, studentID);

                } else if (failCountAzureConnection < 3) {
                    Log.d(TAG, "MarkStudent: Exception found: " + exception.getMessage());
                    Log.d(TAG, "MarkStudent: reattempting section data download");
                    getSectionInfoFromCloud(field, condition, studentID);
                    failCountAzureConnection++;
                }
                else {
                    Log.d(TAG, "MarkStudent: 4th time an exception has been found: " + exception.getMessage());
                }
            }
        });
    }

    void getPartInfoFromCloud(final String field, final int condition, final int studentID) {

        Log.d(TAG, "MarkStudent: downloading part information from Azure");

        // This query must download
        mClientAzureConnection.getTable(MarkSchemePart.class)
                .where().field(field).eq(condition)
                .orderBy("PARTASSIGNMENTID", QueryOrder.Ascending)
                .execute(new TableQueryCallback<MarkSchemePart>() {

                    @Override
                    public void onCompleted(java.util.List<MarkSchemePart> resultPart, int count, Exception exception, ServiceFilterResponse response) {

                        if (exception == null) {

                            Log.d(TAG, "MarkStudent: part data successfully retrieved from Azure");

                            for (MarkSchemePart partDownloaded : resultPart) {
                                currentActiveAssignment.setPartIDPartName(partDownloaded.getPartID(), partDownloaded.getPartName());
                                currentActiveAssignment.setPartNamePartMark(partDownloaded.getPartName(), partDownloaded.getPartAvailableMarks());
                                currentActiveAssignment.setPartIDSectionID(partDownloaded.getPartID(), partDownloaded.getPartSectionID());
                                currentActiveAssignment.setPartIDPartCorrect(partDownloaded.getPartID(), false);
                            }

                            getStudentInfoFromCloud("STUDENTID", studentID);

                        } else {
                            Log.d(TAG, "MarkStudent: exception found: " + exception.getMessage());
                        }
                    }
                });
    }

    void getStudentInfoFromCloud(String field, int condition) {

        Log.d(TAG, "MarkStudent: downloading student information from Azure");

        mClientAzureConnection.getTable(Student.class).where().field(field).eq(condition)
                .orderBy("STUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<Student>() {

            @Override
            public void onCompleted(java.util.List<Student> resultStudentInfo, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    Log.d(TAG, "MarkStudent: student data successfully retrieved from Azure");

                    for (Student student : resultStudentInfo) {
                        // Set up all of the student information in the local assignment
                        currentActiveAssignment.setStudentFirstName(student.getStudentFirstName());
                        currentActiveAssignment.setStudentLastName(student.getStudentLastName());
                        currentActiveAssignment.setStudentID(student.getStudentID());
                        currentActiveAssignment.setStudentMarks(0);
                    }

                    // Once all data has been downloaded, it can be passed to and used by the fragments that will populate the tab layout
                    setUpTabLayoutFragments();
                    setUpSaveButton();

                } else {
                    Log.d(TAG, "MarkStudent: exception found: " + exception.getMessage());
                }
            }
        });
    }

    // This method adds or updates elements in the SQL database with the information passed to it via a class
    void addItemsToTable(localAssignment assignmentToSave){

        Log.d(TAG, "MarkStudent: adding item to SQL database");

        final StudentTotalMarks studentMarks = new StudentTotalMarks();
        studentMarks.setStudentTotalMarksStudentID(assignmentToSave.studentID);
        studentMarks.setStudentTotalMarksAssignmentID(assignmentToSave.assignmentID);
        studentMarks.setStudentTotalMarksAchieved(assignmentToSave.getStudentMarks());

        mClientAzureConnection.getTable(StudentTotalMarks.class).insert(studentMarks, new TableOperationCallback<StudentTotalMarks>() {

            @Override
            public void onCompleted(StudentTotalMarks entity, Exception exception, ServiceFilterResponse response) {

                // Always check for exceptions/completion
                if (exception == null){
                    Log.d(TAG, "MarkStudent: SQL insert successful");
                    studentMarks.totalMarksAchieved = 0;
                }

                else{
                    exception.printStackTrace();
                }
            }
        });
    }

    // This method implements an interface: a construct used to pass data between fragments and their containing activity
    @Override
    public void sendDataToActivity(HashMap<Integer, Boolean> partIDPartCorrect){
        for (Map.Entry<Integer, Boolean> entry : partIDPartCorrect.entrySet()){
            if (currentActiveAssignment.partIDPartCorrect.containsKey(entry.getKey())){
                    currentActiveAssignment.partIDPartCorrect.put(entry.getKey(), entry.getValue());
            }
        }

        UpdateTotalMarksTag(currentActiveAssignment);
    }

    // This method implements all the necessary logic to launch a tablayout of various fragments within this activity
    private void setUpTabLayoutFragments() {

        Log.d(TAG, "MarkStudent: setting up tab layout, fragments");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        setSupportActionBar(toolbar);

        viewPager.setOffscreenPageLimit(5);

        // More information on the adapter can be found in its class
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), currentActiveAssignment);
        Log.d(TAG, "MarkStudent: tablayout adapter built");

        viewPager.setAdapter(adapter);
        Log.d(TAG, "MarkStudent: adapter set");

        tabLayout.setupWithViewPager(viewPager);
        Log.d(TAG, "MarkStudent: view pager set and complete, tablayout is functional");

    }

    // This method sets up the save button in order to allow for data saving
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

    // This method implements the logic to prepare the data to be saved in the cloud and call the Azure adapter function to save it
    public boolean saveStudentMarksInCloud(localAssignment assignmentToSave){
        Log.d(TAG, "MarkStudent: saving mark data");
        for (Map.Entry<String, Integer> entry : assignmentToSave.partNamePartMark.entrySet()){
            if (assignmentToSave.partIDPartCorrect.get(getKeyForValueInHashMap(assignmentToSave.partIDPartName, entry.getKey()))){
                assignmentToSave.studentMarks += assignmentToSave.partNamePartMark.get(entry.getKey());
            }
        }
        addItemsToTable(assignmentToSave);

        assignmentToSave.studentMarks = 0;

        return true;
    }

    // This method updates the total marks counter on the UI
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

    // System required method
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_mark_student);
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

    // This method determines what happens when something is selected in the navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.student_selection) {
            Log.d(TAG, "MarkStudent: student selection activity selected in navigation drawer");
            item.setChecked(true);
            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        } else if (id == R.id.make_mark_scheme) {
            Log.d(TAG, "MarkStudent: make mark scheme activity selected in navigation drawer");
            item.setChecked(true);
        }
        else if (id == R.id.check_marks) {
            Log.d(TAG, "MarkStudent: grade consult activity selected in navigation drawer");
            item.setChecked(true);
            Intent myIntent = new Intent(this, GradeConsult.class);
            this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_mark_student);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
