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
import com.sg.clockwork.adapter.BadgesAdapter;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.model.Rewards;
import com.sg.clockwork.presenter.ViewBadgesPresenter;

import java.util.ArrayList;

public class BadgeActivity extends AppCompatActivity {

    Button backButton;
    ListView listView;
    ArrayList<Rewards> badgeList;
    ViewBadgesPresenter viewBadgesPresenter;
    BadgesAdapter badgesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                backToListing.putExtra("Previous", "reward");
                startActivity(backToListing);
            }
        });

        listView = (ListView) findViewById(R.id.list);
        badgeList = new ArrayList<Rewards>();
        viewBadgesPresenter = new ViewBadgesPresenter(badgeList,this);
        viewBadgesPresenter.getBadges();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_badge, menu);
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

    public void displayBadgeListing() {
        badgesAdapter = viewBadgesPresenter.getBadgesAdapter();
        badgeList = viewBadgesPresenter.badgeList();
        listView.setAdapter(badgesAdapter);

    }

    @Override
    public void onBackPressed () {
        Intent backToListing = new Intent(this.getApplicationContext(), MainActivity.class);
        backToListing.putExtra("Previous", "rewards");
        startActivity(backToListing);
    }
}
