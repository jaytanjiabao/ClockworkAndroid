package com.android.clockwork.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;

import java.util.ArrayList;

/**
 * Created by Hoi Chuen on 10/9/2015.
 */
public class CompletedJobAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private static LayoutInflater inflater = null;
    ProgressDialog dialog;
    Post p;
    int arrayPosition;

    public CompletedJobAdapter(Activity activity, ArrayList<Post> arrayList) {
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
            view = inflater.inflate(R.layout.rating_listing_row, null);
        }

        p = postList.get(position);
        arrayPosition = position;

        TextView jobTitle = (TextView) view.findViewById(R.id.jobTitle);
        TextView hiringCo = (TextView) view.findViewById(R.id.hiringCo);
        TextView comments = (TextView) view.findViewById(R.id.comment);
        ImageView ratings = (ImageView) view.findViewById(R.id.imageView2);

        // set text
        jobTitle.setText(p.getHeader());
        hiringCo.setText(p.getCompany());
        comments.setText(p.getComments());
        if(p.getRating()==-1){
            ratings.setImageResource(R.drawable.bad);
        }else if(p.getRating() == 0) {
            ratings.setImageResource(R.drawable.neutral);
        }else {
            ratings.setImageResource(R.drawable.good);
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
