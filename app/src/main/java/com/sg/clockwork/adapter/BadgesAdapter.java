package com.sg.clockwork.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sg.clockwork.R;
import com.sg.clockwork.model.Rewards;

import java.util.ArrayList;

/**
 * Created by Hoi Chuen on 25/10/2015.
 */
public class BadgesAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Rewards> badgeList = new ArrayList<Rewards>();
    private static LayoutInflater inflater = null;
    ProgressDialog dialog;
    Rewards p;
    int arrayPosition;
    Context context;

    public BadgesAdapter(Activity activity, ArrayList<Rewards> arrayList,Context context) {
        this.activity = activity;
        this.badgeList = arrayList;
        this.dialog = new ProgressDialog(activity);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            view = inflater.inflate(R.layout.badges_listing_row, null);
        }

        p = badgeList.get(position);
        arrayPosition = position;

        TextView badgeTitle = (TextView) view.findViewById(R.id.badgeTitle);
        TextView comments = (TextView) view.findViewById(R.id.comment);
        ImageView badge = (ImageView) view.findViewById(R.id.imageView2);

        // set text
        badgeTitle.setText(p.getName());
        comments.setText(p.getCriteria());
        String badge_id = p.getBadge_id();
        if(p.getStatus().equalsIgnoreCase("uncompleted")) {
            int resID = context.getResources().getIdentifier(badge_id, "drawable", context.getPackageName());
            badge.setImageResource(resID);
        }else {
            badge_id = badge_id+"_done";
            int resID = context.getResources().getIdentifier(badge_id, "drawable", context.getPackageName());
            badge.setImageResource(resID);
        }
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
