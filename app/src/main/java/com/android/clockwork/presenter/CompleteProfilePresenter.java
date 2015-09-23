package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.android.clockwork.model.APIManager;
import com.android.clockwork.model.CompleteProfileManager;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.view.activity.CompleteProfileActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * Created by Hoi Chuen on 12/9/2015.
 */
public class CompleteProfilePresenter implements CompleteProfileListener {

    ProgressDialog dialog;
    Context currentContext;
    SessionManager sessionManager;
    HashMap<String, String> user;
    CompleteProfileManager completeProfileManager;
    ApplyJobPresenter applyJobPresenter;
    CompleteProfileActivity completeProfileActivity;
    TextView statusText;
    APIManager apiManager;

    public CompleteProfilePresenter(FragmentActivity fragmentActivity, ProgressDialog dialog, TextView statusText) {
        this.dialog = dialog;
        this.statusText = statusText;
        completeProfileActivity = (CompleteProfileActivity) fragmentActivity;
        currentContext = fragmentActivity.getApplicationContext();
        sessionManager = new SessionManager(currentContext);
        applyJobPresenter = new ApplyJobPresenter(fragmentActivity,dialog);
        completeProfileManager = new CompleteProfileManager(this,dialog,currentContext,statusText);
        apiManager = new APIManager();
    }


    public void completeProfile(int postID, String nationality,String gender, String mobileNo, String dob) {

        user = sessionManager.getUserDetails();
        String email = user.get(SessionManager.KEY_EMAIL);
        String authToken = user.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        completeProfileManager.completeProfile(postID,email,nationality,gender,mobileNo,dob,authToken);
        completeProfileManager.execute(apiManager.completeProfile());
    }

    @Override
    public void onSuccess(String result,int postID) {
        Gson gson = new Gson();
        Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
        HashMap userHash = gson.fromJson(result, hashType);
        Double idDouble = (Double)userHash.get("id");
        int id = idDouble.intValue();
        String username = (String)userHash.get("username");
        String email = (String)userHash.get("email");
        String accountType = (String)userHash.get("account_type");
        String authenticationToken = (String)userHash.get("authentication_token");
        String avatar_path = (String) userHash.get("avatar_path");
        String address = (String) userHash.get("address");
        Double contactNo = (Double) userHash.get("contact_number");
        NumberFormat nm = NumberFormat.getNumberInstance();
        String contact = "";
        if (contactNo != null) {
            contact = nm.format(contactNo);
            contact = contact.replace(",", "");
        }
        String dob = (String) userHash.get("date_of_birth");
        String nationality = (String) userHash.get("nationality");
        sessionManager.updateSession(id, username, email, accountType, authenticationToken, avatar_path, address, contact, dob, nationality);
        applyJobPresenter.applyJob(postID);
        completeProfileActivity.navigateToHome();
    }

}
