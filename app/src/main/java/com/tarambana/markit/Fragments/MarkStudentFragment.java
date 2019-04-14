package com.tarambana.markit.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tarambana.markit.DataContainers.localSection;
import com.tarambana.markit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkStudentFragment extends Fragment {

    public localSection currentActiveSection = new localSection();

    final static String TAG = "TASA_LOG ";

    // Declaration and instantiation of the interface
    private MarksToActivityPasser dataPasserToActivity;

    public interface MarksToActivityPasser {
        public void sendDataToActivity(HashMap<Integer, Boolean> partIDPartCorrect);
    }

    public MarkStudentFragment() {
        // Required empty public constructor
    }

    public static MarkStudentFragment newInstance(Bundle bundleReceived) {
        MarkStudentFragment fragment = new MarkStudentFragment();
        // Retrieve the bundle sent in a format compatible with the fragment concept: as arguments
        fragment.setArguments(bundleReceived);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Log.d(TAG, "MarkStudentFragment " + this.getId() + " : getting information from arguments");
            currentActiveSection.setAssignmentID(getArguments().getInt("assignmentID"));
            currentActiveSection.setSectionID(getArguments().getInt("sectionID"));
            currentActiveSection.setSectionName(getArguments().getString("sectionName"));

            for (int i = 0; i < getArguments().size() - 4; i++){
                if (getArguments().getInt("partID" + i) != 0){
                currentActiveSection.setPartIDPartName(getArguments().getInt("partID" + i), getArguments().getString("partName" + i));
                currentActiveSection.setPartNamePartMark(getArguments().getString("partName" + i), getArguments().getInt("partMarks" + i));
                currentActiveSection.setPartIDPartCorrect(getArguments().getInt("partID" + i), false);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MarksToActivityPasser){
        dataPasserToActivity = (MarksToActivityPasser) context;
            Log.d(TAG, "MarkStudentFragment " + this.getId() + " : data passer instantiated");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mark_student, container, false);
        return rootView;
    }

    // This method is called every time the fragment is instantiated in UI and sets up the UI section of its function: checkboxes are responsive, etc...
    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "MarkStudentFragment " + this.getFragmentManager().findFragmentById(R.id.markCB1) + " : fragment started");

        TextView sectionTitle = (TextView) getView().findViewById(R.id.sectionTitleTV);

        List<CheckBox> checkBoxesToActivate = new ArrayList<>();
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB1));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB2));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB3));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB4));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB5));

        SetUpCheckBoxReactions();

        sectionTitle.setText(currentActiveSection.getSectionName());

        int i = 0;
        for (Map.Entry<String, Integer> entry : currentActiveSection.partNamePartMark.entrySet()) {
            if (entry.getKey() == null){

            } else{
            checkBoxesToActivate.get(i).setText(entry.getKey());
            checkBoxesToActivate.get(i).setVisibility(View.VISIBLE);
            i++;
            }
        }
    }

    // This method sets up all the checkboxes in the marking UI to respond to user clicks, etc...
    void SetUpCheckBoxReactions(){

        final CheckBox checkBox1 = (CheckBox) getView().findViewById(R.id.markCB1);
        final CheckBox checkBox2 = (CheckBox) getView().findViewById(R.id.markCB2);
        final CheckBox checkBox3 = (CheckBox) getView().findViewById(R.id.markCB3);
        final CheckBox checkBox4 = (CheckBox) getView().findViewById(R.id.markCB4);
        final CheckBox checkBox5 = (CheckBox) getView().findViewById(R.id.markCB5);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox1.getText().toString()), true);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }

                else {
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox1.getText().toString()), false);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);

                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox2.getText().toString()), true);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }

                else {
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox2.getText().toString()), false);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox3.getText().toString()), true);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }

                else {
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox3.getText().toString()), false);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox4.getText().toString()), true);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }

                else {
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox4.getText().toString()), false);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox5.getText().toString()), true);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }

                else {
                    currentActiveSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentActiveSection.partIDPartName, checkBox5.getText().toString()), false);
                    dataPasserToActivity.sendDataToActivity(currentActiveSection.partIDPartCorrect);
                }
            }
        });
    }

    // Utility method to get the key that corresponds to the input value in a given hashmap
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
 