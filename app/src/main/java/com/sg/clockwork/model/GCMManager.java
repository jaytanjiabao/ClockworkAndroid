package com.sg.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sg.clockwork.notification.Config;
import com.sg.clockwork.presenter.GCMPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jiabao.tan.2012 on 21/10/2015.
 */
public class GCMManager extends AsyncTask<String, Void, String> {

    Context currentContext;
    GoogleCloudMessaging gcm;
    GCMPresenter gcmPresenter;
    SessionManager sessionManager;
    String returnValue;

    public GCMManager (GoogleCloudMessaging gcm, Context currentContext, GCMPresenter gcmPresenter) {
        this.gcm = gcm;
        this.currentContext = currentContext;
        this.gcmPresenter = gcmPresenter;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... urls) {
        returnValue = "";
        try {
            returnValue = gcm.register(Config.GOOGLE_PROJECT_ID);
        } catch(IOException io) {
            returnValue = "Error :" + io.getMessage();
        }
        return returnValue;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        // store value and return to presenter
        sessionManager = new SessionManager(currentContext);
        sessionManager.updateGCMCredentials(result);
        gcmPresenter.onSuccess(result);
    }
}
