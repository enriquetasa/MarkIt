package com.tarambana.markit.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tarambana.markit.DataContainers.localAssignment;
import com.tarambana.markit.DataContainers.localSection;
import com.tarambana.markit.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkStudentFragment extends Fragment {

    public localSection currentSection = new localSection();

    final static String TAG = "TASA_LOG:";

    private MarksToActivityPasser dataPasser;

    public interface MarksToActivityPasser {
        public void sendDataToActivity(HashMap<Integer, Boolean> partIDPartCorrect);
    }

    //region CONSTRUCTORS
    public MarkStudentFragment() {
        // Required empty public constructor
    }

    public static MarkStudentFragment newInstance(Bundle bundleReceived) {
        MarkStudentFragment fragment = new MarkStudentFragment();
        fragment.setArguments(bundleReceived);
        return fragment;
    }

    //endregion

    // region FRAGMENT METHODS
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentSection.setAssignmentID(getArguments().getInt("assignmentID"));
            currentSection.setSectionID(getArguments().getInt("sectionID"));
            currentSection.setSectionName(getArguments().getString("sectionName"));

            for (int i = 0; i < getArguments().size() - 4; i++){
                if (getArguments().getInt("partID" + i) != 0){
                currentSection.setPartIDPartName(getArguments().getInt("partID" + i), getArguments().getString("partName" + i));
                currentSection.setPartNamePartMark(getArguments().getString("partName" + i), getArguments().getInt("partMarks" + i));
                currentSection.setPartIDPartCorrect(getArguments().getInt("partID" + i), false);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MarksToActivityPasser){
        dataPasser = (MarksToActivityPasser) context;
        Log.d(TAG, "data passer instantiated");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mark_student, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "In Fragment");

        TextView sectionTitle = (TextView) getView().findViewById(R.id.sectionTitleTV);

        List<CheckBox> checkBoxesToActivate = new ArrayList<>();
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB1));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB2));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB3));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB4));
        checkBoxesToActivate.add((CheckBox)getView().findViewById(R.id.markCB5));

        SetUpCheckBoxReactions();

        sectionTitle.setText(currentSection.getSectionName());

        int i = 0;
        for (Map.Entry<String, Integer> entry : currentSection.partNamePartMark.entrySet()) {
            if (entry.getKey() == null){

            } else{
            checkBoxesToActivate.get(i).setText(entry.getKey());
            checkBoxesToActivate.get(i).setVisibility(View.VISIBLE);
            i++;
            }
        }
    }

    // endregion

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
                    // Display toast that says marks added
                    // Save those marks to some sort of counter
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox1.getText().toString()), true);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }

                else {
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox1.getText().toString()), false);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);

                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    // Display toast that says marks added
                    // Save those marks to some sort of counter
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox2.getText().toString()), true);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }

                else {
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox2.getText().toString()), false);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Display toast that says marks added
                    // Save those marks to some sort of counter
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox3.getText().toString()), true);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }

                else {
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox3.getText().toString()), false);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Display toast that says marks added
                    // Save those marks to some sort of counter
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox4.getText().toString()), true);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }

                else {
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox4.getText().toString()), false);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Display toast that says marks added
                    // Save those marks to some sort of counter
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox5.getText().toString()), true);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }

                else {
                    currentSection.setPartIDPartCorrect(getKeyForValueInHashMap(currentSection.partIDPartName, checkBox5.getText().toString()), false);
                    dataPasser.sendDataToActivity(currentSection.partIDPartCorrect);
                }
            }
        });
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
 