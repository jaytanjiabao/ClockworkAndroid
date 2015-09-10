package com.android.clockwork.view.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.clockwork.R;
import com.android.clockwork.adapter.CompletedJobAdapter;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.ViewCompletedJobPresenter;

import java.util.ArrayList;

public class ViewCompletedJobActivity extends AppCompatActivity {
    ProgressDialog dialog;
    ArrayList<Post> postList;
    ListView listView;
    ViewCompletedJobPresenter viewCompletedJobPresenter;
    CompletedJobAdapter completedJobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_job);
        dialog = new ProgressDialog(this);
        listView = (ListView) findViewById(R.id.list);
        postList = new ArrayList<Post>();
        viewCompletedJobPresenter = new ViewCompletedJobPresenter(postList,this,dialog,false);
        viewCompletedJobPresenter.getCompletedJobList();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayCompletedJobListing() {
        completedJobAdapter = viewCompletedJobPresenter.getCompletedJobAdapter();
        postList = viewCompletedJobPresenter.completedJobList();
        System.out.println(postList.size());
        listView.setAdapter(completedJobAdapter);
    }

}
