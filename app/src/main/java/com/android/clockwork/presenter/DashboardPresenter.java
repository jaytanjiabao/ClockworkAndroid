package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.util.Log;

import com.android.clockwork.adapter.DashboardAdapter;
import com.android.clockwork.model.AppliedJobsManager;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.view.DashboardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jiabao.tan.2012 on 2/9/2015.
 */
public class DashboardPresenter implements DashboardListener {
    ArrayList<Post> appliedList;
    FragmentActivity fragmentActivity;
    ProgressDialog dialog;
    SessionManager sessionManager;
    Context currentContext;
    AppliedJobsManager appliedJobsManager;
    HashMap<String, String> usermap;
    DashboardAdapter dashboardAdapter;
    DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView, ArrayList<Post> appliedList, FragmentActivity fragmentActivity) {
        this.dashboardView = dashboardView;
        this.appliedList = appliedList;
        //this.dialog = dialog;
        this.appliedJobsManager = new AppliedJobsManager(this);
        this.fragmentActivity = fragmentActivity;
        this.currentContext = fragmentActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
    }

    public void getAppliedJobList() {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        appliedJobsManager.setCredentials(email, authToken);
        appliedJobsManager.execute("https://clockwork-api.herokuapp.com/api/v1/users/get_applied_jobs");
    }

    @Override
    public void onNoListingError() {

    }

    public ArrayList<Post> createGsonFromString(String string) {
        Log.d("Dashboard Listing", string);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
        appliedList = gson.fromJson(string, listType);
        return appliedList;
    }

    @Override
    public void onSuccess(String result) {
        this.appliedList = createGsonFromString(result);
        setListingAdapter(new DashboardAdapter(fragmentActivity, appliedList));
        dashboardView.displayAppliedJobListing(this);
    }

    public ArrayList<Post> appliedJobList() {
        return appliedList;
    }

    public void setListingAdapter(DashboardAdapter dashboardAdapter) {
        this.dashboardAdapter = dashboardAdapter;
    }

    public DashboardAdapter getDashboardAdapter() {
        return dashboardAdapter;
    }
}
