package com.sg.clockwork.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sg.clockwork.view.tab.JobListingFragment;
import com.sg.clockwork.view.tab.DashboardFragment;
import com.sg.clockwork.view.tab.ProfileFragment;
import com.sg.clockwork.view.tab.RewardsFragment;

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
                RewardsFragment tab4 = new RewardsFragment();
                return tab4;
            case 3:
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