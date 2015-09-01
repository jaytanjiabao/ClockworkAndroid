package com.android.clockwork.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.clockwork.R;
import com.android.clockwork.model.Session;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.EditProfilePresenter;
import com.android.clockwork.view.tab.ProfileFragment;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    Button updateButton;
    EditText nameText, addressText, contactText;
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    String email, authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Activity", "Initializing");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(EditProfileActivity.this);

        nameText = (EditText) findViewById(R.id.nameText);
        addressText = (EditText) findViewById(R.id.addressText);
        contactText = (EditText) findViewById(R.id.contactText);
        editProfilePresenter = new EditProfilePresenter(this, dialog);
        updatePersonalDetails();

        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Activity", "Before executing..");
                editProfilePresenter.updateProfile(nameText.getText().toString(), addressText.getText().toString(),
                        contactText.getText().toString(), email, authToken);
                Log.d("Activity", "After executing..");
                Intent editProfile = new Intent(view.getContext(), MainActivity.class);
                startActivity(editProfile);
            }
        });
    }

    public void updatePersonalDetails() {
        HashMap<String, String> user = editProfilePresenter.getUserMap();
        nameText.setText(user.get(SessionManager.KEY_NAME));
        // to implement address
        // to implement contact
        email = user.get(SessionManager.KEY_EMAIL);
        authToken = user.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
}
