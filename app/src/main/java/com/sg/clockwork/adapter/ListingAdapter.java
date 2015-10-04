package com.sg.clockwork.adapter;

/**
 * Created by jiabao.tan.2012 on 2/8/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sg.clockwork.model.Post;
import com.sg.clockwork.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListingAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private static LayoutInflater inflater = null;

    public ListingAdapter(Activity activity, ArrayList<Post> arrayList) {
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
            view = inflater.inflate(R.layout.job_listing_row, null);
        }

        Post p = postList.get(position);

        TextView jobTitle = (TextView) view.findViewById(R.id.jobTitle);
        TextView hiringCo = (TextView) view.findViewById(R.id.hiringCo);
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView salary = (TextView) view.findViewById(R.id.salary);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView days = (TextView) view.findViewById(R.id.days);

        // set text
        jobTitle.setText(p.getHeader().toUpperCase());
        hiringCo.setText(p.getCompany().toUpperCase());

        SimpleDateFormat read = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat write = new SimpleDateFormat("d MMM yyyy");
        try {
            Date date = read.parse(p.getJobDate());
            startDate.setText(write.format(date));
        } catch (ParseException pe) {
            startDate.setText("" + p.getJobDate());
        }

        if (p.getDuration() > 1) {
            days.setText(p.getDuration() + " days");
        } else {
            days.setText(p.getDuration() + " day");
        }

        if (p.getSalary() % 1 == 0) {
            salary.setText("$" + (int) p.getSalary());
        } else {
            salary.setText("$" + p.getSalary());
        }

        location.setText(p.getLocation());

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
