package com.tarambana.markit.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.List;
import java.util.Map;

public class MarkStudentFragment extends Fragment {

    public localSection currentSection = new localSection();

    public MarkStudentFragment() {
        // Required empty public constructor
    }

    public static MarkStudentFragment newInstance(Bundle bundleReceived) {
        MarkStudentFragment fragment = new MarkStudentFragment();
        fragment.setArguments(bundleReceived);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentSection.setAssignmentID(getArguments().getInt("assignmentID"));
            currentSection.setSectionID(getArguments().getInt("sectionID"));
            currentSection.setSectionName(getArguments().getString("sectionName"));

            for (int i = 0; i < getArguments().size() - 3; i++){
                currentSection.setPartIDPartName(getArguments().getInt("partID" + i), getArguments().getString("partName" + i));
                currentSection.setPartNamePartMark(getArguments().getString("partName" + i), getArguments().getInt("partMarks" + i));
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mark_student, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

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

    void SetUpCheckBoxReactions(){
        CheckBox checkBox1 = (CheckBox) getView().findViewById(R.id.markCB1);
        CheckBox checkBox2 = (CheckBox) getView().findViewById(R.id.markCB2);
        CheckBox checkBox3 = (CheckBox) getView().findViewById(R.id.markCB3);
        CheckBox checkBox4 = (CheckBox) getView().findViewById(R.id.markCB4);
        CheckBox checkBox5 = (CheckBox) getView().findViewById(R.id.markCB5);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Display toast that says marks added
                    // Save those marks to some sort of counter
                    Toast.makeText(getContext(), "yup", Toast.LENGTH_SHORT).show();
                }

                else {
                    // take away from the marks?
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // find your way back into putting marks into currentAssignment
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // find your way back into putting marks into currentAssignment
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // find your way back into putting marks into currentAssignment
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // find your way back into putting marks into currentAssignment
            }
        });
    }
}
 