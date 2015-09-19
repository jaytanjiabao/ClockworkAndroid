package com.android.clockwork.view;

import com.android.clockwork.presenter.DashboardPresenter;

/**
 * Created by jiabao.tan.2012 on 2/9/2015.
 */
public interface DashboardView {
    public void showProgress();

    public void hideProgress();

    public void displayAppliedJobListing(DashboardPresenter presenter);
}
