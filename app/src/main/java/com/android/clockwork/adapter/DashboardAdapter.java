package com.android.clockwork.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.JobActionPresenter;
import com.android.clockwork.view.activity.ViewJobActivity;


import java.util.ArrayList;

/**
 * Created by jiabao.tan.2012 on 2/9/2015.
 */
public class DashboardAdapter extends BaseAdapter {
    public final static String PAR_KEY = "DASHBOARD";
    private Activity activity;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private static LayoutInflater inflater = null;
    ProgressDialog dialog;
    Post p;
    int arrayPosition;
    View view;
    JobActionPresenter jobActionPresenter;

    public DashboardAdapter(Activity activity, ArrayList<Post> arrayList) {
        this.activity = activity;
        this.postList = arrayList;
        this.dialog = new ProgressDialog(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        jobActionPresenter = new JobActionPresenter(activity, dialog);

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            view = inflater.inflate(R.layout.dashboard_listing_row, null);
        }

        p = postList.get(position);
        arrayPosition = position;

        TextView jobTitle = (TextView) view.findViewById(R.id.jobTitle);
        TextView hiringCo = (TextView) view.findViewById(R.id.hiringCo);
        TextView status = (TextView) view.findViewById(R.id.status);
        TextView salary = (TextView) view.findViewById(R.id.salary);
        ImageButton popup = (ImageButton) view.findViewById(R.id.popup);

        popup.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu(v);
            }
        }));
        popup.setFocusable(false);

        // set text
        jobTitle.setText(p.getHeader());
        hiringCo.setText(p.getCompany());
        String appStatusUpper = p.getStatus().substring(0, 1).toUpperCase();
        String fullStatus = appStatusUpper + p.getStatus().substring(1);

        status.setText("Application Status: " + fullStatus);

        if (p.getSalary() % 1 == 0) {
            salary.setText("$" + (int) p.getSalary());
        } else {
            salary.setText("$" + p.getSalary());
        }

        return view;
    }

    public void viewMenu(View v) {
        view = v;
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        Menu m = popup.getMenu();
        inflater.inflate(R.menu.menu_dashboard_item, m);
        if (p.getStatus().equalsIgnoreCase("pending")) {
            Log.d("Status", p.getStatus());
            m.removeItem(R.id.accept);
        } else if (p.getStatus().equalsIgnoreCase("offered")) {
            Log.d("Status", p.getStatus());
            m.removeItem(R.id.withdraw);
        } else if (p.getStatus().equalsIgnoreCase("hired")) {
            m.removeItem(R.id.withdraw);
            m.removeItem(R.id.accept);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view:
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(PAR_KEY, p);

                        Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                        viewJobActivity.putExtra("Activity", "dashboard");
                        viewJobActivity.putExtras(bundle);
                        view.getContext().startActivity(viewJobActivity);
                        return true;
                    case R.id.withdraw:
                        jobActionPresenter.withdrawJobApplication(p.getId());
                        postList.remove(arrayPosition);
                        notifyDataSetChanged();
                        return true;
                    case R.id.accept:
                        jobActionPresenter.acceptJobOffer(p.getId());
                        notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }


    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
