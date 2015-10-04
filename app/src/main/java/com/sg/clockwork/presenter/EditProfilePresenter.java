package com.sg.clockwork.presenter;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.EditProfileManager;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.view.tab.ProfileFragment;

import java.io.File;
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
    ProfileFragment profileFragment;
    APIManager apiManager;

    public EditProfilePresenter(FragmentActivity fragmentActivity, ProgressDialog dialog) {
        this.fragmentActivity = fragmentActivity;
        this.dialog = dialog;
        this.currentContext = fragmentActivity.getApplicationContext();
        this.editProfileManager = new EditProfileManager(this, this.dialog,currentContext);
        this.sessionManager = new SessionManager(currentContext);
        apiManager = new APIManager();
    }

    public EditProfilePresenter(ProfileFragment profileFragment,ProgressDialog dialog) {
        this.profileFragment = profileFragment;
        this.dialog = dialog;
        this.currentContext = profileFragment.getActivity().getApplicationContext();
        this.editProfileManager = new EditProfileManager(this, this.dialog,currentContext);
        this.sessionManager = new SessionManager(currentContext);
        apiManager = new APIManager();
    }

    public void updateProfile(String name, String address, String contact, String email, String authToken) {
        Log.d("Manager", "Before executing..");
        editProfileManager.setProfileDetails(name, address, contact, email, authToken);
        editProfileManager.execute(apiManager.editProfile());
        Log.d("Manager", "After executing..");
    }

    public void changePassword(String oldPassword, String newPassword, String retypedPassword, String email, String authToken) {
        Log.d("Manager", "Before executing..");
        editProfileManager.preparePasswordChange(true);
        editProfileManager.setPasswordDetails(oldPassword, newPassword, retypedPassword, email, authToken);
        editProfileManager.execute(apiManager.editProfile());
        Log.d("Manager", "After executing..");
    }

    public void changeProfilePicture(String email, File file, String authToken) {
        Log.d("Manager", "Before executing..");
        editProfileManager.prepareProfilePicChange(true);
        editProfileManager.setProfilePictureDetails(email, file, authToken);
        editProfileManager.execute(apiManager.editProfile());
        Log.d("Manager", "After executing..");
    }

    public HashMap<String, String> getUserMap() {
        return sessionManager.getUserDetails();
    }

    public void updateSession(int id, String userName, String email, String accountType,String authenticationToken, String avatarPath, String address, String contact, String dob, String nationality) {
        sessionManager.updateSession(id,userName,email,accountType,authenticationToken,avatarPath,address,contact,dob,nationality);
    }

    public HashMap<String, Integer> getUserID () {
        return sessionManager.getUserID();
    }

    @Override
    public void onSuccess(String result, boolean changePassword, boolean changeProfilePicture) {
        // success
        // update stored session
        if (changePassword) {
            Toast.makeText(fragmentActivity.getBaseContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
        } else if(changeProfilePicture) {
            Toast.makeText(currentContext, "Profile Picture changed successfully", Toast.LENGTH_LONG).show();
            profileFragment.navigateToHome();
        }
        else{
            Toast.makeText(fragmentActivity.getBaseContext(), "Profile updated successfully", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onError(String s) {
        // error
    }
}
