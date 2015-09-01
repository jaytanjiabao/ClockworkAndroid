package com.android.clockwork.presenter;

import android.content.Context;

import com.android.clockwork.model.LoginManager;
import com.android.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class LoginPresenter implements LoginListener {
    private PreludeActivity preludeActivity;
    private LoginManager loginManager;
    Context currentContext;

    public LoginPresenter(PreludeActivity preludeActivity) {
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
    }

    public void validateCredentials(String userEmail, String userPassword) {
        this.loginManager = new LoginManager(currentContext);
        loginManager.execute("https://clockwork-api.herokuapp.com/users/sign_in.json");
        loginManager.login(userEmail, userPassword, this);
    }

    public void onSuccess () {
        preludeActivity.navigateToHome();
    }
}