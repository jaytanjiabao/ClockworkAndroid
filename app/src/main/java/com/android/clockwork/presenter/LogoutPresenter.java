package com.android.clockwork.presenter;


import android.content.Context;

import com.android.clockwork.model.LogoutManager;
import com.android.clockwork.view.tab.ProfileFragment;

/**
 * Created by Hoi Chuen on 22/8/2015.
 */
public class LogoutPresenter implements LogoutListener {

    private ProfileFragment profileFragment;
    Context currentContext;
    private LogoutManager logoutManager;

    public LogoutPresenter (ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
        this.currentContext = profileFragment.getActivity().getApplicationContext();
    }

    public void logOut (){
        this.logoutManager = new LogoutManager(currentContext,this);
        logoutManager.execute("https://clockwork-api.herokuapp.com/users/sign_out.json");
    }

    public void onSuccess () {
        profileFragment.navigateToLogin();
    }
}
