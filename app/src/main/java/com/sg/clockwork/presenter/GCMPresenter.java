package com.sg.clockwork.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.GCMManager;
import com.sg.clockwork.model.GCMServerManager;

/**
 * Created by jiabao.tan.2012 on 21/10/2015.
 */
public class GCMPresenter {
    String regId;
    GoogleCloudMessaging gcm;
    Activity activity;
    APIManager apiManager;
    GCMManager gcmManager;
    GCMServerManager gcmServerManager;
    RegisterPresenter registerPresenter;

    public GCMPresenter(Activity activity, APIManager apiManager, RegisterPresenter registerPresenter) {
        this.activity = activity;
        this.apiManager = apiManager;
        gcm = GoogleCloudMessaging.getInstance(activity);
        this.registerPresenter = registerPresenter;
    }

    public void registerGCM() {
        this.gcmManager = new GCMManager(gcm, activity.getApplicationContext(), this);
        gcmManager.execute();
    }

    public void onSuccess(String regId) {
        //Toast.makeText(activity.getApplicationContext(), "REG ID: " + regId, Toast.LENGTH_LONG).show();
        registerPresenter.getRegId(regId);
    }

//    pull from shared preferences
//    private String getRegistrationId(Context context) {
//        final SharedPreferences prefs = getSharedPreferences(
//                MyActivity.class.getSimpleName(), Context.MODE_PRIVATE);
//        String registrationId = prefs.getString(REG_ID, "");
//        if (registrationId.isEmpty()) {
//            Log.i(TAG, "Registration not found.");
//            return "";
//        }
//        return registrationId;
//    }
}
