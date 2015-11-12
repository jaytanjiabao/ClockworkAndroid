package com.sg.clockwork.view.tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.sg.clockwork.adapter.ListingAdapter;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.presenter.JobListingPresenter;
import com.sg.clockwork.view.JobListingView;
import com.sg.clockwork.view.activity.MainActivity;
import com.sg.clockwork.view.activity.ViewJobActivity;

import java.util.ArrayList;

public class JobListingFragment extends Fragment implements JobListingView, SwipeRefreshLayout.OnRefreshListener {
    public final static String PAR_KEY = "LISTING";
    JobListingPresenter jobListingPresenter;
    ListView listView;
    ListingAdapter listingAdapter;
    ArrayList<Post> postList;
    ProgressDialog dialog;
    View fragmentView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //showProgress();
        fragmentView = inflater.inflate(com.sg.clockwork.R.layout.tab_fragment_1, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(com.sg.clockwork.R.id.swipeRefresh);
        listView = (ListView) fragmentView.findViewById(com.sg.clockwork.R.id.list);
        progressBar = (ProgressBar) fragmentView.findViewById(com.sg.clockwork.R.id.progressBar2);
        postList = new ArrayList<Post>();
        String search = MainActivity.searchTerm;

        jobListingPresenter = new JobListingPresenter(this, postList, getActivity(),progressBar);
        if (search.equals("")) {
            jobListingPresenter.getAllJobListings(MainActivity.sortBy);
        } else {
            jobListingPresenter.searchJobListing(search);
            MainActivity.searchTerm = "";
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adptView, View view, int position, long arg3) {
                Bundle bundle = new Bundle();
                listingAdapter = (ListingAdapter) listView.getAdapter();
                bundle.putParcelable(PAR_KEY, (Post) listingAdapter.getItem(position));

                Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                viewJobActivity.putExtra("Activity", "jobListing");
                viewJobActivity.putExtras(bundle);
                startActivity(viewJobActivity);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //swipeRefreshLayout.setRefreshing(true);
                                    }
                                }
        );

        return fragmentView;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        JobListingPresenter presenter = new JobListingPresenter(this, postList, getActivity(),progressBar);
        presenter.getAllJobListings(MainActivity.sortBy);
    }

    @Override
    public void displayJobListing(JobListingPresenter presenter) {
        listingAdapter = presenter.getListingAdapter();
        listView.setAdapter(listingAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

/*
    @Override public void showProgress() {
        dialog = new ProgressDialog(getActivity());
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }
*/

    @Override
    public void onNoListingError() {
        //username.setError(getString(R.string.username_error));
    }
}