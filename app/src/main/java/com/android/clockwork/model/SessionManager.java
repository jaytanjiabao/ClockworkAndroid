package com.android.clockwork.model;

import android.content.Context;

import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class SessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "SessionUser";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Session name (make variable public to access from outside)
    public static final String KEY_NAME = "userName";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_ACCOUNTYPE = "accountType";

    public static final String KEY_AUTHENTICATIONTOKEN = "authenticationToken" ;

    public static final String KEY_PASSWORD = "passWord" ;

    public static final String KEY_ID = "id" ;

    public static final String KEY_AVATAR = "avatarPath";

    public static final String KEY_ADDRESS = "address";

    public static final String KEY_CONTACT = "contact";

    public static final String KEY_DOB = "dob";

    public static final String KEY_NATIONALITY = "nationality";

    public static String status = "";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(int id, String userName, String email, String accountType,String authenticationToken, String avatarPath, String address, String contact,String dob, String nationality){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        //Storing ID in pref
        editor.putInt(KEY_ID, id);

        editor.putString(KEY_DOB, dob);

        editor.putString(KEY_NATIONALITY, nationality);

        //Storing accountType in pref
        editor.putString(KEY_ACCOUNTYPE, accountType);

        //Storing passWord in pref
        //editor.putString(KEY_PASSWORD, passWord);

        //Storing authenticationToken in pref
        editor.putString(KEY_AUTHENTICATIONTOKEN, authenticationToken);

        // Storing name in pref
        editor.putString(KEY_NAME, userName);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_AVATAR, avatarPath);

        editor.putString(KEY_ADDRESS, address);

        editor.putString(KEY_CONTACT, contact);

        // commit changes
        editor.commit();
    }


    public void updateSession(int id, String userName, String email, String accountType,String authenticationToken, String avatarPath, String address, String contact, String dob, String nationality){

        //Storing ID in pref
        editor.putInt(KEY_ID, id);

        //Storing accountType in pref
        editor.putString(KEY_ACCOUNTYPE, accountType);

        //Storing passWord in pref
        //editor.putString(KEY_PASSWORD, passWord);

        //Storing authenticationToken in pref
        editor.putString(KEY_AUTHENTICATIONTOKEN, authenticationToken);

        // Storing name in pref
        editor.putString(KEY_NAME, userName);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_AVATAR, avatarPath);

        editor.putString(KEY_ADDRESS, address);

        editor.putString(KEY_CONTACT, contact);

        editor.putString(KEY_DOB, dob);

        editor.putString(KEY_NATIONALITY, nationality);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkNotLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            return true;
        }
        return false;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_DOB, pref.getString(KEY_DOB, null));

        user.put(KEY_NATIONALITY, pref.getString(KEY_NATIONALITY, null));

        user.put(KEY_ACCOUNTYPE, pref.getString(KEY_ACCOUNTYPE, null));

        user.put(KEY_AUTHENTICATIONTOKEN, pref.getString(KEY_AUTHENTICATIONTOKEN, null));

        user.put(KEY_AVATAR, pref.getString(KEY_AVATAR, null));

        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));

        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));

        //user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    public HashMap<String, Integer> getUserID(){

        //Use hashmap to store user credentials
        HashMap<String, Integer> user = new HashMap<String, Integer>();

        // user name
        user.put(KEY_ID, pref.getInt(KEY_ID, 0));

        // return user
        return user;
    }

    public String getSessionStatus () {
        return status;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    // Check for login
    public boolean isUserLoggedIn(){

        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
