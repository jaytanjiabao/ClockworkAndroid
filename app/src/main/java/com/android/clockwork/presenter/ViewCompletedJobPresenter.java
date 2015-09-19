package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.clockwork.adapter.CompletedJobAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.model.ViewCompletedJobManager;
import com.android.clockwork.view.activity.ViewCompletedJobActivity;
import com.android.clockwork.view.tab.ProfileFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hoi Chuen on 10/9/2015.
 */
public class ViewCompletedJobPresenter implements ViewCompletedJobListener{
    ArrayList<Post> completedList;
    ViewCompletedJobActivity viewCompletedJobActivity;
    //ProgressDialog dialog;
    SessionManager sessionManager;
    Context currentContext;
    ViewCompletedJobManager viewCompletedJobManager;
    HashMap<String, String> usermap;
    CompletedJobAdapter completedJobAdapter;
    ProfileFragment fragmentActivity;
    boolean rating = false;


    public ViewCompletedJobPresenter(ArrayList<Post> completedList, ViewCompletedJobActivity viewCompletedJobActivity, boolean rating) {
        this.completedList = completedList;
        //this.dialog = dialog;
        this.viewCompletedJobManager = new ViewCompletedJobManager(this);
        this.viewCompletedJobActivity = viewCompletedJobActivity;
        currentContext = viewCompletedJobActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        this.rating = rating;
    }

    public ViewCompletedJobPresenter(ArrayList<Post> completedList, ProfileFragment viewCompletedJobActivity, boolean rating) {
        this.completedList = completedList;
        //this.dialog = dialog;
        this.viewCompletedJobManager = new ViewCompletedJobManager(this);
        this.fragmentActivity = viewCompletedJobActivity;
        currentContext = viewCompletedJobActivity.getActivity().getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        this.rating = rating;
    }

    public void getCompletedJobList() {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        viewCompletedJobManager.setCredentials(email, authToken);
        viewCompletedJobManager.execute("https://clockwork-api.herokuapp.com/api/v1/users/get_completed_jobs");
    }

    public ArrayList<Post> createGsonFromString(String string) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
        completedList = gson.fromJson(string, listType);
        return completedList;
    }

    @Override
    public void onSuccess(String result) {
        this.completedList = createGsonFromString(result);
        if(rating) {
            fragmentActivity.setRatingsCounter(completedList);
        }else {
            setListingAdapter(new CompletedJobAdapter(viewCompletedJobActivity, completedList));
            viewCompletedJobActivity.displayCompletedJobListing();
        }
    }

    public ArrayList<Post> completedJobList() {
        return completedList;
    }

    public void setListingAdapter(CompletedJobAdapter completedJobAdapter) {
        this.completedJobAdapter = completedJobAdapter;
    }

    public CompletedJobAdapter getCompletedJobAdapter() {
        return completedJobAdapter;
    }

}
