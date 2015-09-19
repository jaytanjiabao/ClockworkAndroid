package com.android.clockwork.view.tab;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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

public class DashboardFragment extends Fragment implements DashboardView {
    public final static String PAR_KEY = "DASHBOARD";
    View fragmentView;
    ArrayList<Post> appliedList;
    ListView listView;
    DashboardPresenter dashboardPresenter;
    JobActionPresenter jobActionPresenter;
    ProgressDialog dialog;
    DashboardAdapter dashboardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        listView = (ListView) fragmentView.findViewById(R.id.listView);
        appliedList = new ArrayList<Post>();

        dialog = new ProgressDialog(getActivity());
        dashboardPresenter = new DashboardPresenter(this, appliedList, getActivity(), dialog);
        dashboardPresenter.getAppliedJobList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adptView, View view, int position, long arg3) {
                Bundle bundle = new Bundle();
                dashboardAdapter = (DashboardAdapter) listView.getAdapter();
                Post p = (Post) dashboardAdapter.getItem(position);
                bundle.putParcelable(PAR_KEY, (Post) dashboardAdapter.getItem(position));

                Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                viewJobActivity.putExtra("Activity", "dashboard");
                viewJobActivity.putExtras(bundle);
                startActivity(viewJobActivity);
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
        appliedList = dashboardPresenter.appliedJobList();
        listView.setAdapter(dashboardAdapter);
    }
}