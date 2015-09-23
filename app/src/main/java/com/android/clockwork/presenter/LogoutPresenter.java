package com.android.clockwork.presenter;


import android.app.ProgressDialog;
import android.content.Context;

import com.android.clockwork.model.APIManager;
import com.android.clockwork.model.LogoutManager;
import com.android.clockwork.view.tab.ProfileFragment;

/**
 * Created by Hoi Chuen on 22/8/2015.
 */
public class LogoutPresenter implements LogoutListener {

    private ProfileFragment profileFragment;
    Context currentContext;
    private LogoutManager logoutManager;
    ProgressDialog dialog;
    APIManager apiManager;

    public LogoutPresenter (ProfileFragment profileFragment, ProgressDialog dialog) {
        this.profileFragment = profileFragment;
        this.currentContext = profileFragment.getActivity().getApplicationContext();
        this.dialog = dialog;
        apiManager = new APIManager();
    }

    public void logOut (){
        this.logoutManager = new LogoutManager(currentContext,this,dialog);
        logoutManager.execute(apiManager.logout());
    }

    public void onSuccess () {
        profileFragment.navigateToLogin();
    }
}
