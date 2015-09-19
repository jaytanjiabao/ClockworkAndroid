package com.android.clockwork.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.clockwork.model.LoginManager;
import com.android.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class LoginPresenter implements LoginListener {
    private PreludeActivity preludeActivity;
    private LoginManager loginManager;
    Context currentContext;
    ProgressBar progressBar;
    TextView statusText;

    public LoginPresenter(PreludeActivity preludeActivity, ProgressBar progressBar, TextView statusText) {
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
        this.progressBar = progressBar;
        this.statusText = statusText;
    }

    public void validateCredentials(String userEmail, String userPassword) {
        this.loginManager = new LoginManager(currentContext,progressBar,statusText);
        loginManager.execute("https://clockwork-api.herokuapp.com/users/sign_in.json");
        loginManager.login(userEmail, userPassword, this);
    }

    public void onSuccess () {
        preludeActivity.navigateToHome();
    }

    public void onFailure() {
        try {
            wait(3000);
            statusText.setVisibility(View.GONE);
        }catch (Exception e) {}

    }
}
