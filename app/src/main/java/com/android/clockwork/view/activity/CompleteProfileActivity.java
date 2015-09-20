package com.android.clockwork.view.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.Post;
import com.android.clockwork.presenter.CompleteProfilePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroupNationality, radioGroupGender;
    private String nationality;
    private String gender;
    Button updateButton,backButton;
    EditText contactText, dobText ;
    CompleteProfilePresenter completeProfilePresenter;
    ProgressDialog dialog;
    Post post;
    private int postID = 0 ;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat sdf;
    TextView statusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(CompleteProfileActivity.this);
        contactText = (EditText)findViewById(R.id.contactText);
        dobText = (EditText) findViewById(R.id.dob);
        updateButton = (Button) findViewById(R.id.updateButton);
        backButton = (Button) findViewById(R.id.backButton);
        statusText = (TextView) findViewById(R.id.statusText);
        completeProfilePresenter = new CompleteProfilePresenter(this,dialog,statusText);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        setDateTimeField();

        this.post = getIntent().getParcelableExtra(ViewJobActivity.PAR_KEY);
        this.postID = post.getId();



        radioGroupNationality = (RadioGroup) findViewById(R.id.myRadioGroup);

        radioGroupNationality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.Singaporean) {
                    nationality = "Singaporean";
                } else if (checkedId == R.id.SingaporePR) {
                    nationality = "Singapore PR";
                } else {
                    nationality = "Others";
                }
            }
        });

        radioGroupGender = (RadioGroup) findViewById(R.id.myRadioGroup2);

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.Male) {
                    gender = "M";
                } else {
                    gender = "F";
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroupNationality.getCheckedRadioButtonId() == -1){
                    statusText.setVisibility(View.VISIBLE);
                    statusText.setText("   Please select your nationality   ");
                } else if(radioGroupGender.getCheckedRadioButtonId()== -1) {
                    statusText.setVisibility(View.VISIBLE);
                    statusText.setText("   Please select your Gender   ");
                } else if(contactText.length()!= 8) {
                    statusText.setVisibility(View.VISIBLE);
                    statusText.setText("   Please enter a valid contact number   ");
                } else if (dobText.length() == 0) {
                    statusText.setVisibility(View.VISIBLE);
                    statusText.setText("   Please enter your date of birth   ");
                } else {
                    statusText.setVisibility(View.GONE);
                    completeProfilePresenter.completeProfile(postID, nationality, gender, contactText.getText().toString(), dobText.getText().toString());
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complete_profile, menu);
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
        Intent backToListing = new Intent(this, MainActivity.class);
        backToListing.putExtra("Previous", "dashboard");
        startActivity(backToListing);
        //finish();
    }

    private void setDateTimeField() {
        dobText.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dobText.setText(sdf.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void onClick(View v) {
        datePickerDialog.show();
    }
}
