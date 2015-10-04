package com.sg.clockwork.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
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

import com.sg.clockwork.model.Post;
import com.sg.clockwork.presenter.JobActionPresenter;
import com.sg.clockwork.view.activity.MainActivity;
import com.sg.clockwork.view.activity.ViewJobActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jiabao.tan.2012 on 2/9/2015.
 */
public class DashboardAdapter extends BaseAdapter {
    public final static String PAR_KEY = "DASHBOARD";
    private Activity activity;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private static LayoutInflater inflater = null;
    ProgressDialog dialog;
    int arrayPosition;
    View view;
    JobActionPresenter jobActionPresenter;
    ArrayList<Post> clashingList;
    String clashedStringCo = "";

    public DashboardAdapter(Activity activity, ArrayList<Post> arrayList) {
        this.activity = activity;
        this.postList = arrayList;
        this.dialog = new ProgressDialog(activity);
        clashingList = new ArrayList<Post>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        jobActionPresenter = new JobActionPresenter(activity, dialog);

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            view = inflater.inflate(com.sg.clockwork.R.layout.dashboard_listing_row, null);
        }

        final Post p = postList.get(position);
        arrayPosition = position;

        TextView jobTitle = (TextView) view.findViewById(com.sg.clockwork.R.id.jobTitle);
        TextView hiringCo = (TextView) view.findViewById(com.sg.clockwork.R.id.hiringCo);
        TextView status = (TextView) view.findViewById(com.sg.clockwork.R.id.status);
        TextView salary = (TextView) view.findViewById(com.sg.clockwork.R.id.salary);
        ImageButton popup = (ImageButton) view.findViewById(com.sg.clockwork.R.id.popup);

        popup.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu(v, p);
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

    public void viewMenu(View v, Post post) {
        view = v;
        final Post p = post;
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        Menu m = popup.getMenu();
        inflater.inflate(com.sg.clockwork.R.menu.menu_dashboard_item, m);
        if (p.getStatus().equalsIgnoreCase("pending")) {
            m.removeItem(com.sg.clockwork.R.id.accept);
        } else if (p.getStatus().equalsIgnoreCase("offered")) {
            m.removeItem(com.sg.clockwork.R.id.withdraw);
        } else if (p.getStatus().equalsIgnoreCase("hired")) {
            m.removeItem(com.sg.clockwork.R.id.withdraw);
            m.removeItem(com.sg.clockwork.R.id.accept);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case com.sg.clockwork.R.id.view:
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(PAR_KEY, p);

                        Intent viewJobActivity = new Intent(view.getContext(), ViewJobActivity.class);
                        viewJobActivity.putExtra("Activity", "dashboard");
                        viewJobActivity.putExtras(bundle);
                        view.getContext().startActivity(viewJobActivity);
                        return true;
                    case com.sg.clockwork.R.id.withdraw:
                        jobActionPresenter.withdrawJobApplication(p.getId());
                        postList.remove(arrayPosition);
                        notifyDataSetChanged();
                        return true;
                    case com.sg.clockwork.R.id.accept:
                        for (Post checkClashingPost : postList) {
                            if (checkClashingPost.getId() == (p.getId())) {
                                continue;
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = null;
                            Date endDate = null;
                            Date retrievedStartDate = null;
                            Date retrievedEndDate = null;
                            try {
                                startDate = formatter.parse(p.getJobDate());
                                endDate = formatter.parse(p.getEnd_date());
                                retrievedStartDate = formatter.parse(checkClashingPost.getJobDate());
                                retrievedEndDate = formatter.parse(checkClashingPost.getEnd_date());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if ((startDate.before(retrievedEndDate) || startDate.equals(retrievedEndDate)) && (endDate.after(retrievedStartDate) || endDate.equals(retrievedStartDate))) {
                                clashingList.add(checkClashingPost);
                            }
                        }

                        if (clashingList != null) {
                            for (Post i : clashingList) {
                                clashedStringCo += i.getHeader() + ",";
                            }
                            clashedStringCo = clashedStringCo.substring(0, clashedStringCo.length() - 1);
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                            builder.setTitle("WARNING");
                            builder.setMessage(Html.fromHtml("Accepting this job offer will cause the following applications to be dropped: " + "<br><br>" +
                                    "<b>" + clashedStringCo + "</b>"));

                            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clashingList = new ArrayList<Post>();
                                    clashedStringCo = "";
                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clashingList = new ArrayList<Post>();
                                    clashedStringCo = "";
                                    jobActionPresenter.acceptJobOffer(p.getId());
                                    dialog.dismiss();
                                    Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                                    backToListing.putExtra("Previous", "dashboard");
                                    view.getContext().startActivity(backToListing);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            jobActionPresenter.acceptJobOffer(p.getId());
                            Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                            backToListing.putExtra("Previous", "dashboard");
                            view.getContext().startActivity(backToListing);
                         }
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
