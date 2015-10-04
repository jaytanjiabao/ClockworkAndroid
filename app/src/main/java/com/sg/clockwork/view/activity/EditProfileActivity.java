package com.sg.clockwork.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.presenter.EditProfilePresenter;
import com.sg.clockwork.presenter.ProfilePicturePresenter;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    Button updateButton,backButton;
    EditText nameText, addressText, contactText;
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    String email, authToken;
    HashMap<String, String> user;
    ImageView pictureView;
    ProfilePicturePresenter profilePicturePresenter;
    HashMap<String, Integer> userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sg.clockwork.R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(com.sg.clockwork.R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(EditProfileActivity.this);

        editProfilePresenter = new EditProfilePresenter(this, dialog);
        user = editProfilePresenter.getUserMap();
        userID = editProfilePresenter.getUserID();
        nameText = (EditText) findViewById(com.sg.clockwork.R.id.nameText);
        backButton = (Button) findViewById(com.sg.clockwork.R.id.backButton);
        addressText = (EditText) findViewById(com.sg.clockwork.R.id.addressText);
        contactText = (EditText) findViewById(com.sg.clockwork.R.id.contactText);
        pictureView = (ImageView) findViewById(com.sg.clockwork.R.id.imageView);


        profilePicturePresenter = new ProfilePicturePresenter(pictureView);
        String avatar_path = user.get(SessionManager.KEY_AVATAR);
        profilePicturePresenter.getProfilePicture(avatar_path);
        updatePersonalDetails();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                backToListing.putExtra("Previous", "profile");
                startActivity(backToListing);
            }
        });

        updateButton = (Button) findViewById(com.sg.clockwork.R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editProfilePresenter.updateProfile(nameText.getText().toString(), addressText.getText().toString(),
                        contactText.getText().toString(), email, authToken);

                String accountType = user.get(SessionManager.KEY_ACCOUNTYPE);
                String avatarPath = user.get(SessionManager.KEY_AVATAR);
                int id = userID.get(SessionManager.KEY_ID);
                String dob = user.get(SessionManager.KEY_DOB);
                String nationality = user.get(SessionManager.KEY_NATIONALITY);
                editProfilePresenter.updateSession(id,nameText.getText().toString(),email,accountType,authToken,avatarPath,addressText.getText().toString(),contactText.getText().toString(),dob,nationality);
                Intent editProfile = new Intent(view.getContext(), MainActivity.class);
                editProfile.putExtra("Previous", "profile");
                startActivity(editProfile);
            }
        });
    }

    public void updatePersonalDetails() {

        nameText.setText(user.get(SessionManager.KEY_NAME));
        addressText.setText(user.get(SessionManager.KEY_ADDRESS));
        contactText.setText(user.get(SessionManager.KEY_CONTACT));
        email = user.get(SessionManager.KEY_EMAIL);
        authToken = user.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.sg.clockwork.R.menu.menu_edit_profile, menu);
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

    @Override
    public void onBackPressed () {
        Intent backToListing = new Intent(this.getApplicationContext(), MainActivity.class);
        backToListing.putExtra("Previous", "profile");
        startActivity(backToListing);
    }
}
