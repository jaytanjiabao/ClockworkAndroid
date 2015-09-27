package com.android.clockwork.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity {

    EditText emailText, nameText, pwText;
    RegisterPresenter registerPresenter;
    ProgressBar progressBar;
    TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        statusText = (TextView) findViewById(R.id.statusText);
        registerPresenter = new RegisterPresenter(this,progressBar,statusText);

        emailText = (EditText)findViewById(R.id.emailText);
        nameText = (EditText)findViewById(R.id.nameText);
        pwText = (EditText)findViewById(R.id.pwText);


        final Button submitBtn = (Button)findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameText.getText().toString().equals("")) {
                    statusText.setVisibility(View.VISIBLE);
                    statusText.setText("   Please enter your name!   ");
                }else {
                    registerPresenter.register(emailText.getText().toString(), pwText.getText().toString(), nameText.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed () {
        Intent backToHome = new Intent(this.getApplicationContext(), PreludeActivity.class);
        startActivity(backToHome);
    }
}
