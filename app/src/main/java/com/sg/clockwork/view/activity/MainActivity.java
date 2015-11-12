package com.sg.clockwork.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.sg.clockwork.adapter.PagerAdapter;


public class MainActivity extends AppCompatActivity {
    public static String searchTerm = "";
    public static String sortBy = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sg.clockwork.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.sg.clockwork.R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(com.sg.clockwork.R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Job Listings"));
        tabLayout.addTab(tabLayout.newTab().setText("My Board"));
        tabLayout.addTab(tabLayout.newTab().setText("My Rewards"));
        tabLayout.addTab(tabLayout.newTab().setText("My Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(com.sg.clockwork.R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        String previous = null;
        if (getIntent().getStringExtra("Previous") != null) {
            previous = getIntent().getStringExtra("Previous");
            if (previous.equalsIgnoreCase("dashboard")) {
                viewPager.setCurrentItem(1);
            }else if(previous.equalsIgnoreCase("rewards")){
                viewPager.setCurrentItem(2);
            }else if(previous.equalsIgnoreCase("jobListing")) {
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(3);
            }
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }


        });

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchTerm = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
        if (getIntent().getStringExtra("Sort") != null) {
            String action = getIntent().getStringExtra("Sort");
            if (action.equalsIgnoreCase("salary")) {
                sortBy = "salary";
            } else if (action.equalsIgnoreCase("start")) {
                sortBy = "start";
            } else if (action.equalsIgnoreCase("recent")) {
                sortBy = "recent";
            } else {
                sortBy = "";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.sg.clockwork.R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(com.sg.clockwork.R.id.action_search).getActionView();
        searchView.setQueryHint("Search for a job..");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case com.sg.clockwork.R.id.action_search:
                return true;
            case com.sg.clockwork.R.id.menuSortSalary:
                Intent reload = new Intent(getApplicationContext(), MainActivity.class);
                reload.putExtra("Sort", "salary");
                startActivity(reload);
                return true;
            case com.sg.clockwork.R.id.menuSortPostingDate:
                Intent reload2 = new Intent(getApplicationContext(), MainActivity.class);
                reload2.putExtra("Sort", "start");
                startActivity(reload2);
                return true;
            case com.sg.clockwork.R.id.menuSortStartDate:
                Intent reload3 = new Intent(getApplicationContext(), MainActivity.class);
                reload3.putExtra("Sort", "recent");
                startActivity(reload3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed () {

    }
}
