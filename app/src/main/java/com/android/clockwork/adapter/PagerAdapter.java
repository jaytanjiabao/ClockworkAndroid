package com.android.clockwork.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.clockwork.view.tab.JobListingFragment;
import com.android.clockwork.view.tab.DashboardFragment;
import com.android.clockwork.view.tab.ProfileFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                JobListingFragment tab1 = new JobListingFragment();
                return tab1;
            case 1:
                DashboardFragment tab2 = new DashboardFragment();
                return tab2;
            case 2:
                ProfileFragment tab3 = new ProfileFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}