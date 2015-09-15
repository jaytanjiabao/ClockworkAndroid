package com.android.clockwork.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.ApplyJobPresenter;
import com.android.clockwork.view.tab.JobListingFragment;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.HashMap;

public class ViewJobActivity extends AppCompatActivity {
    Post post;
    ProgressDialog dialog;
    Button applyButton;
    ApplyJobPresenter applyJobPresenter;
    HashMap<String,String> user;
    public final static String PAR_KEY = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_view_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new ProgressDialog(ViewJobActivity.this);

        initializeScreen();
        applyJobPresenter = new ApplyJobPresenter(this, dialog);


        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://clockworksmu.herokuapp.com/post.jsp?id="+post.getId()))
                .build();
        ShareButton shareButton = (ShareButton)findViewById(R.id.shareButton);
        shareButton.setShareContent(content);




        applyButton = (Button) findViewById(R.id.applyButton);
        user = applyJobPresenter.getSessionInfo();
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.get(SessionManager.KEY_CONTACT)== null || user.get(SessionManager.KEY_DOB) == null || user.get(SessionManager.KEY_NATIONALITY)== null){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(PAR_KEY,post);
                    Intent updateProfile = new Intent(view.getContext(), CompleteProfileActivity.class);
                    updateProfile.putExtras(bundle);
                    startActivity(updateProfile);

                }else{
                    applyJobPresenter.applyJob(post.getId());
                    Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                    startActivity(backToListing);
                }
            }
        });
    }

    public void initializeScreen() {
        post = getIntent().getParcelableExtra(JobListingFragment.PAR_KEY);
        TextView title = (TextView) findViewById(R.id.jobText);
        TextView expiry = (TextView) findViewById(R.id.expiryText);
        TextView hiringCo = (TextView) findViewById(R.id.companyText);
        TextView location = (TextView) findViewById(R.id.locationText);
        TextView description = (TextView) findViewById(R.id.descriptionText);
        TextView jobDate = (TextView) findViewById(R.id.startText);
        TextView salary = (TextView) findViewById(R.id.salary);

        title.setText(post.getHeader());
        hiringCo.setText(post.getCompany());
        expiry.setText("Posted on " + post.getPosting_Date());
        location.setText(post.getLocation());
        description.setText(post.getDescription());
        jobDate.setText("Job Date: " + post.getJobDate());

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
