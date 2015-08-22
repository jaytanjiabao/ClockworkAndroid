package com.android.clockwork.presenter;


import android.content.Context;

import com.android.clockwork.model.LogoutManager;
import com.android.clockwork.view.tab.TabFragment2;

/**
 * Created by Hoi Chuen on 22/8/2015.
 */
public class LogoutPresenter implements LogoutListener {

    private TabFragment2 tabFragment2;
    Context currentContext;
    private LogoutManager logoutManager;

    public LogoutPresenter (TabFragment2 tabFragment2) {
        this.tabFragment2 = tabFragment2;
        this.currentContext = tabFragment2.getActivity().getApplicationContext();
    }

    public void logOut (){
        this.logoutManager = new LogoutManager(currentContext,this);
        logoutManager.execute("https://clockwork-api.herokuapp.com/users/sign_out.json");
    }

    public void onSuccess () {
        tabFragment2.navigateToLogin();
    }
}
