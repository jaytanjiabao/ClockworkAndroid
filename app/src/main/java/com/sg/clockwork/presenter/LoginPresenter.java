package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.GCMServerManager;
import com.sg.clockwork.model.LoginManager;
import com.sg.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class LoginPresenter implements LoginListener {
    private PreludeActivity preludeActivity;
    private LoginManager loginManager;
    private GCMPresenter gcmPresenter;
    GCMServerManager gcmServerManager;
    Context currentContext;
    ProgressBar progressBar;
    TextView statusText;
    APIManager apiManager;
    String id;

    public LoginPresenter(PreludeActivity preludeActivity, ProgressBar progressBar, TextView statusText) {
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
        this.progressBar = progressBar;
        this.statusText = statusText;
        apiManager = new APIManager();
        this.gcmPresenter = new GCMPresenter(preludeActivity, apiManager, this);
    }

    public void validateCredentials(String userEmail, String userPassword) {
        gcmPresenter.registerGCM();
        this.loginManager = new LoginManager(currentContext,progressBar,statusText);
        loginManager.execute(apiManager.login());
        loginManager.login(userEmail, userPassword, this);
    }

    public void onSuccess() {
        this.gcmServerManager = new GCMServerManager(currentContext, this, preludeActivity);
        gcmServerManager.prepareId(id);
        gcmServerManager.execute(apiManager.registerGCMIDToServer());
    }

    public void completeRegistration (boolean status) {
        if (status) {
            Toast.makeText(preludeActivity.getApplicationContext(), "Successfully registered to server", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(preludeActivity.getApplicationContext(), "Failed to register to server", Toast.LENGTH_LONG).show();
        }
        preludeActivity.navigateToHome();
    }
}
