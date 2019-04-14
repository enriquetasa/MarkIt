package com.tarambana.markit.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tarambana.markit.DataContainers.localAssignment;
import com.tarambana.markit.Fragments.MarkStudentFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Note that the maximum amount of sections allowed per assignment is 5
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public localAssignment currentActiveAssignment;

    public SectionsPagerAdapter(FragmentManager fm, localAssignment inputAssignment) {
        super(fm);
        this.currentActiveAssignment = inputAssignment;
    }

    @Override
    public Fragment getItem(int position) {

        MarkStudentFragment fragment = new MarkStudentFragment();

        if (position == 0) {
            fragment = MarkStudentFragment.newInstance(SplitAssignmentIntoSection(currentActiveAssignment, position + 1));
        } else if (position == 1){
            fragment = MarkStudentFragment.newInstance(SplitAssignmentIntoSection(currentActiveAssignment, position + 1));
        } else if (position == 2){
            fragment = MarkStudentFragment.newInstance(SplitAssignmentIntoSection(currentActiveAssignment, position + 1));
        } else if (position == 3) {
            fragment = MarkStudentFragment.newInstance(SplitAssignmentIntoSection(currentActiveAssignment, position + 1));
        } else {
            fragment = MarkStudentFragment.newInstance(SplitAssignmentIntoSection(currentActiveAssignment, position + 1));
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return currentActiveAssignment.sectionIDSectionName.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return (currentActiveAssignment.assignmentID + ".1");
            case 1:
                return (currentActiveAssignment.assignmentID + ".2");
            case 2:
                return (currentActiveAssignment.assignmentID + ".3");
            case 3:
                return (currentActiveAssignment.assignmentID + ".4");
            case 4:
                return (currentActiveAssignment.assignmentID + ".5");
            default:
                return null;
        }
    }

    Bundle SplitAssignmentIntoSection(localAssignment inputAssignment, int sectionID){
        Bundle bundleToReturn = new Bundle();

        bundleToReturn.putInt("assignmentID", inputAssignment.getAssignmentNumber());
        bundleToReturn.putInt("sectionID", sectionID);
        bundleToReturn.putString("sectionName", inputAssignment.getSectionIDSectionName().get(sectionID));

        for(int i = 1; i < inputAssignment.partIDSectionID.size(); i++){

                List<Integer> partIDList = getAllKeysForValueInHashMap(inputAssignment.partIDSectionID, sectionID);

                for (int j = 0; j < partIDList.size(); j++){

                    bundleToReturn.putInt("partID" + j, partIDList.get(j));
                    bundleToReturn.putString("partName" + j, inputAssignment.getPartIDPartName().get(partIDList.get(j)));
                    bundleToReturn.putInt("partMarks" + j, inputAssignment.getPartNamePartMark().get(inputAssignment.getPartIDPartName().get(partIDList.get(j))));
                }
        }

        return  bundleToReturn;
    }

    List<Integer> getAllKeysForValueInHashMap(Map<Integer, Integer> inputHashMap, int value)
    {
        List<Integer> keyToReturn = new ArrayList<>();

        if(inputHashMap.containsValue(value))
        {
            for (Map.Entry<Integer, Integer> entry : inputHashMap.entrySet())
            {
                if (entry.getValue().equals(value))
                {
                    keyToReturn.add(entry.getKey());
                }
            }
        }
        return keyToReturn;
    }

}