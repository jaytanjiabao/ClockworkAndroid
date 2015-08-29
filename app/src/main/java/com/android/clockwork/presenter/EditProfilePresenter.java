package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.clockwork.adapter.ListingAdapter;
import com.android.clockwork.model.EditProfileManager;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jiabao.tan.2012 on 26/8/2015.
 */
public class EditProfilePresenter implements EditProfileListener {
    FragmentActivity fragmentActivity;
    ProgressDialog dialog;
    EditProfileManager editProfileManager;
    Context currentContext;
    SessionManager sessionManager;

    public EditProfilePresenter(FragmentActivity fragmentActivity, ProgressDialog dialog) {
        this.fragmentActivity = fragmentActivity;
        this.dialog = dialog;
        this.editProfileManager = new EditProfileManager(this, this.dialog);
        this.currentContext = fragmentActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
    }

    public EditProfilePresenter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        this.currentContext = fragmentActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
    }

    public void updateProfile(String name, String address, String contact, String dob, String email, String authToken) {
        Log.d("Manager", "Before executing..");
        editProfileManager.setProfileDetails(name, address, contact, dob, email, authToken);
        editProfileManager.execute("https://clockwork-api.herokuapp.com/api/v1/users/update");
        Log.d("Manager", "After executing..");
    }

    public HashMap<String, String> getUserMap() {
        return sessionManager.getUserDetails();
    }

    @Override
    public void onSuccess(String result) {
        // success
        Toast.makeText(fragmentActivity.getBaseContext(), "Profile updated successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String s) {
        // error
    }
}
