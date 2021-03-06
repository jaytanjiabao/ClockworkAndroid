package com.sg.clockwork.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sg.clockwork.model.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sg.clockwork.R.layout.activity_splash);
        ProgressBar pb = (ProgressBar) findViewById(com.sg.clockwork.R.id.progressBar4);
        pb.setVisibility(View.VISIBLE);
        sessionManager = new SessionManager(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                System.out.println(sessionManager.checkNotLogin());
                if(sessionManager.checkNotLogin()) {
                    Intent userLogIn = new Intent(SplashActivity.this, PreludeActivity.class);
                    startActivity(userLogIn);
                }else{
                    Intent alreadyLoggedIn = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(alreadyLoggedIn);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.sg.clockwork.R.menu.menu_splash, menu);
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
}
