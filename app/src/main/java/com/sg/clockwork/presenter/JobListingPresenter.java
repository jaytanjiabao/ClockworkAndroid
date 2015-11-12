package com.sg.clockwork.presenter;

import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;

import com.sg.clockwork.adapter.ListingAdapter;
import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.JobListingManager;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.view.JobListingView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jiabao.tan.2012 on 18/8/2015.
 */
public class JobListingPresenter implements JobListingListener {
    JobListingView jobListingView;
    JobListingManager jobListingManager;
    ArrayList<Post> postList;
    ListingAdapter listingAdapter;
    FragmentActivity fragmentActivity;
    String sortBy = "";

    ProgressBar progressBar;
    APIManager apiManager;

    public JobListingPresenter(JobListingView jobListingView, ArrayList<Post> postList, FragmentActivity fragmentActivity, ProgressBar progressBar) {
        this.jobListingView = jobListingView;
        this.postList = postList;
        this.progressBar = progressBar;
        this.jobListingManager = new JobListingManager(this, progressBar);
        this.fragmentActivity = fragmentActivity;
        this.apiManager = new APIManager();

    }

    public void getAllJobListings(String sortBy) {
        this.sortBy = sortBy;
        jobListingManager.execute(apiManager.jobListing());
    }

    public void searchJobListing(String searchTerm) {
        try {
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
        ArrayList<Post> postList = createGsonFromString(result);

        if (sortBy.equalsIgnoreCase("salary")) {
            Collections.sort(postList, Post.salaryComparator);
        } else if (sortBy.equalsIgnoreCase("start")) {
            Collections.sort(postList, Post.oldestComparator);
        } else if (sortBy.equalsIgnoreCase("recent")) {
            Collections.sort(postList, Post.latestComparator);
        }

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
