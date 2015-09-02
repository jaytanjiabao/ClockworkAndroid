package com.android.clockwork.view.tab;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.clockwork.R;
import com.android.clockwork.adapter.DashboardAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.DashboardPresenter;
import com.android.clockwork.view.DashboardView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import java.util.ArrayList;

public class DashboardFragment extends Fragment implements DashboardView {
    View fragmentView;
    ArrayList<Post> appliedList;
    SwipeMenuListView listView;
    DashboardPresenter dashboardPresenter;
    ProgressDialog dialog;
    DashboardAdapter dashboardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        listView = (SwipeMenuListView) fragmentView.findViewById(R.id.listView);
        appliedList = new ArrayList<Post>();

        dashboardPresenter = new DashboardPresenter(this, appliedList, getActivity(), dialog);
        dashboardPresenter.getAppliedJobList();

        //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView adptView, View view, int position, long arg3) {
                //Bundle bundle = new Bundle();
                //listingAdapter = (ListingAdapter) listView.getAdapter();
                //bundle.putParcelable(PAR_KEY, (Post) listingAdapter.getItem(position));

                //Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                //viewJobActivity.putExtras(bundle);
                //startActivity(viewJobActivity);
            //}
        //});

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        return fragmentView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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