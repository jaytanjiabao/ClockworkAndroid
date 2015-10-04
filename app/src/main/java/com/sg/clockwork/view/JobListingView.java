package com.sg.clockwork.view;

import com.sg.clockwork.presenter.JobListingPresenter;

/**
 * Created by jiabao.tan.2012 on 18/8/2015.
 */
public interface JobListingView {
    //public void showProgress();

   // public void hideProgress();

    public void onNoListingError();

    public void displayJobListing(JobListingPresenter presenter);
}