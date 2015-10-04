package com.android.clockwork.view.tab;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.clockwork.R;
import com.android.clockwork.adapter.DashboardAdapter;
import com.android.clockwork.adapter.ListingAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.DashboardPresenter;
import com.android.clockwork.presenter.JobActionPresenter;
import com.android.clockwork.view.DashboardView;

import com.android.clockwork.view.activity.ViewJobActivity;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import java.util.ArrayList;

public class DashboardFragment extends Fragment implements DashboardView, SwipeRefreshLayout.OnRefreshListener {
    public final static String PAR_KEY = "DASHBOARD";
    View fragmentView;
    ArrayList<Post> appliedList;
    ListView listView;
    DashboardPresenter dashboardPresenter;
    //ProgressDialog dialog;
    DashboardAdapter dashboardAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipeRefresh);
        listView = (ListView) fragmentView.findViewById(R.id.listView);
        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar3);
        appliedList = new ArrayList<Post>();

        //dialog = new ProgressDialog(getActivity());
        dashboardPresenter = new DashboardPresenter(this, appliedList, getActivity(),progressBar);
        dashboardPresenter.getAppliedJobList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adptView, View view, int position, long arg3) {
                Bundle bundle = new Bundle();
                dashboardAdapter = (DashboardAdapter) listView.getAdapter();
                Post p = (Post) dashboardAdapter.getItem(position);
                bundle.putParcelableArrayList("appliedList", appliedList);
                bundle.putParcelable(PAR_KEY, (Post) dashboardAdapter.getItem(position));

                Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                viewJobActivity.putExtra("Activity", "dashboard");
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
        DashboardPresenter presenter= new DashboardPresenter(this, appliedList, getActivity(),progressBar);
        presenter.getAppliedJobList();
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
    public void displayAppliedJobListing(DashboardPresenter presenter) {
        dashboardAdapter = presenter.getDashboardAdapter();
        appliedList = dashboardPresenter.appliedJobList();
        if (appliedList.size() != 0) {
            listView.setAdapter(dashboardAdapter);
        } else {
            listView.setBackground(getResources().getDrawable(R.drawable.no_job));
        }

        swipeRefreshLayout.setRefreshing(false);
    }
}