package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.clockwork.model.ApplyJobManager;
import com.android.clockwork.model.SessionManager;

import java.util.HashMap;

/**
 * Created by jiabao.tan.2012 on 29/8/2015.
 */
public class ApplyJobPresenter implements ApplyJobListener {
    FragmentActivity fragmentActivity;
    ProgressDialog dialog;
    ApplyJobManager applyJobManager;
    Context currentContext;
    SessionManager sessionManager;
    String email, authToken;

    public ApplyJobPresenter(FragmentActivity fragmentActivity, ProgressDialog dialog) {
        this.fragmentActivity = fragmentActivity;
        this.dialog = dialog;
        this.applyJobManager = new ApplyJobManager(this, this.dialog);
        this.currentContext = fragmentActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
    }

    public void applyJob(int id) {
        // set details
        setAuthenticationDetails();
        applyJobManager.prepareAuthentication(email, authToken, id);
        applyJobManager.execute("https://clockwork-api.herokuapp.com/api/v1/users/apply");
    }

    public void setAuthenticationDetails() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        email =  user.get(SessionManager.KEY_EMAIL);
        authToken = user.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
    }

    @Override
    public void onSuccess(String string) {
        Toast.makeText(fragmentActivity.getBaseContext(), "Successfully applied for job!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String string) {

    }

    public HashMap<String,String> getSessionInfo () {
        HashMap<String, String> user = sessionManager.getUserDetails();
        return user;
    }
}
