package com.android.clockwork.view.tab;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.adapter.DashboardAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.DashboardPresenter;
import com.android.clockwork.presenter.LogoutPresenter;
import com.android.clockwork.view.DashboardView;
import com.android.clockwork.view.activity.PreludeActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardFragment extends Fragment implements DashboardView {
    View fragmentView;
    ArrayList<Post> appliedList;
    ListView listView;
    DashboardPresenter dashboardPresenter;
    ProgressDialog dialog;
    DashboardAdapter dashboardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        listView = (ListView) fragmentView.findViewById(R.id.list);
        appliedList = new ArrayList<Post>();

        dashboardPresenter = new DashboardPresenter(this, appliedList, getActivity(), dialog);
        dashboardPresenter.getAppliedJobList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adptView, View view, int position, long arg3) {
                //Bundle bundle = new Bundle();
                //listingAdapter = (ListingAdapter) listView.getAdapter();
                //bundle.putParcelable(PAR_KEY, (Post) listingAdapter.getItem(position));

                //Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                //viewJobActivity.putExtras(bundle);
                //startActivity(viewJobActivity);
            }
        });

        return fragmentView;
    }

    @Override
    public void showProgress() {
        //
    }

    @Override
    public void hideProgress() {
        //
    }

    @Override
    public void displayAppliedJobListing() {
        dashboardAdapter = dashboardPresenter.getDashboardAdapter();
        listView.setAdapter(dashboardAdapter);
    }
}