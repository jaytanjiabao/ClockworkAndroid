package com.android.clockwork.view.tab;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.clockwork.R;
import com.android.clockwork.adapter.ListingAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.JobListingPresenter;
import com.android.clockwork.view.JobListingView;

import java.util.ArrayList;

public class JobListingFragment extends Fragment implements JobListingView {
    JobListingPresenter jobListingPresenter;
    ListView listView;
    ListingAdapter listingAdapter;
    ArrayList<Post> postList;
    ProgressDialog dialog;
    View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        showProgress();
        fragmentView = inflater.inflate(R.layout.tab_fragment_1, container, false);
        listView = (ListView) fragmentView.findViewById(R.id.list);
        postList = new ArrayList<Post>();

        Log.d("Fragment", "Creating Presenter");
        jobListingPresenter = new JobListingPresenter(this, postList, getActivity(), dialog);
        jobListingPresenter.getAllJobListings();

        return fragmentView;
    }

    @Override
    public void displayJobListing() {
        listingAdapter = jobListingPresenter.getListingAdapter();
        listView.setAdapter(listingAdapter);
    }

    @Override public void showProgress() {
        dialog = new ProgressDialog(getActivity());
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void onNoListingError() {
        //username.setError(getString(R.string.username_error));
    }
}