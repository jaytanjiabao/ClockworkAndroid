package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.FBLoginManager;
import com.sg.clockwork.model.GCMServerManager;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 28/8/2015.
 */
public class FBLoginPresenter implements FBLoginListener {
    private PreludeActivity preludeActivity;
    private FBLoginManager fbLoginManager;
    Context currentContext;
    ProgressBar progressBar;
    TextView statusText;
    APIManager apiManager;
    String id;

    private GCMPresenter gcmPresenter;
    GCMServerManager gcmServerManager;

    public FBLoginPresenter(PreludeActivity preludeActivity, ProgressBar progressBar, TextView statusText){
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
        this.progressBar = progressBar;
        this.statusText = statusText;
        apiManager = new APIManager();
        this.gcmPresenter = new GCMPresenter(preludeActivity, apiManager, this);
    }

    public void fbLogin(String email, String fb_Id, String avatar_Path, String account_Type, String userName){
        gcmPresenter.registerGCM();
        this.fbLoginManager = new FBLoginManager(currentContext,progressBar,statusText);
        fbLoginManager.execute(apiManager.fbLogin_Register());
        fbLoginManager.fbLogin(email, fb_Id, avatar_Path, account_Type, userName, this);
    }

    public void getRegId(String id) {
        this.id = id;
    }

    public void onSuccess(String email, String authToken) {
        this.gcmServerManager = new GCMServerManager(currentContext, this, preludeActivity);
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
        navigateToHome();
    }

    public void navigateToHome () {
        preludeActivity.navigateToHome();
    }

}
