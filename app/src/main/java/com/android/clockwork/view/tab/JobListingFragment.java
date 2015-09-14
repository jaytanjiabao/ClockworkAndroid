package com.android.clockwork.view.tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.clockwork.R;
import com.android.clockwork.adapter.ListingAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.JobListingPresenter;
import com.android.clockwork.view.JobListingView;
import com.android.clockwork.view.activity.MainActivity;
import com.android.clockwork.view.activity.ViewJobActivity;

import java.util.ArrayList;

public class JobListingFragment extends Fragment implements JobListingView {
    public final static String PAR_KEY = "KEY";
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
        String search = MainActivity.searchTerm;

        Log.d("Fragment", "Creating Presenter");
        jobListingPresenter = new JobListingPresenter(this, postList, getActivity(), dialog);
        if (search.equals("")) {
            jobListingPresenter.getAllJobListings();
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
                viewJobActivity.putExtras(bundle);
                startActivity(viewJobActivity);
            }
        });

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