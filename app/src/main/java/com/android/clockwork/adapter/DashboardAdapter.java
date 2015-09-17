package com.android.clockwork.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;


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
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard_item, popup.getMenu());
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
