package com.tarambana.markit.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarambana.markit.R;

public class MarkStudentFragment extends Fragment {


    public MarkStudentFragment() {
        // Required empty public constructor
    }

    public static MarkStudentFragment newInstance(String param1, String param2) {
        MarkStudentFragment fragment = new MarkStudentFragment();
        // TODO - Initialise all that will be needed here my friend
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

}
