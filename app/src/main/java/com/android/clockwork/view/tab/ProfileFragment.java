package com.android.clockwork.view.tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.EditProfilePresenter;

import com.android.clockwork.presenter.LogoutPresenter;
import com.android.clockwork.presenter.ProfilePicturePresenter;

import com.android.clockwork.view.activity.ChangePasswordActivity;

import com.android.clockwork.view.activity.EditProfileActivity;
import com.android.clockwork.view.activity.PreludeActivity;
import java.util.HashMap;

public class ProfileFragment extends Fragment {
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    Button editButton, pwButton, logoutButton;
    View fragmentView;
    TextView usernameText, emailText;
    ImageView pictureView;
    HashMap<String, String> user;
    LogoutPresenter logoutPresenter;
    ProfilePicturePresenter profilePicturePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_3, container, false);
        usernameText = (TextView) fragmentView.findViewById(R.id.usernameText);
        emailText = (TextView) fragmentView.findViewById(R.id.emailText);
        pictureView = (ImageView) fragmentView.findViewById(R.id.imageView);

        // to remove editProfilePresenter
        editProfilePresenter = new EditProfilePresenter(getActivity());
        logoutPresenter = new LogoutPresenter(this);
        profilePicturePresenter = new ProfilePicturePresenter(pictureView);
        user = editProfilePresenter.getUserMap();
        String avatar_path = user.get(SessionManager.KEY_AVATAR);
        profilePicturePresenter.getProfilePicture(avatar_path);
        updatePersonalDetails();

        editButton = (Button) fragmentView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(view.getContext(), EditProfileActivity.class);
                startActivity(editProfile);
            }
        });

        pwButton = (Button) fragmentView.findViewById(R.id.pwButton);
        pwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePassword = new Intent(view.getContext(), ChangePasswordActivity.class);
                startActivity(changePassword);
            }
        });

        logoutButton = (Button) fragmentView.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                logoutPresenter.logOut();
            }
        });

        return fragmentView;
    }

    public void navigateToLogin() {
        startActivity(new Intent(fragmentView.getContext(), PreludeActivity.class));
    }

    public void updatePersonalDetails() {
        usernameText.setText(user.get(SessionManager.KEY_NAME));
        emailText.setText(user.get(SessionManager.KEY_EMAIL));
    }

}