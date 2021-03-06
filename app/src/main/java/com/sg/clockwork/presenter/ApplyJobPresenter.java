package com.sg.clockwork.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.ApplyJobManager;
import com.sg.clockwork.model.SessionManager;

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
    APIManager apiManager;

    public ApplyJobPresenter(FragmentActivity fragmentActivity, ProgressDialog dialog) {
        this.fragmentActivity = fragmentActivity;
        this.dialog = dialog;
        this.applyJobManager = new ApplyJobManager(this, this.dialog);
        this.currentContext = fragmentActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        apiManager = new APIManager();
    }

    public void applyJob(int id) {
        // set details
        setAuthenticationDetails();
        applyJobManager.prepareAuthentication(email, authToken, id);
        applyJobManager.execute(apiManager.applyJob());
    }

    public void setAuthenticationDetails() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        email =  user.get(SessionManager.KEY_EMAIL);
        authToken = user.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
    }

    @Override
    public void onSuccess(String string) {
        if (string.substring(0, 3).equalsIgnoreCase("bad")) {
            Toast.makeText(fragmentActivity.getBaseContext(), string.substring(string.indexOf("-") + 2), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(fragmentActivity.getBaseContext(), "You have successfully applied for the job!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onError(String string) {

    }

    public HashMap<String,String> getSessionInfo () {
        HashMap<String, String> user = sessionManager.getUserDetails();
        return user;
    }
}
