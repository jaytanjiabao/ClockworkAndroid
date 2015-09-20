package com.android.clockwork.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.ApplyJobPresenter;
import com.android.clockwork.presenter.JobActionPresenter;
import com.android.clockwork.presenter.ProfilePicturePresenter;
import com.android.clockwork.view.tab.DashboardFragment;
import com.android.clockwork.view.tab.JobListingFragment;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ViewJobActivity extends AppCompatActivity {
    Post post;
    ProgressDialog dialog;
    Button applyButton;
    ApplyJobPresenter applyJobPresenter;
    HashMap<String,String> user;
    public final static String PAR_KEY = "KEY";
    ProfilePicturePresenter profilePicturePresenter;
    JobActionPresenter jobActionPresenter;
    String activity = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_view_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(ViewJobActivity.this);

        jobActionPresenter = new JobActionPresenter(this, dialog);
        applyJobPresenter = new ApplyJobPresenter(this, dialog);
        initializeScreen();

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Interested to be a " + post.getHeader() + " at " + post.getCompany() + "? Check it out at Clockwork!")
                .setContentUrl(Uri.parse("http://clockworksmu.herokuapp.com/post.jsp?id="+post.getId()))
                .build();
        ShareButton shareButton = (ShareButton)findViewById(R.id.shareButton);
        shareButton.setShareContent(content);

        user = applyJobPresenter.getSessionInfo();
    }

    public void initializeScreen() {
        Intent intent = getIntent();
        activity = intent.getStringExtra("Activity");
        if (activity.equals("jobListing")) {
            post = getIntent().getParcelableExtra(JobListingFragment.PAR_KEY);
            applyButton = (Button) findViewById(R.id.applyButton);
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
                        startActivity(backToListing);
                    }
                }
            });
        } else {
            post = getIntent().getParcelableExtra(DashboardFragment.PAR_KEY);
            applyButton = (Button) findViewById(R.id.applyButton);
            if (post.getStatus().equalsIgnoreCase("pending")) {
                    applyButton.setText("WITHDRAW APPLICATION");
                    applyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jobActionPresenter.withdrawJobApplication(post.getId());
                            Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                            startActivity(backToListing);
                        }
                    });
            } else if (post.getStatus().equalsIgnoreCase("offered")) {
                applyButton.setText("ACCEPT JOB OFFER");
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        jobActionPresenter.acceptJobOffer(post.getId());
                        Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                        startActivity(backToListing);
                    }
                });
            } else if (post.getStatus().equalsIgnoreCase("hired")) {
                applyButton.setText("YOU HAVE BEEN HIRED");
            }
        }

        TextView title = (TextView) findViewById(R.id.jobText);
        TextView hiringCo = (TextView) findViewById(R.id.companyText);
        TextView location = (TextView) findViewById(R.id.locationText);
        TextView description = (TextView) findViewById(R.id.descriptionText);
        TextView jobDate = (TextView) findViewById(R.id.startText);
        TextView salary = (TextView) findViewById(R.id.salary);
        TextView duration = (TextView) findViewById(R.id.startTime);
        ImageView logo = (ImageView) findViewById(R.id.imageView);

        profilePicturePresenter = new ProfilePicturePresenter(logo);
        if (post.getAvatar_path() != null) {
            profilePicturePresenter.getProfilePicture(post.getAvatar_path());
        }

        title.setText(post.getHeader().toUpperCase());
        hiringCo.setText(post.getCompany().toUpperCase());
        location.setText(post.getLocation());
        description.setText(post.getDescription());
        jobDate.setText(post.getDuration() + " days starting on " + post.getJobDate());
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
        getMenuInflater().inflate(R.menu.menu_view_job, menu);
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


}
