package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.LoginManager;
import com.sg.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class LoginPresenter implements LoginListener {
    private PreludeActivity preludeActivity;
    private LoginManager loginManager;
    Context currentContext;
    ProgressBar progressBar;
    TextView statusText;
    APIManager apiManager;

    public LoginPresenter(PreludeActivity preludeActivity, ProgressBar progressBar, TextView statusText) {
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
        this.progressBar = progressBar;
        this.statusText = statusText;
        apiManager = new APIManager();
    }

    public void validateCredentials(String userEmail, String userPassword) {
        this.loginManager = new LoginManager(currentContext,progressBar,statusText);
        loginManager.execute(apiManager.login());
        loginManager.login(userEmail, userPassword, this);
    }

    public void onSuccess () {
        preludeActivity.navigateToHome();
    }

}
