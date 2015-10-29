package com.sg.clockwork.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sg.clockwork.R;
import com.sg.clockwork.model.Rewards;

import java.util.ArrayList;

/**
 * Created by Hoi Chuen on 26/10/2015.
 */
public class ScoresAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Rewards> badgeList = new ArrayList<Rewards>();
    private static LayoutInflater inflater = null;
    Rewards p;
    int arrayPosition;

    public ScoresAdapter(Activity activity, ArrayList<Rewards> arrayList) {
        this.activity = activity;
        this.badgeList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            view = inflater.inflate(R.layout.scores_listing_row, null);
        }

        p = badgeList.get(position);
        arrayPosition = position;

        TextView quizTitle = (TextView) view.findViewById(R.id.quizTitle);
        TextView points = (TextView) view.findViewById(R.id.points);


        // set text
        quizTitle.setText(p.getType());
        points.setText(p.getScore());


        return view;
    }

    @Override
    public int getCount() {
        return badgeList.size();
    }

    @Override
    public Object getItem(int position) {
        return badgeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
