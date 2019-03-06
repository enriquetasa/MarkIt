package com.tarambana.markit.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tarambana.markit.Fragments.MarkStudentFragment;
import com.tarambana.markit.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // The bundle info needs to come in at some point

    // This determines the fragment for each tab TODO - must add the fragments here programatically
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MarkStudentFragment();
        } else if (position == 1){
            return new MarkStudentFragment();
        } else if (position == 2){
            return new MarkStudentFragment();
        } else {
            return new MarkStudentFragment();
        }
    }

    // This determines the number of tabs TODO - implement the amount of section programatically
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position TODO - implement the section title programatically
        switch (position) {
            case 0:
                return "bufa";
            case 1:
                return "Amic";
            case 2:
                return "Noa";
            case 3:
                return "Yea";
            default:
                return null;
        }
    }
}