package com.android.clockwork.presenter;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.clockwork.adapter.ListingAdapter;
import com.android.clockwork.model.APIManager;
import com.android.clockwork.model.JobListingManager;
import com.android.clockwork.model.Post;
import com.android.clockwork.view.JobListingView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
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
    //ProgressDialog dialog;
    ProgressBar progressBar;
    APIManager apiManager;

    public JobListingPresenter(JobListingView jobListingView, ArrayList<Post> postList, FragmentActivity fragmentActivity, ProgressBar progressBar) {
        this.jobListingView = jobListingView;
        this.postList = postList;
        //this.dialog = dialog;
        this.progressBar = progressBar;
        this.jobListingManager = new JobListingManager(this, progressBar);
        this.fragmentActivity = fragmentActivity;
        apiManager = new APIManager();

    }

    public void getAllJobListings() {
        Log.d("Presenter", "Executing API call..");
        jobListingManager.execute(apiManager.jobListing());
    }

    public void searchJobListing(String searchTerm) {
        try {
            Log.d("Presenter", "Executing API call..");
            String requestURL = apiManager.searchJob();
            requestURL += URLEncoder.encode(searchTerm, "UTF-8");
            jobListingManager.execute(requestURL);
        } catch (IOException ioException) {
            // error handling, unlikely to happen
        }
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
        jobListingView.displayJobListing(this);
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
