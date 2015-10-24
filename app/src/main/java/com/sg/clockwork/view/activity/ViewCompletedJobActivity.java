package com.sg.clockwork.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sg.clockwork.R;
import com.sg.clockwork.adapter.CompletedJobAdapter;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.presenter.ViewCompletedJobPresenter;

import java.util.ArrayList;

public class ViewCompletedJobActivity extends AppCompatActivity {
    Button backButton;
    ArrayList<Post> postList;
    ListView listView;
    ViewCompletedJobPresenter viewCompletedJobPresenter;
    CompletedJobAdapter completedJobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        postList = new ArrayList<Post>();
        backButton = (Button) findViewById(R.id.backButton);
        viewCompletedJobPresenter = new ViewCompletedJobPresenter(postList,this,false);
        viewCompletedJobPresenter.getCompletedJobList();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                backToListing.putExtra("Previous", "profile");
                startActivity(backToListing);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_completed_job, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void displayCompletedJobListing() {
        completedJobAdapter = viewCompletedJobPresenter.getCompletedJobAdapter();
        postList = viewCompletedJobPresenter.completedJobList();

        if(postList.size()!= 0) {
            listView.setAdapter(completedJobAdapter);
        }else {
            listView.setBackground(getResources().getDrawable(R.drawable.ratings_new));
        }
    }

    @Override
    public void onBackPressed () {
        Intent backToListing = new Intent(this.getApplicationContext(), MainActivity.class);
        backToListing.putExtra("Previous", "profile");
        startActivity(backToListing);
    }
}
