package com.android.clockwork.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.JobActionPresenter;
import com.android.clockwork.view.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by jiabao.tan.2012 on 2/9/2015.
 */
public class DashboardAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private static LayoutInflater inflater = null;
    ProgressDialog dialog;
    Post p;
    int arrayPosition;

    public DashboardAdapter(Activity activity, ArrayList<Post> arrayList) {
        this.activity = activity;
        this.postList = arrayList;
        this.dialog = new ProgressDialog(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

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
        TextView salary = (TextView) view.findViewById(R.id.salary);
        TextView actionButton = (TextView) view.findViewById(R.id.actionButton);

        // set text
        jobTitle.setText(p.getHeader());
        hiringCo.setText(p.getStatus());
        salary.setText("$" + p.getSalary());

        if (p.getStatus().equalsIgnoreCase("pending")) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setBackgroundColor(Color.parseColor("#4183D7"));
            actionButton.setText("Withdraw Job Application: " + p.getId());
        } else if (p.getStatus().equalsIgnoreCase("offered")) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setBackgroundColor(Color.parseColor("#87D37C"));
            actionButton.setText("Accept Job Offer: " + p.getId());
        } else if (p.getStatus().equalsIgnoreCase("hired")) {
            actionButton.setVisibility(View.INVISIBLE);
        } else if (p.getStatus().equalsIgnoreCase("completed")) {
            // completed
        } else {
            // all else
        }

        return view;
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
