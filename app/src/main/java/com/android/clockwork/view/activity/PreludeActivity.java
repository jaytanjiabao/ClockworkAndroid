package com.android.clockwork.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.clockwork.R;
import com.android.clockwork.presenter.LoginPresenter;

public class PreludeActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userEmail,userPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelude);

        userEmail = (EditText) findViewById(R.id.emailText);
        userPassword = (EditText) findViewById(R.id.passwordText);
        findViewById(R.id.loginButton).setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prelude, menu);
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

    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        loginPresenter.validateCredentials(userEmail.getText().toString(), userPassword.getText().toString());
    }
}
