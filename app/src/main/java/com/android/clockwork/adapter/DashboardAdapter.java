package com.android.clockwork.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public DashboardAdapter(Activity activity, ArrayList<Post> arrayList) {
        this.activity = activity;
        this.postList = arrayList;
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

        Post p = postList.get(position);

        TextView jobTitle = (TextView) view.findViewById(R.id.jobTitle);
        TextView hiringCo = (TextView) view.findViewById(R.id.hiringCo);
        TextView salary = (TextView) view.findViewById(R.id.salary);

        // set text
        jobTitle.setText(p.getHeader());
        hiringCo.setText(p.getCompany());
        salary.setText("$" + p.getSalary());

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
