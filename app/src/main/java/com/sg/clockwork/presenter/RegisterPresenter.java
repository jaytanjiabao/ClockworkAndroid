package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.GCMServerManager;
import com.sg.clockwork.model.RegisterManager;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.view.activity.RegisterActivity;

/**
 * Created by Hoi Chuen on 29/8/2015.
 */
public class RegisterPresenter implements RegisterListener {

    private RegisterActivity registerActivity;
    private RegisterManager registerManager;
    private GCMPresenter gcmPresenter;
    Context currentContext;
    APIManager apiManager;
    ProgressBar progressBar;
    TextView statusText;
    GCMServerManager gcmServerManager;
    String id;

    public RegisterPresenter(RegisterActivity registerActivity, ProgressBar progressBar, TextView statusText){
        this.registerActivity = registerActivity;
        this.currentContext = registerActivity.getApplicationContext();
        apiManager = new APIManager();
        this.progressBar = progressBar;
        this.statusText = statusText;
        this.gcmPresenter = new GCMPresenter(registerActivity, apiManager, this);
    }

    public void register(String nric, String email, String passWord, String name) {
        gcmPresenter.registerGCM();
        this.registerManager = new RegisterManager(currentContext,progressBar,statusText);
        registerManager.execute(apiManager.fbLogin_Register());
        registerManager.register(nric, email, passWord, name, this, id);
    }

    public void getRegId(String id) {
        this.id = id;
    }

    public void onSuccess(String email, String authToken) {
        this.gcmServerManager = new GCMServerManager(currentContext, this, registerActivity);
        SessionManager sessionManager = new SessionManager(currentContext);
        gcmServerManager.prepareId(id, email, authToken);
        gcmServerManager.execute(apiManager.registerGCMIDToServer());
    }

    public void completeRegistration(boolean status) {
        if (status) {
            Toast.makeText(currentContext, "Successfully registered to server", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(currentContext, "Failed to register to server", Toast.LENGTH_LONG).show();
        }
        navigateBackHome();
    }

    public void navigateBackHome() {
        registerActivity.navigateToHome();
    }
}
