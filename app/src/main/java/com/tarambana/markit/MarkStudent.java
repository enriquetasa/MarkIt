package com.tarambana.markit;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
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

public class MarkStudent extends AppCompatActivity implements MarkStudentFragment.MarksToActivityPasser {

    static String TAG = "TASA_LOG:";

    int counter = 0;

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

        } catch (MalformedURLException e) {
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

        getSectionInfoFromCloud("SECTIONASSIGNMENTID", assignmentSelected, studentIDSelected);
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

    // region CLOUD FUNCTIONS
    void getSectionInfoFromCloud(final String field, final int condition, final int studentID) {

        Log.d(TAG, "Getting Section Info from cloud");

        // This query must download
        mClient.getTable(MarkSchemeSection.class)
                .where().field(field).eq(condition)
                .orderBy("SECTIONID", QueryOrder.Ascending)
                .execute(new TableQueryCallback<MarkSchemeSection>() {

            @Override
            public void onCompleted(java.util.List<MarkSchemeSection> resultSection, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {


                    for (MarkSchemeSection sectionDownloaded : resultSection) {
                        currentAssignment.setSectionIDSectionName(sectionDownloaded.getSectionID(), sectionDownloaded.getSectionName());
                    }

                    getPartInfoFromCloud("PARTASSIGNMENTID", condition, studentID);

                } else if (counter < 3) {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                    getSectionInfoFromCloud(field, condition, studentID);
                    counter++;
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
        mClient.getTable(MarkSchemePart.class)
                .where().field(field).eq(condition)
                .orderBy("PARTASSIGNMENTID", QueryOrder.Ascending)
                .execute(new TableQueryCallback<MarkSchemePart>() {

                    @Override
                    public void onCompleted(java.util.List<MarkSchemePart> resultPart, int count, Exception exception, ServiceFilterResponse response) {

                        if (exception == null) {

                            Log.d(TAG, "Transaction correct");

                            for (MarkSchemePart partDownloaded : resultPart) {
                                currentAssignment.setPartIDPartName(partDownloaded.getPartID(), partDownloaded.getPartName());
                                currentAssignment.setPartNamePartMark(partDownloaded.getPartName(), partDownloaded.getPartAvailableMarks());
                                currentAssignment.setPartIDSectionID(partDownloaded.getPartID(), partDownloaded.getPartSectionID());
                                currentAssignment.setPartIDPartCorrect(partDownloaded.getPartID(), false);
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

        mClient.getTable(Student.class).where().field(field).eq(condition)
                .orderBy("STUDENTID", QueryOrder.Ascending).execute(new TableQueryCallback<Student>() {

            @Override
            public void onCompleted(java.util.List<Student> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    for (Student student : result) {
                        // Set up all of the student information in the local assignment
                        currentAssignment.setStudentFirstName(student.getStudentFirstName());
                        currentAssignment.setStudentLastName(student.getStudentLastName());
                        currentAssignment.setStudentID(student.getStudentID());
                        currentAssignment.setStudentMarks(0);
                    }

                    setUpTabLayoutFragments();
                    setUpSaveButton();

                } else {
                    Log.d(TAG, "Exception found: " + exception.getMessage());
                }
            }
        });
    }

    void UpdateTableItem(localAssignment assignmentToSave){

        StudentTotalMarks studentMarks = new StudentTotalMarks();
        studentMarks.setStudentTotalMarksStudentID(assignmentToSave.studentID);
        studentMarks.setStudentTotalMarksAssignmentID(assignmentToSave.assignmentID);
        studentMarks.setStudentTotalMarksAchieved(assignmentToSave.getStudentMarks());

        // Update the table, automatic listener set for end of transaction with Azure
        mClient.getTable(StudentTotalMarks.class).update(studentMarks, new TableOperationCallback<StudentTotalMarks>() {
            @Override
            public void onCompleted(StudentTotalMarks entity, Exception exception, ServiceFilterResponse response) {

                // Always check for exceptions/completion
                if (exception == null){
                    // Great success in altering the object
                    // Do something if you wish
                }

                else {
                    // there has been an exception handle it
                }
            }
        });
    } // This does not get used, as we're saving all the records now

    void addItemsToTable(localAssignment assignmentToSave){

        final StudentTotalMarks studentMarks = new StudentTotalMarks();
        studentMarks.setStudentTotalMarksStudentID(assignmentToSave.studentID);
        studentMarks.setStudentTotalMarksAssignmentID(assignmentToSave.assignmentID);
        studentMarks.setStudentTotalMarksAchieved(assignmentToSave.getStudentMarks());

        // Insert statement, directly pass the container class
        mClient.getTable(StudentTotalMarks.class).insert(studentMarks, new TableOperationCallback<StudentTotalMarks>() {

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
            if (currentAssignment.partIDPartCorrect.containsKey(entry.getKey())){
                    currentAssignment.partIDPartCorrect.put(entry.getKey(), entry.getValue());
            }
        }
    }
    // endregion

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

    private void setUpSaveButton(){

        Button saveButton = (Button) findViewById(R.id.SaveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // this is where the report will be generated
                if(saveStudentMarksInCloud(currentAssignment)){
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