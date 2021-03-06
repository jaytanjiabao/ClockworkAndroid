package com.sg.clockwork.presenter;


import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.widget.ProgressBar;

import com.sg.clockwork.adapter.DashboardAdapter;
import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.AppliedJobsManager;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.view.DashboardView;
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
    SessionManager sessionManager;
    Context currentContext;
    AppliedJobsManager appliedJobsManager;
    HashMap<String, String> usermap;
    DashboardAdapter dashboardAdapter;
    DashboardView dashboardView;
    ProgressBar progressBar;
    APIManager apiManager;

    public DashboardPresenter(DashboardView dashboardView, ArrayList<Post> appliedList, FragmentActivity fragmentActivity, ProgressBar progressBar) {
        this.dashboardView = dashboardView;
        this.appliedList = appliedList;
        this.progressBar = progressBar;
        this.appliedJobsManager = new AppliedJobsManager(this,progressBar);
        this.fragmentActivity = fragmentActivity;
        this.currentContext = fragmentActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        apiManager = new APIManager();
    }

    public void getAppliedJobList() {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        appliedJobsManager.setCredentials(email, authToken);
        appliedJobsManager.execute(apiManager.dashboard());
    }

    @Override
    public void onNoListingError() {

    }

    public ArrayList<Post> createGsonFromString(String string) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
        appliedList = gson.fromJson(string, listType);
        return appliedList;
    }

    @Override
    public void onSuccess(String result) {
        this.appliedList = createGsonFromString(result);
        ArrayList<Post> withoutCompletedJob = new ArrayList<Post>();
        for (Post p: appliedList) {
            if (!p.getStatus().equalsIgnoreCase("completed")) {
                withoutCompletedJob.add(p);
            }
        }
        this.appliedList = withoutCompletedJob;
        setListingAdapter(new DashboardAdapter(fragmentActivity, withoutCompletedJob));
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
