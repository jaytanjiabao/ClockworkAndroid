package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.clockwork.adapter.ListingAdapter;
import com.android.clockwork.model.JobListingManager;
import com.android.clockwork.model.Post;
import com.android.clockwork.view.JobListingView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by jiabao.tan.2012 on 18/8/2015.
 */
public class JobListingPresenter implements JobListingListener {
    JobListingView jobListingView;
    JobListingManager jobListingManager;
    ArrayList<Post> postList;
    ListingAdapter listingAdapter;
    FragmentActivity fragmentActivity;
    ProgressDialog dialog;

    public JobListingPresenter(JobListingView jobListingView, ArrayList<Post> postList, FragmentActivity fragmentActivity, ProgressDialog dialog) {
        this.jobListingView = jobListingView;
        this.postList = postList;
        this.dialog = dialog;
        this.jobListingManager = new JobListingManager(this, this.dialog);
        this.fragmentActivity = fragmentActivity;
    }

    public void getAllJobListings() {
        Log.d("Presenter", "Executing API call..");
        jobListingManager.execute("https://clockwork-api.herokuapp.com/api/v1/posts/all.json");
    }

    public ArrayList<Post> createGsonFromString(String string) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
        postList = gson.fromJson(string, listType);
        return postList;
    }

    @Override
    public void onSuccess(String result) {
        Log.d("Presenter", result);
        ArrayList<Post> postList = createGsonFromString(result);
        setListingAdapter(new ListingAdapter(fragmentActivity, postList));
        jobListingView.displayJobListing();
    }

    @Override
    public void onNoListingError() {
        //jobListingView.navigateToHome();
    }

    public ListingAdapter getListingAdapter() {
        return listingAdapter;
    }

    public void setListingAdapter(ListingAdapter listingAdapter) {
        this.listingAdapter = listingAdapter;
    }
}
