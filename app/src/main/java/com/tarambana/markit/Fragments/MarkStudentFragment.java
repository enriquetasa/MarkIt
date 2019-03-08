package com.tarambana.markit.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tarambana.markit.DataContainers.localAssignment;
import com.tarambana.markit.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MarkStudentFragment extends Fragment {

    public static localAssignment currentAssignment;
    public static int fragmentPosition;

    public MarkStudentFragment() {
        // Required empty public constructor
    }

    public static MarkStudentFragment newInstance(localAssignment inputAssignment, int position) {
        MarkStudentFragment fragment = new MarkStudentFragment();
        currentAssignment = inputAssignment;
        fragmentPosition = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        CheckBox markCB1 = (CheckBox) getView().findViewById(R.id.markCB1);
        CheckBox markCB2 = (CheckBox) getView().findViewById(R.id.markCB2);
        CheckBox markCB3 = (CheckBox) getView().findViewById(R.id.markCB3);
        CheckBox markCB4 = (CheckBox) getView().findViewById(R.id.markCB4);
        CheckBox markCB5 = (CheckBox) getView().findViewById(R.id.markCB5);

        // TODO - will have to set up the checkboxes and title here
        sectionTitle.setText(currentAssignment.sectionNames.get(fragmentPosition));

        if (currentAssignment.partNames.size() > 4){
            markCB1.setText(currentAssignment.partNames.get(0));
            markCB1.setVisibility(View.VISIBLE);
            markCB2.setText(currentAssignment.partNames.get(1));
            markCB2.setVisibility(View.VISIBLE);
            markCB3.setText(currentAssignment.partNames.get(2));
            markCB3.setVisibility(View.VISIBLE);
            markCB4.setText(currentAssignment.partNames.get(3));
            markCB4.setVisibility(View.VISIBLE);
            markCB5.setText(currentAssignment.partNames.get(4));
            markCB5.setVisibility(View.VISIBLE);
        } else if (currentAssignment.partNames.size() > 3) {
            markCB1.setText(currentAssignment.partNames.get(0));
            markCB1.setVisibility(View.VISIBLE);
            markCB2.setText(currentAssignment.partNames.get(1));
            markCB2.setVisibility(View.VISIBLE);
            markCB3.setText(currentAssignment.partNames.get(2));
            markCB3.setVisibility(View.VISIBLE);
            markCB4.setText(currentAssignment.partNames.get(3));
            markCB4.setVisibility(View.VISIBLE);
        } else if (currentAssignment.partNames.size() > 2) {
            markCB1.setText(currentAssignment.partNames.get(0));
            markCB1.setVisibility(View.VISIBLE);
            markCB2.setText(currentAssignment.partNames.get(1));
            markCB2.setVisibility(View.VISIBLE);
            markCB3.setText(currentAssignment.partNames.get(2));
            markCB3.setVisibility(View.VISIBLE);
        } else if (currentAssignment.partNames.size() > 1) {
            markCB1.setText(currentAssignment.partNames.get(0));
            markCB1.setVisibility(View.VISIBLE);
            markCB2.setText(currentAssignment.partNames.get(1));
            markCB2.setVisibility(View.VISIBLE);
        } else if (currentAssignment.partNames.size() > 0) {
            markCB1.setText(currentAssignment.partNames.get(0));
            markCB1.setVisibility(View.VISIBLE);
        }
    }
}
