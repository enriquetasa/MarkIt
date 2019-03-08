package com.tarambana.markit.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tarambana.markit.DataContainers.localAssignment;
import com.tarambana.markit.Fragments.MarkStudentFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public localAssignment currentAssignment;

    public SectionsPagerAdapter(FragmentManager fm, localAssignment inputAssignment) {
        super(fm);
        this.currentAssignment = inputAssignment;
    }

    // TODO - must add the fragments here programatically
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            MarkStudentFragment fragment = MarkStudentFragment.newInstance(currentAssignment, 0);
            return fragment;
        } else if (position == 1){
            MarkStudentFragment fragment = MarkStudentFragment.newInstance(currentAssignment, 1);
            return fragment;
        } else if (position == 2){
            MarkStudentFragment fragment = MarkStudentFragment.newInstance(currentAssignment, 2);
            return fragment;
        } else if (position == 3) {
            MarkStudentFragment fragment = MarkStudentFragment.newInstance(currentAssignment, 3);
            return fragment;
        } else if (position == 4) {
            MarkStudentFragment fragment = MarkStudentFragment.newInstance(currentAssignment, 4);
            return fragment;
        }else {
            return new MarkStudentFragment();
        }
    }

    @Override
    public int getCount() {
        return currentAssignment.sectionMarks.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return (currentAssignment.assignmentNumber + "." + currentAssignment.sectionNumbers.get(0));
            case 1:
                return (currentAssignment.assignmentNumber + "." + currentAssignment.sectionNumbers.get(1));
            case 2:
                return (currentAssignment.assignmentNumber + "." + currentAssignment.sectionNumbers.get(2));
            case 3:
                return (currentAssignment.assignmentNumber + "." + currentAssignment.sectionNumbers.get(3));
            case 4:
                return (currentAssignment.assignmentNumber + "." + currentAssignment.sectionNumbers.get(4));
            default:
                return null;
        }
    }
}

// TODO - note that the maximum amount of fragments we take is 5