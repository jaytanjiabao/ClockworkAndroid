package com.sg.clockwork.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sg.clockwork.R;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.presenter.ApplyJobPresenter;
import com.sg.clockwork.presenter.JobActionPresenter;
import com.sg.clockwork.presenter.ProfilePicturePresenter;
import com.sg.clockwork.view.tab.DashboardFragment;
import com.sg.clockwork.view.tab.JobListingFragment;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ViewJobActivity extends AppCompatActivity {
    Post post;
    ProgressDialog dialog;
    Button applyButton,backButton,rejectButton;
    ApplyJobPresenter applyJobPresenter;
    HashMap<String,String> user;
    public final static String PAR_KEY = "KEY";
    ProfilePicturePresenter profilePicturePresenter;
    JobActionPresenter jobActionPresenter;
    String activity = "null";
    ArrayList<Post> appliedList;
    ArrayList<Post> clashingList;
    String clashedStringCo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(com.sg.clockwork.R.layout.activity_view_job);

        Toolbar toolbar = (Toolbar) findViewById(com.sg.clockwork.R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backButton = (Button) findViewById(com.sg.clockwork.R.id.backButton);
        dialog = new ProgressDialog(ViewJobActivity.this);

        jobActionPresenter = new JobActionPresenter(this, dialog);
        applyJobPresenter = new ApplyJobPresenter(this, dialog);
        post = getIntent().getParcelableExtra(JobListingFragment.PAR_KEY);
        appliedList = getIntent().getParcelableArrayListExtra("appliedList");
        clashingList = new ArrayList<Post>();

        initializeScreen();

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Interested to be a " + post.getHeader() + " at " + post.getCompany() + "? Check it out at Clockwork!")
                .setContentUrl(Uri.parse("http://staging-clockworksmu.herokuapp.com/post.jsp?id=" + post.getId()))
                .build();
        ShareButton shareButton = (ShareButton)findViewById(com.sg.clockwork.R.id.shareButton);
        shareButton.setShareContent(content);

        user = applyJobPresenter.getSessionInfo();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                backToListing.putExtra("Previous", "home");
                startActivity(backToListing);
            }
        });
    }

    public void initializeScreen() {
        Intent intent = getIntent();
        activity = intent.getStringExtra("Activity");
        if (activity.equals("jobListing")) {
           applyButton = (Button) findViewById(com.sg.clockwork.R.id.applyButton);
            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user.get(SessionManager.KEY_CONTACT)== null || user.get(SessionManager.KEY_DOB) == null || user.get(SessionManager.KEY_NATIONALITY)== null){
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(PAR_KEY, post);
                        Intent updateProfile = new Intent(view.getContext(), CompleteProfileActivity.class);
                        updateProfile.putExtras(bundle);
                        startActivity(updateProfile);
                    } else{
                        applyJobPresenter.applyJob(post.getId());
                        Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                        backToListing.putExtra("Previous", "dashboard");
                        startActivity(backToListing);
                    }
                }
            });
        } else {
            post = getIntent().getParcelableExtra(DashboardFragment.PAR_KEY);
            rejectButton = (Button)findViewById(com.sg.clockwork.R.id.rejectJobOffer);
            applyButton = (Button) findViewById(com.sg.clockwork.R.id.applyButton);
            if (post.getStatus().equalsIgnoreCase("pending")) {
                    applyButton.setText("WITHDRAW APPLICATION");
                    applyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jobActionPresenter.withdrawJobApplication(post.getId());
                            Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                            backToListing.putExtra("Previous", "dashboard");
                            startActivity(backToListing);
                        }
                    });
            } else if (post.getStatus().equalsIgnoreCase("offered")) {
                applyButton.setText("ACCEPT JOB OFFER");
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        for (Post checkClashingPost : appliedList) {
                            if (checkClashingPost.getId() == (post.getId())) {
                                continue;
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = null;
                            Date endDate = null;
                            Date retrievedStartDate = null;
                            Date retrievedEndDate = null;
                            try {
                                startDate = formatter.parse(post.getJobDate());
                                endDate = formatter.parse(post.getEnd_date());
                                retrievedStartDate = formatter.parse(checkClashingPost.getJobDate());
                                retrievedEndDate = formatter.parse(checkClashingPost.getEnd_date());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if ((startDate.before(retrievedEndDate) || startDate.equals(retrievedEndDate)) && (endDate.after(retrievedStartDate) || endDate.equals(retrievedStartDate))) {
                                clashingList.add(checkClashingPost);
                            }
                        }

                        if (clashingList.size()!= 0) {
                            for (Post i : clashingList) {
                                clashedStringCo += i.getHeader() + ",";
                            }
                            clashedStringCo = clashedStringCo.substring(0, clashedStringCo.length() - 1);
                            System.out.println(clashedStringCo);
                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewJobActivity.this);

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
                                    jobActionPresenter.acceptJobOffer(post.getId());
                                    Intent backToListing = new Intent(getApplicationContext(), MainActivity.class);
                                    backToListing.putExtra("Previous", "dashboard");
                                    startActivity(backToListing);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            jobActionPresenter.acceptJobOffer(post.getId());
                            Intent backToListing = new Intent(getApplicationContext(), MainActivity.class);
                            backToListing.putExtra("Previous", "dashboard");
                            startActivity(backToListing);
                        }
                    }
                });

                rejectButton.setVisibility(View.VISIBLE);
                rejectButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        jobActionPresenter.withdrawJobApplication(post.getId());
                        Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                        backToListing.putExtra("Previous", "dashboard");
                        startActivity(backToListing);
                    }
                });

            } else if (post.getStatus().equalsIgnoreCase("hired")) {
                applyButton.setText("YOU HAVE BEEN HIRED");
            }
        }

        TextView title = (TextView) findViewById(com.sg.clockwork.R.id.jobText);
        TextView hiringCo = (TextView) findViewById(com.sg.clockwork.R.id.companyText);
        TextView location = (TextView) findViewById(com.sg.clockwork.R.id.locationText);
        TextView description = (TextView) findViewById(com.sg.clockwork.R.id.descriptionText);
        TextView jobDate = (TextView) findViewById(com.sg.clockwork.R.id.startText);
        TextView salary = (TextView) findViewById(com.sg.clockwork.R.id.salary);
        TextView duration = (TextView) findViewById(com.sg.clockwork.R.id.startTime);
        ImageView logo = (ImageView) findViewById(com.sg.clockwork.R.id.imageView);

        profilePicturePresenter = new ProfilePicturePresenter(logo);
        if (post.getAvatar_path() != null) {
            profilePicturePresenter.getProfilePicture(post.getAvatar_path());
        }

        title.setText(post.getHeader().toUpperCase());
        hiringCo.setText(post.getCompany().toUpperCase());
        location.setText(post.getLocation());
        description.setText(post.getDescription());

        SimpleDateFormat read = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat write = new SimpleDateFormat("d MMM yyyy");
        try {
            Date date = read.parse(post.getJobDate());
            jobDate.setText("You will work for " + post.getDuration() + " days starting on " + write.format(date));
        } catch (ParseException pe) {

        }

        //jobDate.setText("You will work for " + post.getDuration() + " days starting on " + post.getJobDate());
        duration.setText(post.getStart_time() + " to " + post.getEnd_time());

        if (post.getSalary() % 1 == 0) {
            salary.setText("$" + (int) post.getSalary());
        } else {
            salary.setText("$" + post.getSalary());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.sg.clockwork.R.menu.menu_view_job, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed () {
        Intent backToListing = new Intent(this.getApplicationContext(), MainActivity.class);
        backToListing.putExtra("Previous", "jobListing");
        startActivity(backToListing);
    }

}
