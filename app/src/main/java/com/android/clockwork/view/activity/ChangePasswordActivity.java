package com.android.clockwork.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.clockwork.R;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.EditProfilePresenter;
import com.android.clockwork.presenter.ProfilePicturePresenter;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {
    Button updateButton;
    EditText oldPwText, newPwText, confirmPwText;
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    String email, authToken;
    ImageView pictureView;
    HashMap<String, String> user;
    ProfilePicturePresenter profilePicturePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(ChangePasswordActivity.this);
        oldPwText = (EditText) findViewById(R.id.oldPwText);
        newPwText = (EditText) findViewById(R.id.newPwText);
        confirmPwText = (EditText) findViewById(R.id.confirmPwText);
        pictureView = (ImageView) findViewById(R.id.imageView);

        editProfilePresenter = new EditProfilePresenter(this, dialog);
        user = editProfilePresenter.getUserMap();
        profilePicturePresenter = new ProfilePicturePresenter(pictureView);
        String avatar_path = user.get(SessionManager.KEY_AVATAR);
        profilePicturePresenter.getProfilePicture(avatar_path);
        oldPwText.requestFocus();

        updatePersonalDetails();

        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change password
                if (oldPwText.length() <= 0) {
                    oldPwText.setError("Password missing!");
                } else if (oldPwText.length() < 8) {
                    oldPwText.setError("Password must contain at least 8 characters!");
                } else if (!newPwText.getText().toString().equals(confirmPwText.getText().toString())) {
                    confirmPwText.setError("New passwords do not match!");
                } else if (newPwText.length() < 8) {
                    newPwText.setError("Password must contain at least 8 characters!");
                } else {
                    editProfilePresenter.changePassword(oldPwText.getText().toString(), newPwText.getText().toString(),
                            confirmPwText.getText().toString(), email, authToken);
                    Intent changePassword = new Intent(view.getContext(), MainActivity.class);
                    startActivity(changePassword);
                }
            }
        });
        fieldValidation();
    }

    public void fieldValidation() {
        TextWatcher oldPasswordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    oldPwText.setError("Password missing!");
                } else if (s.length() < 8) {
                    oldPwText.setError("Password must contain at least 8 characters!");
                }
            }
        };
        oldPwText.addTextChangedListener(oldPasswordWatcher);

        TextWatcher passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    newPwText.setError("Password missing!");
                } else if (s.length() < 8) {
                    newPwText.setError("Password must contain at least 8 characters!");
                }
            }
        };
        newPwText.addTextChangedListener(passwordWatcher);

        TextWatcher newPasswordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!newPwText.getText().toString().equals(confirmPwText.getText().toString())) {
                    confirmPwText.setError("New passwords do not match!");
                }
            }
        };
        confirmPwText.addTextChangedListener(newPasswordWatcher);
    }

    public void updatePersonalDetails() {

        // to implement address
        // to implement contact
        email = user.get(SessionManager.KEY_EMAIL);
        authToken = user.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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
